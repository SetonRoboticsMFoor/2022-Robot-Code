// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrainSub extends SubsystemBase {

  private final WPI_TalonSRX frontLeft;
  private final WPI_TalonSRX frontRight;
  private final WPI_TalonSRX rearLeft;
  private final WPI_TalonSRX rearRight;

  private final MotorControllerGroup leftSide;
  private final MotorControllerGroup rightSide;

  private final DifferentialDrive driveTrain;

  private final ADXRS450_Gyro driveGyro;

  public DriveTrainSub() {

    frontLeft = new WPI_TalonSRX(Constants.FRONT_LEFT_MOTOR_PORT);
    frontRight = new WPI_TalonSRX(Constants.FRONT_RIGHT_MOTOR_PORT);
    rearLeft = new WPI_TalonSRX(Constants.REAR_LEFT_MOTOR_PORT);
    rearRight = new WPI_TalonSRX(Constants.REAR_RIGHT_MOTOR_PORT);

    leftSide = new MotorControllerGroup(frontLeft, rearLeft);
    rightSide = new MotorControllerGroup(frontRight, rearRight);

    driveTrain = new DifferentialDrive(leftSide, rightSide);

    driveGyro = new ADXS450();
  }

  public void arcadeDrive(Joystick driveStick, double speed) {
    driveTrain.arcadeDrive(driveStick.getRawAxis(2)* speed, -driveStick.getRawAxis(1) * speed);
  }

  public void autoDrive(double turnSpeed, double driveSpeed) {
    driveTrain.arcadeDrive(turnSpeed, driveSpeed);
  }

  public void autoDriveStop() {
    driveTrain.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
