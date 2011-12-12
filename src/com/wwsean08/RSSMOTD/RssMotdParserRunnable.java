package com.wwsean08.RSSMOTD;

import java.util.ArrayList;
import subin.rnd.xml.RssParser;
import subin.rnd.xml.RssParser.Item;
import subin.rnd.xml.RssParser.RssFeed;

public class RssMotdParserRunnable implements Runnable{
	private String URL;
	private int numOfStories;
	public static ArrayList<String> titles;
	public RssMotdParserRunnable(String feed, int items){
		URL = feed;
		numOfStories = items;
	}

	@Override
	public void run() {
		titles = new ArrayList<String>();
		RssParser rp = new RssParser(URL);
		rp.parse();
		RssFeed feed = rp.getFeed();
		ArrayList<Item> items = feed.getItems();
		if(!items.isEmpty()){
			if(numOfStories > items.size())
				numOfStories = items.size();
			for(int i=0; i<numOfStories;i++){
				titles.add(items.get(i).title);
			}
		}
		System.out.println("[NEWS] RSS Updated");
	}
}

