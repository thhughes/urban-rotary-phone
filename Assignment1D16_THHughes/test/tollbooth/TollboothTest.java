/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package tollbooth;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import tollbooth.gatecontroller.*;

/**
 * Test cases for the Tollbooth, TollGate class.
 * @version Feb 3, 2016
 */
public class TollboothTest
{
	private static final String TollboothTestLogPath = "/logs/TollboothLoggerOutput/";
	
	@Test
	public void createNewTollGateWithNoController()
	{
		assertNotNull(new TollGate(null, null));
	}
	
	@Test
	public void createNewTollGateWithAController()
	{
		assertNotNull(new TollGate(new TestGateController(), null));
	}
	
	@Test
	public void newGateControllerIsClosed() throws TollboothException
	{
		final GateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		assertFalse(gate.isOpen());
	}

	@Test
	public void gateControllerIsOpenAfterOpenMessage() throws TollboothException
	{
		final GateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		gate.open();
		assertTrue(gate.isOpen());
	}
	
	@Test
	public void gateControllerOpensAndIncrementsStatsWhenGateIsClosed() throws TollboothException
	{
		final GateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		gate.open();
		assertEquals(1,gate.getNumberOfOpens());
	}
	
	@Test
	public void gateControllerOpensAndDoesNotIncrementsStatsWhenGateIsOpen() throws TollboothException
	{
		final GateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		gate.open();
		gate.open();
		assertEquals(1,gate.getNumberOfOpens());
	}
	
	@Test
	public void gateControllerNumberClosesDefaultCorrect() throws TollboothException
	{
		final GateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		assertEquals(0, gate.getNumberOfCloses());
	}
	
	@Test
	public void gateControllerClosesWhenReceivesCloseMessage() throws TollboothException
	{
		final GateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		gate.open();
		gate.close();
		assertFalse(gate.isOpen());
	}
	
	@Test
	public void gateControllerNumberOpensDefaultCorrect() throws TollboothException
	{
		final GateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		assertEquals(0, gate.getNumberOfOpens());
	}
	
	@Test
	public void gateControllerDoesntChangeStatsOnCloseWhenClosed() throws TollboothException
	{
		final GateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		gate.close();
		assertEquals(0, gate.getNumberOfCloses());
	}
	
	@Test
	public void gateControllerCorrectlyIncrementsCloseCountWhenReceivesCloseAndIsOpen() throws TollboothException
	{
		final GateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		gate.open();
		gate.close();
		assertEquals(1,gate.getNumberOfCloses());
	}
	
	@Test
	public void gatControllerCorrectlyClosesGateWhenResetIsUsedWhileOpen() throws TollboothException
	{
		final GateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		gate.open();
		gate.reset();
		
		assertFalse(gate.isOpen());
	}
	
	@Test
	public void gatControllerCorrectlyClosesGateWhenResetIsUsedWhileClosed() throws TollboothException
	{
		final GateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		gate.reset();
		assertFalse(gate.isOpen());	
	}
	
	
	@Test
	public void gateControllerCorrectlyLeavesStatsAloneWhenResetIsUsedWhileOpen() throws TollboothException
	{
		final GateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		gate.open();
		int numCloses = gate.getNumberOfCloses();
		gate.reset();
		assertEquals(numCloses, gate.getNumberOfCloses());
	}
	
	@Test
	public void gateControllerOpensOnSingleFailureForOpen() throws TollboothException
	{
		final TestGateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		controller.failNTimes(1,false);
		gate.open();
		assertTrue(gate.isOpen());
		
	}
	
