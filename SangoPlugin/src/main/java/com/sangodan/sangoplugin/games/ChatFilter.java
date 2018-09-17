package com.sangodan.sangoplugin.games;

import java.util.Locale;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatFilter implements Listener {

	@SuppressWarnings("unused")
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		String message = event.getMessage();
		String goodmessage = new String(message);
		message = message.toLowerCase(Locale.ENGLISH);
		Player player = event.getPlayer();
		Set<Player> receivers = event.getRecipients();
		if(message.contains(" ez ") || message.equals("ez") || message.contains(" ez") || message.contains("ez ")) {
			goodmessage = "I am bad (hail sangodan)";
		} else if (message.contains("sangodan") || message.contains("sangoman") || message.contains("sangosucc")){
			goodmessage = "I used the lord SANGO name in vain";
		} else if (message.contains("winner201")) {
			goodmessage = "Winner201 is bad";
		}
		event.setMessage(goodmessage);
	}
}
