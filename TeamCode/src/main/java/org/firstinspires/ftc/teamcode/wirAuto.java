/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * This OpMode illustrates the concept of driving a path based on encoder counts.
 * The code is structured as a LinearOpMode
 *
 * The code REQUIRES that you DO have encoders on the wheels,
 *   otherwise you would use: RobotAutoDriveByTime;
 *
 *  This code ALSO requires that the drive Motors have been configured such that a positive
 *  power command moves them forward, and causes the encoders to count UP.
 *
 *   The desired path in this example is:
 *   - Drive forward for 48 inches
 *   - Spin right for 12 Inches
 *   - Drive Backward for 24 inches
 *   - Stop and close the claw.
 *
 *  The code is written using a method called: encoderDrive(speed, leftInches, rightInches, timeoutS)
 *  that performs the actual movement.
 *  This method assumes that each movement is relative to the last stopping place.
 *  There are other ways to perform encoder based moves, but this method is probably the simplest.
 *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@Autonomous(name="wirAuto", group="Robot")
//@Disabled

public class wirAuto extends LinearOpMode {

    /* Declare OpMode members. */

    private ElapsedTime timer = new ElapsedTime();
    public wirHardware robot = new wirHardware();


    @Override
    public void runOpMode() {


        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Ready to start", "");
        telemetry.update();
        robot.init(hardwareMap);
        // Wait for the game to start (driver presses START)
        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        encoderDrive(1, 1000);
        sleep(500);
        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the OpMode running.
     */
    public void encoderDrive(double speed, int tickTarget) {
        robot.rightBack.setTargetPosition(tickTarget);
        robot.leftBack.setTargetPosition(tickTarget);
        robot.rightFront.setTargetPosition(tickTarget);
        robot.leftFront.setTargetPosition(tickTarget);

        robot.rightBack.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.leftBack.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.rightFront.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.leftFront.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        robot.rightBack.setPower(speed);
        robot.leftBack.setPower(speed);
        robot.rightFront.setPower(speed);
        robot.leftFront.setPower(speed);

        while (robot.rightFront.isBusy()|| robot.leftBack.isBusy()){
            telemetry.addData("right F: ",robot.rightFront.getCurrentPosition());
            telemetry.addData("left B: ",robot.leftBack.getCurrentPosition());
            telemetry.update();
        }
            robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.leftFront.setPower(0);
        robot.rightBack.setPower(0);

        robot.rightBack.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        robot.leftBack.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        robot.rightFront.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        robot.leftFront.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        robot.rightBack.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftBack.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightFront.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftFront.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    }
}