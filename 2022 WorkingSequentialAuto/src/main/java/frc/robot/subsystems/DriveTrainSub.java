// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrainSub extends SubsystemBase {

  //initialize ALL THE THINGS
  //spark maxes (that i think are breaking things >:( )
  private final CANSparkMax leftMotor;
  private final CANSparkMax rightMotor;
  //motor controller groups
  private final MotorControllerGroup leftSide;
  private final MotorControllerGroup rightSide;
  //the whole drive train!
  private final DifferentialDrive driveTrain;

  //constructor!
  public DriveTrainSub() {

    //initializes all the stuff when method is called
    //spark maxes
    rightMotor = new CANSparkMax(Constants.LEFT_MOTOR_PORT,MotorType.kBrushless);
    leftMotor = new CANSparkMax(Constants.RIGHT_MOTOR_PORT,MotorType.kBrushless);
    //motor controller groups
    leftSide = new MotorControllerGroup(leftMotor);
    rightSide = new MotorControllerGroup(rightMotor);
    //the whole drivetrain
    driveTrain = new DifferentialDrive(leftSide, rightSide);
  }

  //method to show camera on the driver station
  public void getCamera() {
    CameraServer.startAutomaticCapture();
  }

  //method to set drive mode to arcade drive
  public void arcadeDrive(Joystick driveStick, double speed) {
    //sets drive mode to arcade drive, with joystick axis 2 as the forward/backward speed and axis 1 as the rotational speed
    driveTrain.arcadeDrive(driveStick.getRawAxis(2)* speed, -driveStick.getRawAxis(1) * speed);
  }

  //method for ?autonomous? driving (kinda important)
  public void autoDrive(double turnSpeed, double driveSpeed) {
    //
    driveTrain.arcadeDrive(turnSpeed, driveSpeed);
  }

  //method to stop movement
  public void autoDriveStop() {
    //turns motors off
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