package org.firstinspires.ftc.teamcode.Constants;

import com.acmerobotics.dashboard.config.Config;

public class Constants {
    public static int ARM_TRANSITION_POSITION = -28, ARM_HANG_POSITION = 70, ARM_PUT_POSITION = 250, ARM_GRAB_POSITION = 340, ARM_GRAB_POSITION_SAFETY = 325;
    public static final double TICKS_IN_DEG =  (double) 360/288*72/45;;
    public static double armP = 0.02, armI = 0.025, armD= 0.001, armF = 0.275;

    // Elavator
    public static final int ELEVATOR_BOTTOM_POSITION = 90;
    public static final int ELEVATOR_MIDDLE_POSITION = 1700;
    public static final int ELEVATOR_TOP_POSITION = 2550;

    // Gripper Spinner
    public static final double GRIPPER_SPINNER_DOWN = 0.9;
    public static final double GRIPPER_SPINNER_MID = 0.55;
    public static final double GRIPPER_SPINNER_UP = 0.15;

    // Low Gripper
    public static final double LOW_GRIPPER_OPENED = 0.75;
    public static final double LOW_GRIPPER_CLOSED = 0.4;
}
