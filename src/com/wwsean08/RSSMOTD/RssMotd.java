package com.wwsean08.RSSMOTD;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RssMotd extends JavaPlugin{
	RssMotdPlayerListener pl;
	FileConfiguration config = null;
	Server server = null;
	PluginManager pm = null;
	@Override
	public void onDisable() {
		//goodbye
	}

	@Override
	public void onEnable() {
		//hello
		server = Bukkit.getServer();
		pm = server.getPluginManager();
		initConfig();
		pl = new RssMotdPlayerListener(config);
		pm.registerEvent(Event.Type.PLAYER_JOIN, pl, Event.Priority.Monitor, this);
		parseRSS();
		server.getLogger().info("[NEWS] RSS reader running");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(commandLabel.equalsIgnoreCase("news")){
			ArrayList<String> list = RssMotdParserRunnable.titles;
			sender.sendMessage(config.getString("info"));
			for(String s : list){
				sender.sendMessage("[NEWS] " + s);
			}
		}
		return true;
	}

	/**
	 * initializes the config file writing default values if none exist
	 */
	private void initConfig(){
		config = this.getConfig();
		config.options().copyDefaults(true);
		this.saveConfig();
	}

	/**
	 * parses the RSS feed and saves the results to be displayed to the user
	 */
	private void parseRSS(){
		//roughly 1200 ticks per second
		long updateInterval = config.getInt("RefreshTime")*1200;
		RssMotdParserRunnable runnable = new RssMotdParserRunnable(config.getString("Feed"), config.getInt("Posts"));
		server.getScheduler().scheduleAsyncRepeatingTask(this, runnable, 0, updateInterval);
	}
}
