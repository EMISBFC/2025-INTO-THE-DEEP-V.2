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
        telemetry.addData(">", "Press START to start tests");
        telemetry.addData(">", "Test results will update for each access method.");
        telemetry.update();

        for (LynxModule hub : allHubs){
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }

        elevator = new Elevator(hardwareMap);

        waitForStart();

        while (opModeIsActive()){
            resetCache(allHubs);

            elevator.update();
        }
    }
    public void resetCache(List<LynxModule> allHubs){
        for(LynxModule hub : allHubs){
            hub.clearBulkCache();
        }
    }
}
