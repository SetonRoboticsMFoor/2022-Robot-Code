// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    // Drive train constants //////////////////////////////////////////////////////////
    public static final int LEFT_MOTOR_PORT = 3;
    public static final int RIGHT_MOTOR_PORT = 13;

    public static final double DRIVE_SPEED = .75;

    public static final int DRIVE_STICK_PORT = 0;

    // Shooter Subsystem constants ///////////////////////////////////////////////////
    
    public static final int SHOOT_MOTOR_PORT = 16;
    public static final int SHOOTER_FORWARD_BUTTON = 1;
    public static final int SHOOTER_REVERSE_BUTTON = 2;
    public static final double FORWARD_SHOOTER_SPEED = 1;
    public static final double REVERSE_SHOOTER_SPEED = 1;

    
    public static final int SHOOT_STICK_PORT = 1;
    public static final int SHOOT_BUTTON_CHANNEL = 0;

    // Herder Subsystem constants ///////////////////////////////////////////////////
    
    public static final int HERD_FORWARD_BUTTON = 3;
    public static final int HERD_REVERSE_BUTTON = 4;
    

    // Autonomous constants //////////////////////////////////////////////////////////
    public static final int AUTO_DRIVE_TIME = 2;
    public static final double AUTO_TURN_SPEED = 0;
    public static final double AUTO_DRIVE_SPEED = .5;

}
