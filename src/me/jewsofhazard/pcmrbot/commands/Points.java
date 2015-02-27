package me.jewsofhazard.pcmrbot.commands;

import me.jewsofhazard.pcmrbot.database.Database;
import me.jewsofhazard.pcmrbot.util.CLevel;

public class Points extends Command {

	@Override
	public CLevel getCommandLevel() {
		return CLevel.Normal;
	}

	@Override
	public String getCommandText() {
		return "points";
	}

	@Override
	public String execute(String channel, String sender, String... parameters) {
		if(parameters[0].equalsIgnoreCase("!"+getCommandText())) {
			String pts = Database.getPoints(sender, channel.substring(1));
			if (pts == null) {
				return "%user% has no points!".replace("%user%", sender);
			} else {
				return String.format("%s has %s point(s)!", sender, pts);
			}
		}
		if(!Database.isMod(sender, channel.substring(1))) {
			return null;
		}
		String[] args = parameters[0].split("[|]");
		if (args.length < 3) {
			return "Hey, thats not right, use !points <add|remove>|<user>|<ammount>";
		}
		if (!args[0].matches("(add|remove)")) {
			return "Hey, thats not right, use !points <add|remove>|<user>|<ammount>";
		}
		if (Database.getPoints(args[1], channel.substring(1)) == null) {
			return "%user% has never been in the channel before!".replace("%user%", args[1]);
		}
		int amt = -1;
		int currentPoints = Integer.valueOf(Database.getPoints(args[1], channel.substring(1)));
		try {
			try {
				amt = Math.abs(Integer.valueOf(args[2]));
			} catch(Exception e1){
				return "That number is too big!";
			}
			if(amt>9000){
				return "That number is OVER 9000!!! Kappa";
			}
		} catch (NumberFormatException e1) {
			return "Hey, thats not right, use !points <add|remove>|<user>|<ammount>";
		}
		if (args[0].equalsIgnoreCase("add")) {
			Database.addPoints(args[1], channel.substring(1), amt);
			return String.format("Succesfully changed the points for %s to %s", args[1], Database.getPoints(args[1], channel.substring(1)));
		} else {
			if(amt>currentPoints){
				Database.addPoints(args[1], channel.substring(1), -currentPoints);
				return String.format("Succesfully changed the points for %s to %s", args[1], Database.getPoints(args[1], channel.substring(1)));
			}
			Database.addPoints(args[1], channel.substring(1), -amt);
			return String.format("Succesfully changed the points for %s to %s", args[1], Database.getPoints(args[1], channel.substring(1)));
		}
		
		
	}

}
