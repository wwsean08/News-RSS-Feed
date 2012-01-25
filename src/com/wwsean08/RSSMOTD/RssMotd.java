package com.wwsean08.RSSMOTD;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RssMotd extends JavaPlugin{
	RssMotdPlayerListener pl;
	FileConfiguration config = null;
	Server server = null;
	PluginManager pm = null;
	private RssMotdParserRunnable runnable;
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
		pm.registerEvents(pl, this);
		parseRSS();
		server.getLogger().info("[NEWS] RSS reader running");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(commandLabel.equalsIgnoreCase("news")){
			if(args.length == 0){
				ArrayList<String> list = RssMotdParserRunnable.titles;
				sender.sendMessage(config.getString("info"));
				for(String s : list){
					sender.sendMessage(config.getString("prefix")+ " " + s);
				}
			}else if(args[0].equalsIgnoreCase("update")){
				if(sender instanceof Player){
					Player player = (Player)sender;
					if(player.hasPermission("news.update")){
						runnable.run();
					}else{
						player.sendMessage("you do not have permission to do that");
					}
				}else if(sender instanceof ConsoleCommandSender){
					runnable.run();
				}
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
		runnable = new RssMotdParserRunnable(config.getString("Feed"), config.getInt("Posts"));
		server.getScheduler().scheduleAsyncRepeatingTask(this, runnable, 0, updateInterval);
	}
}
