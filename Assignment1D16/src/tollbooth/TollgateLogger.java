package tollbooth;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TollgateLogger extends Logger implements SimpleLogger{

	
	
	public TollgateLogger (String name){
		super(name, null);
		this.info("Logger Name: "+name);
	}

	@Override
	public void accept(LogMessage message) {
		// TODO Auto-generated method stub
		this.log(Level.SEVERE, message.getMessage());
	}

	@Override
	public LogMessage getNextMessage() {
		// TODO Auto-generated method stub
		throw new RuntimeException("Method Not Implemented Yet");
	}
	
}