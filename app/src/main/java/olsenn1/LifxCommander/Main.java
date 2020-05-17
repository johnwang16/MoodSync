/**
 * Lifx Commander
 * Author: Nicholas Olsen
 * Email: olsenn@gmail.com
 * Version: v1.0
 * Date: February 08, 2018
 */

package olsenn1.LifxCommander;

import olsenn1.Messages.Device.*;
import olsenn1.Messages.DataTypes.*;
import olsenn1.Messages.Light.*;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		int port = 56700;
		
		// Start Receiving Incoming Messages
		ReceiveMessages receiveMessages = new ReceiveMessages(port);
		receiveMessages.start();
		
		//Turn On All Lights
		SetPower_Light setPower = new SetPower_Light(olsenn1.Values.Power.ON);
		Command powerOn = new Command(setPower);
		ControlMethods.sendBroadcastMessage(powerOn.getByteArray(), port);
		
		//Make Lights White
		HSBK hsbk1 = new HSBK();
		hsbk1.setSaturation(olsenn1.Values.Levels.MIN);
		SetColor setColor1 = new SetColor(hsbk1);
		Command makeWhite = new Command(setColor1);
		ControlMethods.sendBroadcastMessage(makeWhite.getByteArray(), port);
		
		//Make Light Blue and 50% Brightness (Only Light w/ IP = 192.168.2.35)
		HSBK hsbk2 = new HSBK();
		hsbk2.setHue(olsenn1.Values.Hue.BLUE);
		hsbk2.setBrightness(olsenn1.Values.Levels.MAX / 2);
		SetColor setColor2 = new SetColor(hsbk2);
		Command makeBlue = new Command(setColor2);
		ControlMethods.sendUdpMessage(makeBlue.getByteArray(), "192.168.2.35", port);
		
		//Turn Off Infrared (All Lights)
		SetInfrared setInfrared = new SetInfrared(olsenn1.Values.Levels.MIN);
		Command turnOffIr = new Command(setInfrared);
		ControlMethods.sendBroadcastMessage(turnOffIr.getByteArray(), port);
		
		// Transition Color
		HSBK newColor = new HSBK();
		newColor.setHue(olsenn1.Values.Hue.RED);
		newColor.setSaturation(olsenn1.Values.Levels.MAX);
		newColor.setBrightness(olsenn1.Values.Levels.MAX);
		SetWaveform setWaveform = new SetWaveform();
		setWaveform.setColor(newColor);
		setWaveform.setCycles(2);
		setWaveform.setIsTransient(true);
		setWaveform.setPeriod(4000);
		setWaveform.setWaveform(olsenn1.Values.Waveforms.SINUSOID);
		Command changeColor = new Command(setWaveform);
		ControlMethods.sendBroadcastMessage(changeColor.getByteArray(), port);
		
		// Print Firmware Version
		GetHostFirmware getHostFirmware = new GetHostFirmware();
		Command firmwareCommand = new Command(getHostFirmware);
		firmwareCommand.getFrameAddress().setResRequired(true);
		ControlMethods.sendBroadcastMessage(firmwareCommand.getByteArray(), port);
		
		// Print Current State
		Get get = new Get();
		Command getCommand = new Command(get);
		getCommand.getFrameAddress().setResRequired(true);
		ControlMethods.sendBroadcastMessage(getCommand.getByteArray(), port);
		
	}
}