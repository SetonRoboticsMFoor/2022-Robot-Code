// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import com.revrobotics.SparkMaxRelativeEncoder;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  //initialize variables
  double turn;
  double throttle;

  //initialize timer object
  Timer timer = new Timer();

  //initialize differential drive
  private DifferentialDrive driveTrain;

  //initialize all the motors
  private CANSparkMax leftMotor;
  private CANSparkMax rightMotor;
  private WPI_VictorSPX frontHerderSpinner;
  private WPI_VictorSPX rearHerderSpinner;
  private CANSparkMax frontHerderArm;
  private CANSparkMax shooterMotor;
  private CANSparkMax loaderMotor;
  private CANSparkMax rearHerderArm;
  private CANSparkMax climberArmMotor;
  private CANSparkMax climberWinchMotor;

  //initialize all the sensors
  private RelativeEncoder frontHerderEncoder;
  private RelativeEncoder rearHerderEncoder;
  //private RelativeEncoder shooterPosEncoder;
  private RelativeEncoder climberEncoder;
  private ADXRS450_Gyro driveGyro;

  //initialize joystick and all buttons
  private Joystick shooterStick;
  private Joystick driveStick;
  //herder spinners
  private JoystickButton herderForwardButton;
  private JoystickButton herderReverseButton;
  //herder arms
  private JoystickButton frontHerderUpButton;
  private JoystickButton frontHerderDownButton;
  private JoystickButton rearHerderUpButton;
  private JoystickButton rearHerderDownButton;
  //shooter buttons
  private JoystickButton shooterShooterForwardButton;
  private JoystickButton driverShooterForwardButton;
  private JoystickButton shooterReverseButton;
  private JoystickButton shooterLoaderUpButton;
  private JoystickButton driverLoaderUpButton;
  private JoystickButton shooterLoaderDownButton;
  private JoystickButton driverLoaderDownButton;
  //climber buttons
  private JoystickButton climberUpButton;
  private JoystickButton climberDownButton;
  private JoystickButton driverClimberWinchOutButton;
  private JoystickButton driverClimberWinchInButton;
  private JoystickButton shooterClimberWinchOutButton;
  private JoystickButton shooterClimberWinchInButton;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    //declare things
    //declare all motors
    rightMotor = new CANSparkMax(13,MotorType.kBrushless);
    leftMotor = new CANSparkMax(23,MotorType.kBrushless);
    frontHerderSpinner = new WPI_VictorSPX(9);
    rearHerderSpinner = new WPI_VictorSPX(11);
    frontHerderArm = new CANSparkMax(12,MotorType.kBrushed);
    rearHerderArm = new CANSparkMax(3,MotorType.kBrushed);
    shooterMotor = new CANSparkMax(2, MotorType.kBrushless);
    loaderMotor = new CANSparkMax(10,MotorType.kBrushed);
    climberArmMotor = new CANSparkMax(4,MotorType.kBrushless);
    climberWinchMotor = new CANSparkMax(7,MotorType.kBrushed);
    //declare drive train
    driveTrain = new DifferentialDrive(leftMotor,rightMotor);
    //declare shooter stick things
    shooterStick = new Joystick(0);
    herderForwardButton = new JoystickButton(shooterStick,2);
    herderReverseButton = new JoystickButton(shooterStick,11);
    frontHerderUpButton = new JoystickButton(shooterStick,6);
    frontHerderDownButton = new JoystickButton(shooterStick,4);
    rearHerderUpButton = new JoystickButton(shooterStick,5);
    rearHerderDownButton = new JoystickButton(shooterStick,3);
    shooterShooterForwardButton = new JoystickButton(shooterStick,1);
    shooterReverseButton = new JoystickButton(shooterStick, 12);
    shooterLoaderUpButton = new JoystickButton(shooterStick, 10);
    shooterLoaderDownButton = new JoystickButton(shooterStick, 9);
    shooterClimberWinchInButton = new JoystickButton(shooterStick,8);
    shooterClimberWinchOutButton = new JoystickButton(shooterStick,7);

    //declare drive stick things
    driveStick = new Joystick(1);
    driverShooterForwardButton = new JoystickButton(driveStick,1);
    driverLoaderUpButton = new JoystickButton(driveStick,8);
    driverLoaderDownButton = new JoystickButton(driveStick,7);
    climberUpButton = new JoystickButton(driveStick,10);
    climberDownButton = new JoystickButton(driveStick,9);
    driverClimberWinchInButton = new JoystickButton(driveStick,4);
    driverClimberWinchOutButton = new JoystickButton(driveStick,6);

    //declare encoders
    //shooterPosEncoder = shooterMotor.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature,10);
    frontHerderEncoder = frontHerderArm.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature,12);
    rearHerderEncoder = rearHerderArm.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature,3);
    climberEncoder = climberArmMotor.getEncoder();

    //declare gyro
    driveGyro = new ADXRS450_Gyro();

    //set all encoders to zero
    frontHerderEncoder.setPosition(0);
    rearHerderEncoder.setPosition(0);
    climberEncoder.setPosition(0);

    //set motor brake modes
    leftMotor.setIdleMode(IdleMode.kBrake);
    rightMotor.setIdleMode(IdleMode.kBrake);
    frontHerderSpinner.setNeutralMode(NeutralMode.Brake);
    rearHerderSpinner.setNeutralMode(NeutralMode.Brake);
    frontHerderArm.setIdleMode(IdleMode.kBrake);
    rearHerderArm.setIdleMode(IdleMode.kBrake);
    shooterMotor.setIdleMode(IdleMode.kBrake);
    loaderMotor.setIdleMode(IdleMode.kBrake);
    climberArmMotor.setIdleMode(IdleMode.kBrake);

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    //SmartDashboard.putNumber("Shooter Velocity", shooterPosEncoder.getVelocity());
    //SmartDashboard.putNumber("Shooter Position",shooterPosEncoder.getPosition());
    SmartDashboard.putNumber("Climber Encoder", climberEncoder.getPosition());
    SmartDashboard.putNumber("Gyro Value", driveGyro.getAngle());

    SmartDashboard.putNumber("Front Arm Position", frontHerderEncoder.getPosition());
    SmartDashboard.putNumber("Rear Arm Position", rearHerderEncoder.getPosition());
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);

    //set all encoders to zero (again)
    frontHerderEncoder.setPosition(0);
    rearHerderEncoder.setPosition(0);
    climberEncoder.setPosition(0);

    //reset the timer
    timer.reset();

    //start timer
    timer.start();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        //default auto program:
        //BEHOLD THE JANKIEST OF ALL AUTO CODE
        //GAZE UPON MY SPAGHETTI CODE AND WEEP, YE PEASANTS

        //spin up gun
        if(timer.get()<=5)
          shooterMotor.set(.45);
        else
          shooterMotor.stopMotor();

        if(timer.get()>=3 && timer.get()<=4)
          loaderMotor.set(-1);
        else if(timer.get()<=5)
          loaderMotor.set(1);
        else
          loaderMotor.stopMotor();
          
        
        //move off of tarmac
        if(timer.get()>=5 && timer.get()<=8)
          driveTrain.arcadeDrive(0,-.5);
        else
          driveTrain.stopMotor();

        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    //drive code
    turn = driveStick.getZ();
    throttle = driveStick.getY();

    //deadband
    //set turn to 0 if it is a small number
    if ( Math.abs(turn)<0.05)
      turn = 0;
    else
      turn = driveStick.getZ();
    //set throttle to 0 if it is a small number
    if ( Math.abs(turn)<0.05)
      throttle = 0;
    else
      throttle = driveStick.getY();

    driveTrain.arcadeDrive(turn*.75, -throttle);
    
    //controls for herder spinners
    if(herderForwardButton.get()==true) {
      frontHerderSpinner.set(ControlMode.PercentOutput,-1);
      rearHerderSpinner.set(ControlMode.PercentOutput,-1);
    }
    else if(herderReverseButton.get()==true) {
      frontHerderSpinner.set(ControlMode.PercentOutput,1);
      rearHerderSpinner.set(ControlMode.PercentOutput, 1);
    }
    else {
      frontHerderSpinner.stopMotor();
      rearHerderSpinner.stopMotor();
    }

    //controls for front herder arm
    //front herder
    if(frontHerderUpButton.get() && frontHerderEncoder.getPosition()<-20)
      frontHerderArm.set(-.7);
    else if(frontHerderDownButton.get() && frontHerderEncoder.getPosition()>-155)
      frontHerderArm.set(.55);
    else
      frontHerderArm.stopMotor();
    //rear herder
    if(rearHerderUpButton.get() && rearHerderEncoder.getPosition()<-20)
      rearHerderArm.set(.8);
    else if(rearHerderDownButton.get() && rearHerderEncoder.getPosition()>-650)
      rearHerderArm.set(-.65);
    else
    rearHerderArm.stopMotor();

    //climber controls
    if(climberUpButton.get() && climberEncoder.getPosition()<=220)
      climberArmMotor.set(1);
    else if(climberUpButton.get() && climberEncoder.getPosition()<248)
    climberArmMotor.set(.75);
    else if(climberDownButton.get() && climberEncoder.getPosition()>=25)
      climberArmMotor.set(-1);
    else if(climberDownButton.get() && climberEncoder.getPosition()>0)
    climberArmMotor.set(-.75);
    else
      climberArmMotor.stopMotor();

    //climber winch controls
    if(shooterClimberWinchInButton.get() || driverClimberWinchInButton.get())
      climberWinchMotor.set(.5);
    else if(shooterClimberWinchOutButton.get() || driverClimberWinchOutButton.get())
      climberWinchMotor.set(-.5);
    else
    climberWinchMotor.stopMotor();

    //shooter motor controls
    if(shooterShooterForwardButton.get() || driverShooterForwardButton.get())
      shooterMotor.set(.45);
    else if(shooterReverseButton.get())
      shooterMotor.set(-1);
    else
      shooterMotor.stopMotor();

    //shooter loader controls
    if(shooterLoaderUpButton.get() || driverLoaderUpButton.get())
      loaderMotor.set(-1);
    else if(shooterLoaderDownButton.get() || driverLoaderDownButton.get())
      loaderMotor.set(1);
    else
      loaderMotor.stopMotor();
  }

  

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
    //set all encoders to zero (again)
    frontHerderEncoder.setPosition(0);
    rearHerderEncoder.setPosition(0);
    climberEncoder.setPosition(0);
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    //controls for herder spinners
    if(herderForwardButton.get()) {
      frontHerderSpinner.set(ControlMode.PercentOutput,-1);
      rearHerderSpinner.set(ControlMode.PercentOutput,-1);
    }
    else if(herderReverseButton.get()) {
      frontHerderSpinner.set(ControlMode.PercentOutput,1);
      rearHerderSpinner.set(ControlMode.PercentOutput, 1);
    }
    else {
      frontHerderSpinner.stopMotor();
      rearHerderSpinner.stopMotor();
    }

    //controls for front herder arm
    //front herder
    if(frontHerderUpButton.get())
      frontHerderArm.set(-.7);
    else if(frontHerderDownButton.get())
      frontHerderArm.set(.55);
    else
      frontHerderArm.stopMotor();
    //rear herder
    if(rearHerderUpButton.get())
      rearHerderArm.set(.8);
    else if(rearHerderDownButton.get())
      rearHerderArm.set(-.65);
    else
    rearHerderArm.stopMotor();

    //climber controls
    if(climberUpButton.get())
      climberArmMotor.set(.25);
    else if(climberDownButton.get())
      climberArmMotor.set(-.25);
    else
      climberArmMotor.stopMotor();

    //climber winch controls
    if(shooterClimberWinchInButton.get() || driverClimberWinchInButton.get())
      climberWinchMotor.set(.5);
    else if(shooterClimberWinchOutButton.get() || driverClimberWinchOutButton.get())
      climberWinchMotor.set(-.5);
    else
    climberWinchMotor.stopMotor();

    //shooter motor controls
    if(shooterShooterForwardButton.get() || driverShooterForwardButton.get())
      shooterMotor.set(.45);
    else if(shooterReverseButton.get())
      shooterMotor.set(-1);
    else
      shooterMotor.stopMotor();

    //shooter loader controls
    if(shooterLoaderUpButton.get() || driverLoaderUpButton.get())
      loaderMotor.set(-1);
    else if(shooterLoaderDownButton.get() || driverLoaderDownButton.get())
      loaderMotor.set(1);
    else
      loaderMotor.stopMotor();
  }
}