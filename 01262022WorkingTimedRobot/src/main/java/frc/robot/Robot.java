
package frc.robot;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private WPI_TalonSRX frontLeft= new WPI_TalonSRX(7);
  private WPI_TalonSRX frontRight= new WPI_TalonSRX(2);
  private WPI_TalonSRX rearLeft= new WPI_TalonSRX(17);
  private WPI_TalonSRX rearRight= new WPI_TalonSRX(14);

  private MotorControllerGroup leftSide = new MotorControllerGroup(frontLeft, rearLeft);
  private MotorControllerGroup rightSide = new MotorControllerGroup(frontRight, rearRight);
  
  private DifferentialDrive myDrive = new DifferentialDrive(leftSide, rightSide);
  private Joystick myJoy = new Joystick(0);
  private UsbCamera frontCam = new UsbCamera("Front Camera", 0);
  //uncomment when fixed by WPI
  //private ADXRS450_Gyro myGyro = new ADXRS450_Gyro();

  
  
  
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    //myGyro.reset();
    //myGyro.calibrate();
    
  }

  @Override
  public void robotPeriodic() {
    //SmartDashboard.putData(myGyro);
    
   
  }

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  
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

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    double turnSpeed = myJoy.getRawAxis(2) * .5;
    double forwardSpeed = - myJoy.getRawAxis(1) * .5;
    myDrive.arcadeDrive(turnSpeed, forwardSpeed);
   

  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}
 
  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}
