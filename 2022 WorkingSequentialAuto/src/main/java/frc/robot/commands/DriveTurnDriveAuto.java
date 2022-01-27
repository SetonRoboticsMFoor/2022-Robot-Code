// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrainSub;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DriveTurnDriveAuto extends SequentialCommandGroup {
  /** Creates a new DriveTurnDriveAuto. */
  public DriveTurnDriveAuto(DriveTrainSub driveTrain) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
       new TimedAutoCommand(driveTrain, 2, 0, .4), 
       // The line below waits for .5 seconds
       new TimedAutoCommand(driveTrain, .5, 0, 0),
       new TimedAutoCommand(driveTrain, 2, .4, 0), 
       // The line below waits for .5 seconds
       new TimedAutoCommand(driveTrain, .5, 0, 0),
       new TimedAutoCommand(driveTrain, 2, 0, -.4)
       );
    // subsytem, run time, turn speed, drive speed
   
    
    
  }
}
