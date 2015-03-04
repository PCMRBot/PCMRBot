package me.jewsofhazard.pcmrbot.customcommands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jewsofhazard.pcmrbot.database.Database;

public class CustomCommandParser {
	private static final Logger logger = Logger.getLogger(CustomCommandParser.class+"");
	
	/**
	 * @param command - command without the leading !
	 * @param sender - person who sent the command
	 * @param channel - channel the command was sent in
	 * @param parameters - parameters passed with the command
	 * @return formatted if the command is valid, null otherwise
	 */
	public static String parse(String command, String sender, String channel, String parameters) {
		ResultSet rs = Database.getCustomCommands(channel.substring(1));
		
		try {
			while(rs.next()) {
				if(rs.getString(1).equalsIgnoreCase(command)) {
					String reply = rs.getString(3);
					String[] passed = parameters.split("[|]");
					String[] params = rs.getString(2).split(" ");
					for(int i = 0;i < params.length;i++) {
						reply.replace(params[i], passed[i]);
					}
					return reply;
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "There was an issue executing a custom command", e);
		}
		return null;
	}
}
