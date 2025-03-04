package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.hardware.lynx.LynxModule;
import java.util.List;

public class Elevator {
    private DcMotorEx elevatorMotorRight;
    private DcMotorEx elevatorMotorLeft;
    private PIDController pid;
    private List<LynxModule> allHubs;

    private static final int BOTTOM_POSITION = 90;
    private static final int MIDDLE_POSITION = 1700;
    private static final int TOP_POSITION = 2550;

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

        pid = new PIDController(0.01, 0.0, 0.0005);
    }

    public void update() {
        for (LynxModule hub : allHubs) {
            hub.clearBulkCache();
        }

        int currentPositionRight = elevatorMotorRight.getCurrentPosition();
        int currentPositionLeft = elevatorMotorLeft.getCurrentPosition();

        double powerRight = pid.calculatePID(targetPositionRight, currentPositionRight);
        double powerLeft = pid.calculatePID(targetPositionLeft, currentPositionLeft);

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
    }

    public void moveToBottom() {
        setTargetPosition(BOTTOM_POSITION);
    }

    public void moveToMiddle() {
        setTargetPosition(MIDDLE_POSITION);
    }

    public void moveToTop() {
        setTargetPosition(TOP_POSITION);
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

