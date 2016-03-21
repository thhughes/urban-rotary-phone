package tollbooth;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import tollbooth.gatecontroller.TestGateController;

public class TollgateLoggerTest {

	
	
	
	@Test
	public void createNewTollGateLogger() throws IOException
	{
		assertNotNull(new TollgateLogger(null));
	}
	
	@Test
	public void createNewTollGateLoggerWithName() throws IOException
	{
		assertNotNull(new TollgateLogger("TEST_NAMEDLOGGERTEST"));
	}
	
}
