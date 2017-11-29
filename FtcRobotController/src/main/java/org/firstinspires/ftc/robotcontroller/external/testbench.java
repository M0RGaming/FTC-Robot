package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Basic: Linear OpMode", group="Linear Opmode")

public class Test2 extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDriveForward = null;
    private DcMotor rightDriveForward = null;
    private DcMotor leftDriveBack = null;
    private DcMotor rightDriveBack = null;
    private Servo rightClaw = null;
    private Servo leftClaw = null;
    private Servo shooter = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftDriveBack  = hardwareMap.get(DcMotor.class, "left_drive_back");
        rightDriveBack = hardwareMap.get(DcMotor.class, "right_drive_back");
        leftDriveForward  = hardwareMap.get(DcMotor.class, "left_drive_forward");
        rightDriveForward  = hardwareMap.get(DcMotor.class, "right_drive_forward");
        rightClaw = hardwareMap.get(Servo.class, "right_claw");
        leftClaw = hardwareMap.get(Servo.class, "left_claw");
        shooter = hardwareMap.get(Servo.class, "shooter");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftDriveForward.setDirection(DcMotor.Direction.REVERSE);
        rightDriveForward.setDirection(DcMotor.Direction.FORWARD);
        leftDriveBack.setDirection(DcMotor.Direction.FORWARD);
        rightDriveBack.setDirection(DcMotor.Direction.REVERSE);

        leftDriveForward.setPower(0);
        rightDriveForward.setPower(0);
        leftDriveBack.setPower(0);
        rightDriveBack.setPower(0);

        rightClaw.setPosition(0.5);
        leftClaw.setPosition(0.5);
        shooter.setPosition(0.5);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            

            // Setup a variable for each drive wheel to save power level for telemetry
            double leftPower;
            double rightPower;
            double rightPos;
            double leftPos;
            double backPos;

            boolean auto = true;

            while (auto==true) {
                if (gamepad1.a) {
                    auto = false;
                };
            };

            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
//            double drive = -gamepad1.left_stick_y;
//            double turn  =  gamepad1.right_stick_x;
//            leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
//            rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            leftPower  = -gamepad1.left_stick_y ;
            rightPower = -gamepad1.right_stick_y ;
            

// Cancels out any unnessicary movement.
            if(leftPower<0.1) {
                leftPower = 0;
            };
            
            if(rightPower<0.1) {
                rightPower = 0;
            };

            // Send calculated power to wheels
            leftDriveForward.setPower(leftPower);
            rightDriveForward.setPower(rightPower);
            leftDriveBack.setPower(leftPower);
            rightDriveBack.setPower(rightPower);
            

// Moves the servos on the press on the buttons, hopefully it works

            if(gamepad1.x) {

                rightClaw.setPosition(0.25);
                leftClaw.setPosition(0.75);

            } else if (gamepad1.b) {

                rightClaw.setPosition(0.75);
                leftClaw.setPosition(0.25);

            } else if (gamepad1.start) {

                shooter.setPosition(0.75);

            };
            

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Left Claw Pos", leftClaw.getPosition());
            telemetry.addData("Right Claw Pos", rightClaw.getPosition());
            telemetry.addData("Shooter Pos", shooter.getPosition());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
    }
}