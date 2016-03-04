package org.georgev.calc;

import org.georgev.calc.parser.ExpressionParser;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;


public class Calc 
{
    public static void main( String[] args )
    {
    	setLogging();
    	if (args != null && args.length == 1) {
    		System.out.println(ExpressionParser.parse(args[0]).evaluate());
    	}
    }

	private static void setLogging() {
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    	root.setLevel(Level.INFO);
    	String logLevel = System.getProperty("logLevel", "INFO");
    	if (Level.INFO.toString().equalsIgnoreCase(logLevel)) {
    		root.setLevel(Level.INFO);
    	} else if (Level.ERROR.toString().equalsIgnoreCase(logLevel)) {
    		root.setLevel(Level.ERROR);
    	} if (Level.DEBUG.toString().equalsIgnoreCase(logLevel)) {
    		root.setLevel(Level.DEBUG);
    	}
	}
}
