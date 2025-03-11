package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.hardware.lynx.LynxModule;
import java.util.List;
import org.firstinspires.ftc.teamcode.Constants.Constants;
import com.seattlesolvers.solverslib.controller.PIDController;

public class Elevator {
    private DcMotorEx elevatorMotorRight;
    private DcMotorEx elevatorMotorLeft;

    private PIDController pid;
    public static double p = 0.0, i = 0.0, d = 0.0;
    public static double f = 0.0;

    private List<LynxModule> allHubs;

    private int targetPositionRight = 0;
    private int targetPositionLeft = 0;

    public Elevator(HardwareMap hardwareMap) {
        elevatorMotorRight = hardwareMap.get(DcMotorEx.class, "liftMotorRight");
        elevatorMotorLeft = hardwareMap.get(DcMotorEx.class, "liftMotorLeft");

        elevatorMotorRight.setDirection(DcMotorEx.Direction.FORWARD);
        elevatorMotorLeft.setDirection(DcMotorEx.Direction.REVERSE);

        elevatorMotorRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        elevatorMotorLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        elevatorMotorRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        elevatorMotorLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        allHubs = hardwareMap.getAll(LynxModule.class);

        pid = new PIDController(p, i, d);
    }

    public void update() {
        int currentPositionRight = elevatorMotorRight.getCurrentPosition();
        int currentPositionLeft = elevatorMotorLeft.getCurrentPosition();

        double powerRight = pid.calculate(targetPositionRight, currentPositionRight);
        double powerLeft = pid.calculate(targetPositionLeft, currentPositionLeft);

        elevatorMotorRight.setPower(powerRight);
        elevatorMotorLeft.setPower(powerLeft);
    }



    public void elevatorControl(Gamepad gamepad) {
        if (gamepad.dpad_down) {
            moveToBottom();
        } else if (gamepad.dpad_left) {
            moveToMiddle();
        } else if (gamepad.dpad_up) {
            moveToTop();
        }
        update();
    }

    public void moveToBottom() {
        setTargetPosition(Constants.ELEVATOR_BOTTOM_POSITION);
    }

    public void moveToMiddle() {
        setTargetPosition(Constants.ELEVATOR_MIDDLE_POSITION);
    }

    public void moveToTop() {
        setTargetPosition(Constants.ELEVATOR_TOP_POSITION);
    }

    public int getCurrentPositionRight() {
        return elevatorMotorRight.getCurrentPosition();
    }

    public int getCurrentPositionLeft() {
        return elevatorMotorLeft.getCurrentPosition();
    }

    public void setTargetPosition(int position) {
        this.targetPositionRight = position;
        this.targetPositionLeft = position;
    }
}