package org.firstinspires.ftc.teamcode;

public class PIDController {
    private double intergralSum = 0, lastError = 0;
    private double Kp = 0, Ki = 0, Kd = 0;

    public PIDController(double Kp, double Ki, double Kd) {
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
    }

    public double calculatePID(double target, double current) {
        double error = target - current;
        intergralSum += error;
        double derivative = error - lastError;
        lastError = error;

        double output = (Kp * error) + (Ki * intergralSum) + (Kd * derivative);

        return Math.max(-1, Math.min(1, output));
    }

    public void reset(){
        intergralSum = 0;
        lastError = 0;
    }

    public double calculate(int currentPos, int targetArm) {
        return 0;
    }
}
