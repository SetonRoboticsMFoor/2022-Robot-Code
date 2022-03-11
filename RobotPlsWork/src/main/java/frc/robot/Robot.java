// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import javax.crypto.spec.ChaCha20ParameterSpec;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
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

  //initialize differential drive
  private DifferentialDrive driveTrain;

  //initialize all the motors
  private CANSparkMax leftMotor;
  private CANSparkMax rightMotor;
  private WPI_VictorSPX frontHerderSpinner;
  private WPI_VictorSPX rearHerderSpinner;
  private CANSparkMax frontHerderArm;
  private CANSparkMax shooterMotor;
  private CANSparkMax rearHerderArm;
  private CANSparkMax climberArm;

  //initialize all the sensors
  private RelativeEncoder frontHerderEncoder;
  private RelativeEncoder rearHerderEncoder;
  private RelativeEncoder shooterPosEncoder;
  private RelativeEncoder climberEncoder;
  private ADXRS450_Gyro driveGyro;

  //initialize joystick and all buttons
  private Joystick shooterStick;
  //herder spinners
  private JoystickButton herderForwardButton;
  private JoystickButton herderReverseButton;
  //herder arms
  private JoystickButton frontHerderUpButton;
  private JoystickButton frontHerderDownButton;
  private JoystickButton rearHerderUpButton;
  private JoystickButton rearHerderDownButton;
  //shooter buttons
  private JoystickButton shooterForwardButton;
  private JoystickButton shooterReverseButton;
  //climber buttons
  private JoystickButton climberUpButton;
  private JoystickButton climberDownButton;

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
    climberArm = new CANSparkMax(4,MotorType.kBrushless);
    //declare drive train
    driveTrain = new DifferentialDrive(leftMotor,rightMotor);
    //declare joystick things
    shooterStick = new Joystick(0);
    herderForwardButton = new JoystickButton(shooterStick,2);
    herderReverseButton = new JoystickButton(shooterStick,11);
    frontHerderUpButton = new JoystickButton(shooterStick,6);
    frontHerderDownButton = new JoystickButton(shooterStick,4);
    rearHerderUpButton = new JoystickButton(shooterStick,5);
    rearHerderDownButton = new JoystickButton(shooterStick,3);
    climberUpButton = new JoystickButton(shooterStick,8);
    climberDownButton = new JoystickButton(shooterStick,7);
    //declare encoders
    shooterPosEncoder = shooterMotor.getEncoder();
    frontHerderEncoder = frontHerderArm.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature, 12);
    rearHerderEncoder = rearHerderArm.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature, 3);
    climberEncoder = climberArm.getEncoder();
    //declare gyro
    driveGyro = new ADXRS450_Gyro();

    //set motor brake modes
    frontHerderSpinner.setNeutralMode(NeutralMode.Brake);
    rearHerderSpinner.setNeutralMode(NeutralMode.Brake);
    frontHerderArm.setIdleMode(IdleMode.kBrake);
    rearHerderArm.setIdleMode(IdleMode.kBrake);
    shooterMotor.setIdleMode(IdleMode.kBrake);
    climberArm.setIdleMode(IdleMode.kBrake);

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
    SmartDashboard.putNumber("Shooter Velocity", shooterPosEncoder.getVelocity());
    SmartDashboard.putNumber("Shooter Position",shooterPosEncoder.getPosition());
    SmartDashboard.putNumber("Climber Encoder", climberEncoder.getPosition());
    SmartDashboard.putNumber("Gyro Value", driveGyro.getAngle());

    SmartDashboard.putNumber("Front Arm Position", frontHerderEncoder.getPosition()/800);
    SmartDashboard.putNumber("Rear Arm Position", rearHerderEncoder.getPosition()/800);
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
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {

    //set all encoders to zero
    frontHerderEncoder.setPosition(0);
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    //drive code (all one line of it)
    driveTrain.arcadeDrive(shooterStick.getZ()*.75, -shooterStick.getY());
    
    //controls for herder spinners
    if(herderForwardButton.get()==true) {
      frontHerderSpinner.set(ControlMode.PercentOutput,1);
      rearHerderSpinner.set(ControlMode.PercentOutput,1);
    }
    else if(herderReverseButton.get()==true) {
      frontHerderSpinner.set(ControlMode.PercentOutput,-1);
      rearHerderSpinner.set(ControlMode.PercentOutput, -1);
    }
    else {
      frontHerderSpinner.stopMotor();
      rearHerderSpinner.stopMotor();
    }

    //controls for front herder arm
    //front herder
    if(frontHerderUpButton.get()==true)
      frontHerderArm.set(.6);
    else if(frontHerderDownButton.get()==true)
      frontHerderArm.set(-.45);
    else
      frontHerderArm.stopMotor();
    //rear herder
    if(rearHerderUpButton.get()==true && rearHerderEncoder.getPosition()<.77)
      rearHerderArm.set(-.7);
    else if(rearHerderDownButton.get()==true/* && rearHerderEncoder.getPosition()>*/)
      rearHerderArm.set(.55);
    else
    rearHerderArm.stopMotor();

    if(climberUpButton.get()==true) {
      climberArm.set(1);
    }
    else if(climberDownButton.get()==true) {
      climberArm.set(-1);
    }
    else {
      climberArm.stopMotor();
    }
  }

  

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}