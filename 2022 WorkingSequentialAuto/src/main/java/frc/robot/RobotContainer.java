// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.DriveTurnDriveAuto;
import frc.robot.commands.ShooterForwardCom;
import frc.robot.commands.ShooterReverseCom;
import frc.robot.commands.TeleDriveCommand;
import frc.robot.subsystems.DriveTrainSub;
import frc.robot.subsystems.ShooterSub;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveTrainSub m_driveTrainSub = new DriveTrainSub();
  private final ShooterSub m_shooterSub = new ShooterSub();
  private final TeleDriveCommand m_teleDriveCommand = new TeleDriveCommand(m_driveTrainSub);
  private final DriveTurnDriveAuto m_timedAutoCommand = new DriveTurnDriveAuto(m_driveTrainSub);
  public static Joystick driveStick = new Joystick(Constants.DRIVE_STICK_PORT);
  public static JoystickButton forwardButton = new JoystickButton(driveStick, Constants.SHOOTER_FORWARD_BUTTON);
  public static JoystickButton reverseButton = new JoystickButton(driveStick,Constants.SHOOTER_REVERSE_BUTTON);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    m_driveTrainSub.setDefaultCommand(m_teleDriveCommand);
    // Configure the button bindings
    configureButtonBindings();

    forwardButton.toggleWhenPressed(new ShooterForwardCom(m_shooterSub));
    reverseButton.toggleWhenPressed(new ShooterReverseCom(m_shooterSub));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_timedAutoCommand;
  }
}
