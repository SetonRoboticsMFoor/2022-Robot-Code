// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HerderSub extends SubsystemBase {

  //declare things!
  //individual motors
  private final WPI_VictorSPX frontHerderSpinner;
  private final WPI_VictorSPX rearHerderSpinner;
  private final CANSparkMax frontHerderArm;
  private final CANSparkMax rearHerderArm;
  //motor controller groups
  private final MotorControllerGroup herderArms;
  private final MotorControllerGroup herderSpinners;

  /** Creates a new HerderSub. */
  public HerderSub() {

    //initialize all motors
    frontHerderSpinner = new WPI_VictorSPX(7);
    rearHerderSpinner = new WPI_VictorSPX(11);
    frontHerderArm = new CANSparkMax(12,MotorType.kBrushed);
    rearHerderArm = new CANSparkMax(3,MotorType.kBrushed);

    //initialize motor controller group
    herderArms = new MotorControllerGroup(frontHerderArm,rearHerderArm);
    herderSpinners = new MotorControllerGroup(frontHerderSpinner,rearHerderSpinner);
  }

  public void spinForward() {
    herderSpinners.set(.5);
  }

  public void spinBack() {
    herderSpinners.set(-.5);
  }

  public void stopMotor() {
    herderSpinners.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
