package net.creativegames.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public void onEnable(){
		System.out.println("Arena API");
//		new ListenerClass(this);
		for(Player player : Bukkit.getOnlinePlayers()){
			ArenaAPI.resetPlayer(player, player.getLocation());
		}
	}

}
