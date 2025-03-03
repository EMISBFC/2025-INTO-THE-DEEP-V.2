package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.List;

@Config
@TeleOp(name="Teleop RIGHT!!")
public class TeleOpSpecimen extends LinearOpMode {
    private Elevator elevator;

    @Override
    public void runOpMode() {
        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs){
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }

        elevator = new Elevator(hardwareMap);

        telemetry.addData(">", "Press START to start tests");
        telemetry.addData(">", "Test results will update for each access method.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()){
            resetCache(allHubs);

            elevator.elevatorControl(gamepad2);
        }
    }
    public void resetCache(List<LynxModule> allHubs){
        for(LynxModule hub : allHubs){
            hub.clearBulkCache();
        }
    }
}
