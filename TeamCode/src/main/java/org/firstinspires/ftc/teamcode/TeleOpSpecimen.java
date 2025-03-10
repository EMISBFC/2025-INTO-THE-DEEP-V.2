
package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;

@Config
@TeleOp(name="Teleop RIGHT!!")
public class TeleOpSpecimen extends LinearOpMode {
    private Elevator elevator;
    private GripperSpinner gripperSpinner;
    private lowGripper lowGripper;
    private Arm arm;

    @Override
    public void runOpMode() {
        //  manual bulk reads
        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }

        elevator = new Elevator(hardwareMap);
        gripperSpinner = new GripperSpinner(hardwareMap);
        lowGripper = new lowGripper(hardwareMap);
        arm = new Arm(hardwareMap);


        telemetry.addData(">", "Press START to start tests");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            resetCache(allHubs);

            // Elevator
            elevator.elevatorControl(gamepad2);
            elevator.update();

            // Gripper Spinner
            gripperSpinner.handleInput(gamepad1);
            gripperSpinner.update();

            //Arm
            arm.handleArmSpecimenTele(gamepad2);

            // Low Gripper
            lowGripper.lowGripperControl(gamepad1);


            telemetry.addData("Elevator Right Pos", elevator.getCurrentPositionRight());
            telemetry.addData("Elevator Left Pos", elevator.getCurrentPositionLeft());
            telemetry.update();
        }
    }

    public void resetCache(List<LynxModule> allHubs) {
        for (LynxModule hub : allHubs) {
            hub.clearBulkCache();
        }
    }
}
