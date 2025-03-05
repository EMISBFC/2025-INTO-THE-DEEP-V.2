package org.firstinspires.ftc.teamcode;
//import static org.firstinspires.ftc.teamcode.Elevator.block;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import java.util.List;

import org.firstinspires.ftc.teamcode.Constants.ConstantNamesHardwaremap;
import org.firstinspires.ftc.teamcode.Constants.Constants;


public class Arm{
    private final PIDController controller;
    private final DcMotor arm;
    private int targetArm;
    private double power;
    private int currentPos;

    public Arm(HardwareMap hardwareMap) {
        arm = hardwareMap.get(DcMotor.class, ConstantNamesHardwaremap.ARM);
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm.setDirection(DcMotorSimple.Direction.REVERSE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        controller = new PIDController(Constants.armP, Constants.armI, Constants.armD);
        targetArm = 0; // Default position

    }

    public void handleArmSpecimenTele(Gamepad gamepad){
        if (gamepad.left_bumper) {
            moveToHang();
        } else if (gamepad.right_bumper) {
            moveToGrab();
        }
        else if (gamepad.left_stick_button) {
            //moveToSafety();
        }

        // Update motor power with PID
        updateArm();
    }

    public enum TransitionState {
        IDLE,
        OPEN_HIGH_GRIPPER,
        ARM_TO_TRANSITION,
        LOW_OPEN,
        LOW_CLOSE,
        GRIPPER_TO_MID_FIRST,
        LOW_GRIPPER_UP,
        CLOSE_HIGH_GRIPPER,
        OPEN_LOW_GRIPPER,
        ARM_TO_PUT,
        GRIPPER_TO_MID,
        LOW_FINAL_CLOSE
    }

    private TransitionState transitionState = TransitionState.IDLE;
    private long transitionStartTime;

    /*public void handleTransition(Gamepad gamepad) {
        // Start transition if dpad_up is pressed and we are in the IDLE state
        if (gamepad.triangle && transitionState == TransitionState.IDLE) {
            transitionState = TransitionState.GRIPPER_TO_MID_FIRST;
            transitionStartTime = System.currentTimeMillis();
        }

        // State machine logic
        switch (transitionState) {

            case GRIPPER_TO_MID_FIRST:
                GripperSpinner.LRot.setDirection(Servo.Direction.REVERSE);
                GripperSpinner.LRot.setPosition(Constants.InRotPosMid);
                GripperSpinner.RRot.setDirection(Servo.Direction.FORWARD);
                GripperSpinner.RRot.setPosition(Constants.InRotPosMid);
                if (System.currentTimeMillis() - transitionStartTime > 400) {
                    transitionState = TransitionState.OPEN_HIGH_GRIPPER;
                    transitionStartTime = System.currentTimeMillis();
                }
                break;
            case OPEN_HIGH_GRIPPER:
                HighGripper.OpenGripper();
                if (System.currentTimeMillis() - transitionStartTime > 0) {
                    transitionState = TransitionState.ARM_TO_TRANSITION;
                    transitionStartTime = System.currentTimeMillis();
                }
                break;

            case ARM_TO_TRANSITION:
                moveToTransition();
                if (System.currentTimeMillis() - transitionStartTime > 0) {
                    transitionState = TransitionState.LOW_OPEN;
                    transitionStartTime = System.currentTimeMillis();
                }
                break;

            case LOW_OPEN:
                lowGripper.low_gripper.setPosition(Constants.LOGRIPPER_A_BIT_OPEN_POS);
                if (System.currentTimeMillis() - transitionStartTime > 100) {
                    transitionState = TransitionState.LOW_CLOSE;
                    transitionStartTime = System.currentTimeMillis();
                }
                break;
            case LOW_CLOSE:
                lowGripper.low_gripper.setPosition(Constants.LOGRIPPER_CLOSE_POS);
                if (System.currentTimeMillis() - transitionStartTime > 70) {
                    transitionState = TransitionState.LOW_GRIPPER_UP;
                    transitionStartTime = System.currentTimeMillis();
                }
                break;

            case LOW_GRIPPER_UP:
                GripperSpinner.LRot.setDirection(Servo.Direction.REVERSE);
                GripperSpinner.LRot.setPosition(Constants.InRotPosUp);
                GripperSpinner.RRot.setDirection(Servo.Direction.FORWARD);
                GripperSpinner.RRot.setPosition(Constants.InRotPosUp);
                if (System.currentTimeMillis() - transitionStartTime > 500) {
                    transitionState = TransitionState.CLOSE_HIGH_GRIPPER;
                    transitionStartTime = System.currentTimeMillis();
                }
                break;

            case CLOSE_HIGH_GRIPPER:
                HighGripper.CloseGripper();
                if (System.currentTimeMillis() - transitionStartTime > 450) {
                    transitionState = TransitionState.OPEN_LOW_GRIPPER;
                    transitionStartTime = System.currentTimeMillis();
                }
                break;

            case OPEN_LOW_GRIPPER:
                lowGripper.low_gripper.setPosition(Constants.LOGRIPPER_OPEN_POS);
                if (System.currentTimeMillis() - transitionStartTime > 100) {
                    transitionState = TransitionState.ARM_TO_PUT;
                    transitionStartTime = System.currentTimeMillis();
                }
                break;

            case ARM_TO_PUT:
                moveToPut();
                if (System.currentTimeMillis() - transitionStartTime > 30) {
                    transitionState = TransitionState.GRIPPER_TO_MID;
                    transitionStartTime = System.currentTimeMillis();
                }
                break;

            case GRIPPER_TO_MID:
                GripperSpinner.LRot.setDirection(Servo.Direction.REVERSE);
                GripperSpinner.LRot.setPosition(Constants.InRotPosMid);
                GripperSpinner.RRot.setDirection(Servo.Direction.FORWARD);
                GripperSpinner.RRot.setPosition(Constants.InRotPosMid);
                block = true;
                if (System.currentTimeMillis() - transitionStartTime > 0) {
                    transitionState = TransitionState.IDLE; // Transition complete
                }
                break;
            case LOW_FINAL_CLOSE:
                lowGripper.low_gripper.setPosition(Constants.LOGRIPPER_CLOSE_POS);
                if (System.currentTimeMillis() - transitionStartTime > 0) {
                    transitionState = TransitionState.IDLE;
                    transitionStartTime = System.currentTimeMillis();
                }
                break;

            case IDLE:
                // Do nothing, waiting for the next command
                break;
        }
    }*/

    public void moveToTransition() { targetArm = Constants.ARM_TRANSITION_POSITION; }
    public void moveToHang() { targetArm = Constants.ARM_HANG_POSITION; }
    public void moveToGrab() { targetArm = Constants.ARM_GRAB_POSITION; }
    public void moveToPut() { targetArm = Constants.ARM_PUT_POSITION; }
    public void moveToSafety() { targetArm = Constants.ARM_GRAB_POSITION_SAFETY; }

    public int getCurrentPos() {
        return arm.getCurrentPosition();
    }

    public void updateArm() {
        currentPos = arm.getCurrentPosition();
        double  pid = controller.calculate(currentPos, targetArm);
        double ff = Math.cos(Math.toRadians(currentPos / Constants.TICKS_IN_DEG)) * Constants.armF;
        power = 0.75 * (pid + ff);

        // Clamp power to motor range
        power = Math.max(-1.0, Math.min(1.0, power));

        arm.setPower(power);
    }
}

