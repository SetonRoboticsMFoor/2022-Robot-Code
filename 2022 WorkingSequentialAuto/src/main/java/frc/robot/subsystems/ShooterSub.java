// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class ShooterSub extends SubsystemBase {

  private final WPI_TalonSRX shooterMotor;


  /** Creates a new ShooterSub. */
  public ShooterSub() {

    shooterMotor = new WPI_TalonSRX(16);
  }


  public void spinForward() {
    shooterMotor.set(ControlMode.PercentOutput,getShootSpeed());
    //.getRawAxis() instead of constant
  }


  public void spinReverse() {
    shooterMotor.set(ControlMode.PercentOutput,-getShootSpeed());
    //.getRawAxis() instead of constant
  }

  public double getShootSpeed() {
    return .75*(RobotContainer.driveStick.getRawAxis(3)+1)+.25;
  }


  public void stopMotor() {
    shooterMotor.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
