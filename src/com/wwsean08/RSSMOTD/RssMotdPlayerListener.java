package com.wwsean08.RSSMOTD;

import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class RssMotdPlayerListener extends PlayerListener{
	FileConfiguration config;
	public RssMotdPlayerListener(FileConfiguration file){
		config = file;
	}

	@Override
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		ArrayList<String> titles = RssMotdParserRunnable.titles;
		if(!player.hasPermission("news.ignore")){
			if(config.getBoolean("infoOnJoin")){
				player.sendMessage(config.getString("info"));
			}
			for(String s : titles){
				player.sendMessage(config.getString("prefix") + " " + s);
			}
		}
	}
}