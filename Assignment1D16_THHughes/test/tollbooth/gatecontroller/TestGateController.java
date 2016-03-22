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

package tollbooth.gatecontroller;

import tollbooth.TollboothException;

/**
 * Description
 * @version Feb 15, 2016
 */
public class TestGateController implements GateController
{
	boolean isOpen;
	boolean isResponsive; 
	int numFails; 
	int failCount;
	/**
	 * Constructor for the test gate controller.
	 */
	public TestGateController()
	{
		isOpen = false;
		isResponsive = true;
		numFails = 0;
		failCount = 0;
	}
	
	/*
	 * @see tollbooth.gatecontroller.GateController#open()
	 */
	@Override
	public void open() throws TollboothException
	{
		if(!isResponsive){
			throw new TollboothException("System Unresponsive");
		}
		if(numFails > 0){
			numFails--;
			failCount++;
			if (failCount == 3){
				throw new TollboothException("open: unrecoverable malfunction");
			}
			throw new TollboothException("Open: Failed");
		}
		isOpen = true;
	}
	

	/*
	 * @see tollbooth.gatecontroller.GateController#close()
	 */
	@Override
	public void close() throws TollboothException
	{
		if(!isResponsive){
			throw new TollboothException("System Unresponsive");
		}
		if(numFails > 0){
			numFails--;
			failCount++;
			if (failCount == 2){
				throw new TollboothException("close: unrecoverable malfunction");
			}
			throw new TollboothException("Close: Failed");
		}
		isOpen = false;

	}

	/*
	 * @see tollbooth.gatecontroller.GateController#reset()
	 */
	@Override
	public void reset() throws TollboothException
	{
		if(!isResponsive){
			throw new TollboothException("System Unresponsive");
		}
		if(numFails > 0){
			numFails--;
			failCount++;
			if (failCount == 3){
				throw new TollboothException("System Unresponsive");
			}
			throw new TollboothException("Reset: Failed");
		}
		isOpen = false;

	}

	/*
	 * @see tollbooth.gatecontroller.GateController#isOpen()
	 */
	@Override
	public boolean isOpen() throws TollboothException
	{
		return isOpen;
	}
	
	/**
	 * This tells the gate to fail N times and sets the system to be in unresponsive mode or not. 
	 * @param N : number of times the gate fails. 
	 * @param unresponsive : True for 'will not respond' state
	 */
	public void failNTimes(int N, boolean unresponsive)
	{
		numFails = N;
		isResponsive = !unresponsive;
		
	}
	
	/**
	 * This tells if the gate is responsive. 
	 * @return False if the system is in 'will not respond more'
	 */
	public boolean isResponsive()
	{
		return isResponsive;
	}

}
