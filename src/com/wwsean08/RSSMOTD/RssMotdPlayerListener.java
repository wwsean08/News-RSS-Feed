package com.wwsean08.RSSMOTD;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class RssMotdPlayerListener extends PlayerListener{
	public RssMotdPlayerListener(){

	}

	@Override
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		ArrayList<String> titles = RssMotdParserRunnable.titles;
		for(String s : titles){
			player.sendMessage("[News] " + s);
		}
	}
}
