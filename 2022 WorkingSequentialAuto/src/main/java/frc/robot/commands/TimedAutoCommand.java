// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.DriveTrainSub;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class TimedAutoCommand extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final DriveTrainSub m_subsystem;
  private boolean finish = false;
  private double m_runTime;
  private double m_turnSpeed;
  private double m_driveSpeed;
  Timer timer;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public TimedAutoCommand(DriveTrainSub subsystem, double runTime, double turnSpeed, double driveSpeed) {
    m_subsystem = subsystem;
    m_runTime = runTime;
    m_turnSpeed = turnSpeed;
    m_driveSpeed = driveSpeed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
    timer = new Timer();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    while (timer.get() < m_runTime) {
      m_subsystem.autoDrive(m_turnSpeed, m_driveSpeed);
    }
    finish = true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_subsystem.autoDrive(Constants.AUTO_TURN_SPEED, Constants.AUTO_DRIVE_SPEED);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.autoDriveStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    m_subsystem.autoDriveStop();
    return finish;
  }
}
