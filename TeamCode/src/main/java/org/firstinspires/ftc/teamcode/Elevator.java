package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.lynx.LynxModule;
import java.util.List;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;


public class Elevator {
    private DcMotorEx elevatorMotorRight, elevatorMotorLeft;
    private LynxModule[] allHubs;
    private PIDController pid;
    public static boolean block = false;

    private double targetPositionRight = 0, targetPositionLeft = 0;


    public Elevator(HardwareMap hardwareMap) {
        elevatorMotorRight = hardwareMap.get(DcMotorEx.class, "liftMotorRight");
        elevatorMotorLeft = hardwareMap.get(DcMotorEx.class, "liftMotorLeft");

        elevatorMotorRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        elevatorMotorLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        elevatorMotorRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        elevatorMotorLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        elevatorMotorLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        pid = new PIDController(0, 0, 0);
    }

   /*public void setTargetPosition(double target){
        this.targetPosition = target;
        pid.reset();
   }*/

   public void elevatorControl(Gamepad gamepad){
       if (gamepad.dpad_down && !block) {
           moveToBottom();
       } else if (gamepad.dpad_left && !block) {
           moveToMiddle();
       } else if (gamepad.dpad_up) {
           moveToTop();
       }
       update();
   }

    public void moveToBottom() {
        targetPositionRight = 90;
        targetPositionLeft = 90;
    }

    public void moveToMiddle() {
        targetPositionRight = 1700;
        targetPositionLeft = 1700;
    }

    public void moveToTop() {
        targetPositionRight = 2550;
        targetPositionLeft = 2550;
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
}