	@Test
	public void gateControllerOnlyIncrementsStatsByOneOnFailureToOpenWhileClosed() throws TollboothException
	{
		final TestGateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		controller.failNTimes(1,false);
		gate.open();
		assertEquals(1, gate.getNumberOfOpens());
		
	}
	
	
	@Test
	public void gateControllerCreatesCorrectMessagesOnSingleFailureToOpenWhileClosed() throws TollboothException
	{

		final TestGateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		controller.failNTimes(1,false);
		gate.open();
		
		LogMessage message = logger.getNextMessage();
		assertEquals("open: malfunction", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("open: successful", message.getMessage());
	}
	
	@Test
	public void gateControllerLoggsAnErrorWhenOpenFailsFirstTimeOnSingleFailureWhileClosed() throws TollboothException
	{

		final TestGateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		controller.failNTimes(1,false);
		gate.open();
		
		LogMessage message = logger.getNextMessage();
		assertTrue(message.hasCause());
	}
	
	@Test
	public void gateControllerLoggsCorrectErrorWhenOpenFailsFirstTimeOnSingleFailureWhileClosed() throws TollboothException
	{

		final TestGateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		controller.failNTimes(1,false);
		gate.open();
		
		LogMessage message = logger.getNextMessage();
		assertNotNull(message.getCause());
	}


	
	@Test
	public void gateControllerOpensGateWhileClosedWhenGateFailsTwoTimes() throws TollboothException
	{
		final TestGateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		controller.failNTimes(2,false);
		gate.open();
		assertTrue(gate.isOpen());
	}
	
	@Test
	public void gateControllerDoesNotOpenGateWhileClosedWhenGateFailsThreeTimes() throws TollboothException
	{
		final TestGateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		controller.failNTimes(3,false);
		try{
			gate.open();
		}catch(TollboothException e){
			
		}
		assertFalse(gate.isOpen());
	}
	
	@Test
	public void gateControllerCorrectlyLogsWhileOpensGateWhileClosedWhenGateFailsThreeTimes() throws TollboothException
	{
		final TestGateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		controller.failNTimes(3,true);
		try{
			gate.open();
		}catch(TollboothException e){
			
		}
		
		assertFalse(gate.isOpen());
		LogMessage message = logger.getNextMessage();
		assertEquals("open: malfunction", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("open: malfunction", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("open: unrecoverable malfunction", message.getMessage());
		assertNotNull(message.getCause());
	}
	
	@Test
	public void gateControllerCorrectlyThrowsErrorWhileOpensGateWhileClosedWhenGateFailsThreeTimes() throws TollboothException
	{
		final TestGateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		controller.failNTimes(3,true);
		try{
			gate.open();
			assertTrue(false);					// This will always fail, an error should be thrown here. 
		}catch(TollboothException e){
			assertNotNull(e);
		}

		
	}
	
	@Test
	public void gateControllerFailsToOpenGateWhileClosedWhenGateFailsThreeTimesGateBecomesUnresponsive() throws TollboothException
	{
		final TestGateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		controller.failNTimes(3,true);
		try{
			gate.open();
		}catch(TollboothException e){
			
		}
		assertFalse(gate.isOpen());
	}
	
	@Test
	public void gateControllerDoesntModifyStatsIfInWillNotRespondMode() throws TollboothException
	{
		final TestGateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		controller.failNTimes(3,true);
		try{
			gate.open();
		}catch(TollboothException e){
			
		}
		gate.open();
		
		assertEquals(0, gate.getNumberOfOpens());
		
	}
	
	@Test
	public void gateControllerLogsCorrectlyIfInWillNotRespondMode() throws TollboothException
	{
		final TestGateController controller = new TestGateController();
		final SimpleLogger logger = new TollgateLogger("TheLogger");
		final TollGate gate = new TollGate(controller, logger);
		controller.failNTimes(3,true);
		try{
			gate.open();
		}catch(TollboothException e){
			
		}
		gate.open();
		
		LogMessage message = logger.getNextMessage();
		assertEquals("open: malfunction", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("open: malfunction", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("open: unrecoverable malfunction", message.getMessage());
		message = logger.getNextMessage();
		assertEquals("open: will not respond", message.getMessage());
	}
	
//	@Test
//	public void gateControllerSuccedsToCloseGateWhileOpenAfterOneFailure() throws TollboothException
//	{
//		final TestGateController controller = new TestGateController();
//		final SimpleLogger logger = new TollgateLogger("TheLogger");
//		final TollGate gate = new TollGate(controller, logger);
//		gate.open();
//		controller.failNTimes(1,false);
//		gate.close();
//		
//		assertFalse(gate.isOpen());
//	}
//	
//	@Test
//	public void gateControllerLogsSingleFailureCorrectly() throws TollboothException
//	{
//		final TestGateController controller = new TestGateController();
//		final SimpleLogger logger = new TollgateLogger("TheLogger");
//		final TollGate gate = new TollGate(controller, logger);
//		gate.open();
//		controller.failNTimes(1,false);
//		gate.close();
//		
//		LogMessage message = logger.getNextMessage();
//		assertEquals("close: malfunction", message.getMessage());
//		assertNotNull(message.getCause());
//		message = logger.getNextMessage();
//		assertEquals("close: successful", message.getMessage());
//	}
//	
//	@Test
//	public void gateControllerFailsToCloseGateAfter3Failures() throws TollboothException
//	{
//		final TestGateController controller = new TestGateController();
//		final SimpleLogger logger = new TollgateLogger("TheLogger");
//		final TollGate gate = new TollGate(controller, logger);
//		gate.open();
//		controller.failNTimes(3,true);
//		gate.close();
//		
//		assertTrue(gate.isOpen());
//	}
	
	
	
}
