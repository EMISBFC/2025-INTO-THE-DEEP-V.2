package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.List;

public class lowGripper {
    private Servo lowGripper;
    private List<LynxModule> allHubs;
    private boolean gripperLock = false;

    public enum GripperState {
        OPEN(Constants.LOW_GRIPPER_OPENED),
        CLOSED(Constants.LOW_GRIPPER_CLOSED);

        public final double position;
        GripperState(double position) {
            this.position = position;
        }
    }

    private GripperState currentState = GripperState.CLOSED;

    public lowGripper(HardwareMap hardwareMap) {
       lowGripper = hardwareMap.get(Servo.class, "loServo");
        allHubs = hardwareMap.getAll(LynxModule.class);

        setPosition(currentState); // Initialize at default position
    }

    public void toggleGripper() {
        if (currentState == GripperState.CLOSED) {
            setGripperState(GripperState.OPEN);
        } else {
            setGripperState(GripperState.CLOSED);
        }
    }

    public void lowGripperControl(Gamepad gamepad){
        if (gamepad.x && !gripperLock) {
            toggleGripper();
            gripperLock = true;
        } else if (!gamepad.x) {
            gripperLock = false;
        }
    }

    public void setGripperState(GripperState newState) {
        if (newState == GripperState.OPEN) {
            lowGripper.setPosition(Constants.LOW_GRIPPER_OPENED);
        } else {
            lowGripper.setPosition(Constants.LOW_GRIPPER_CLOSED);
        }
        currentState = newState;
    }


    public void setPosition(GripperState state) {
        this.currentState = state;
        lowGripper.setPosition(state.position);
    }

    public GripperState getCurrentState() {
        return currentState;
    }
}
