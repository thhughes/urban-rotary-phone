package tollbooth;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TollgateLogger implements SimpleLogger{
	private static final String loggerName = "TollgateLogger";
	private static Logger logger;
	private static FileHandler TollgateFileHandler;
	
	public TollgateLogger (String name) throws IOException{
		logger = Logger.getLogger(loggerName);
		TollgateFileHandler = new FileHandler("logs/"+name+".log");
		logger.addHandler(TollgateFileHandler);
		logger.setLevel(Level.ALL);
	}

	@Override
	public void accept(LogMessage message) {
		logger.log(Level.SEVERE, message.getMessage());
	}

	@Override
	public LogMessage getNextMessage() {
		// TODO Auto-generated method stub
		throw new RuntimeException("Method Not Implemented Yet");
	}
	
}
