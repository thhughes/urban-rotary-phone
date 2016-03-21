/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Troy H. Hughes
 *******************************************************************************/

package tollbooth;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import tollbooth.gatecontroller.TestGateController;

public class TollgateLoggerTest {

	private static final String testLogPath = "/logs/TollgateLoggerTests/";
	

	@Test
	public void createNewTollGateLogger()
	{
		assertNotNull(new TollgateLogger(null));
	}
	
	@Test
	public void createNewTollGateLoggerWithName()
	{
		assertNotNull(new TollgateLogger(testLogPath+"TEST_NAMEDLOGGERTEST"));
	}
	
	@Test
	public void defaultLoggerMessageSize()
	{
		final TollgateLogger tblogger = new TollgateLogger(testLogPath+"TEST_addMessage");
		assertEquals(0, tblogger.logLength());
		
	}
	
	@Test
	public void addMessageToToolBoothLogger()
	{
		final TollgateLogger tblogger = new TollgateLogger(testLogPath+"TEST_addMessage");
		tblogger.accept(new LogMessage("This Is A Log Message"));
		assertEquals(1, tblogger.logLength());
		
	}
	@Test
	public void addmultipleMessageToToolBoothLogger()
	{
		final TollgateLogger tblogger = new TollgateLogger(testLogPath+"TEST_addMessage");
		tblogger.accept(new LogMessage("This Is A Log Message"));
		tblogger.accept(new LogMessage("This Is A Log Message"));
		tblogger.accept(new LogMessage("This Is A Log Message"));
		tblogger.accept(new LogMessage("This Is A Log Message"));
		tblogger.accept(new LogMessage("This Is A Log Message"));
		tblogger.accept(new LogMessage("This Is A Log Message"));
		assertEquals(6, tblogger.logLength());
		
	}
	
	@Test
	public void addAndRemoveMultipleMessageToToolBoothLogger()
	{
		final TollgateLogger tblogger = new TollgateLogger(testLogPath+"TEST_addMessage");
		for(int i = 0; i < 6; i++){
			tblogger.accept(new LogMessage("Hello World!"));
		}
		for(int i = 0; i < 6; i++){
			tblogger.getNextMessage();
		}
		assertEquals(0, tblogger.logLength());
		
	}
	
	@Test
	public void receiveNullWhenThereAreNoMessagesToGet()
	{
		final TollgateLogger tblogger = new TollgateLogger(testLogPath+"TEST_addMessage");
		assertNull(tblogger.getNextMessage());
		
	}
}
