package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.List;

public class TeleOpSpecimen extends LinearOpMode {
    private Elevator elevator;

    @Override
    public void runOpMode() {
        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs){
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }

        elevator = new Elevator(hardwareMap);

        waitForStart();

        while (opModeIsActive()){
            for (LynxModule module : allHubs){
                module.clearBulkCache();
            }

            elevator.update();
        }
    }
}
