package net.portalkings.api;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ArenaAPI {
	/* String = World name */
	public static Map<String, Boolean> arenaStarted = new HashMap<String, Boolean>();
	/* String = World name */
	public static Map<String, Integer> arenaSize = new HashMap<>();
	/* String = Player name */
	public static Map<String, PlayerState> playerState = new HashMap<>();
	/* String = Player name */
	public static Map<String, World> redTeam = new HashMap<String, World>();
	/* String = Player name */
	public static Map<String, World> blueTeam = new HashMap<String, World>();

	private static String prefix = ChatColor.translateAlternateColorCodes('&',
			"&a[&bNAME&b]");

	private static String size = ChatColor.translateAlternateColorCodes('&',
			"&a(&bl&a/&b35&a)");

	public static void setSize(World arena, int size) {
		arenaSize.put(arena.getName(), size);
	}

	public static void addPlayerToArena(Player player, World arena,
			String gameName, Boolean hasTeams) {
		prefix = prefix.replaceFirst("NAME", gameName);
		if (!arenaSize.containsKey(arena.getName()))
			arenaSize.put(arena.getName(), 0);
		if (arenaSize.get(arena.getName()) == 35) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&',
					prefix + " &cSorry. That arena is full."));
			return;
		} else {
			if (hasTeams) {
				size = size.replaceFirst("l", "" + arena.getPlayers().size());
				if (redTeam.size() > blueTeam.size()) {
					/* Adding player to the Blue Team. */
					blueTeam.put(player.getName(), arena);
					for (Player p : Bukkit.getOnlinePlayers())
						if (p.getWorld().equals(arena) || p.equals(player))
							p.sendMessage(ChatColor
									.translateAlternateColorCodes('&', prefix
											+ " &1" + player.getName()
											+ "&b has joined the game! " + size));

				}
				if (blueTeam.size() > redTeam.size()) {
					/* Adding player to the Blue Team. */
					redTeam.put(player.getName(), arena);
					for (Player p : Bukkit.getOnlinePlayers())
						if (p.getWorld().equals(arena) || p.equals(player))
							p.sendMessage(ChatColor
									.translateAlternateColorCodes('&', prefix
											+ " &c" + player.getName()
											+ "&b has joined the game! " + size));

				}
			}

		}
	}

	@SuppressWarnings("deprecation")
	public static void resetPlayer(Player player, Location spawn) {
		player.setGameMode(GameMode.SURVIVAL);
		playerState.put(player.getName(), PlayerState.INLOBBY);
		player.setFlying(false);
		player.setAllowFlight(false);
		player.setMaxHealth(2.0);
		player.setHealth(2.0);
		player.teleport(spawn);
		for (Player p : Bukkit.getOnlinePlayers()) {
			player.showPlayer(p);
			p.showPlayer(player);
		}
	}
	
	public static void checkArena(World arena, Location spawn, boolean hasTeams, String gameName, Player winner){
		if(hasTeams){
			prefix = prefix.replaceFirst("NAME", gameName);
			int redTeamSize = 0;
			int blueTeamSize = 0;
			for (int r1 = redTeam.size(); r1 != 0; r1--)
				if (redTeam.containsValue(arena))
					redTeamSize = redTeamSize + 1;
			for (int b1 = blueTeam.size(); b1 != 0; b1--)
				if (blueTeam.containsValue(arena))
					blueTeamSize = blueTeamSize + 1;
			if (!arenaStarted.get(arena.getName()))
				return;
			if (redTeamSize == 0) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (player.getWorld().equals(arena)) {
						player.sendMessage(ChatColor.translateAlternateColorCodes(
								'&', prefix + " The &bblue &8team won!"));
						arenaStarted.put(arena.getName(), false);
						resetPlayer(player, spawn);
						return;
					}
				}
			}
			if (blueTeamSize == 0) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (player.getWorld().equals(arena)) {
						player.sendMessage(ChatColor.translateAlternateColorCodes(
								'&', prefix + " The &cred &8team won!"));
						player.teleport(spawn);
						arenaStarted.put(arena.getName(), false);
						resetPlayer(player, spawn);
						return;
					}
				}
			}
		
		}
		if(!hasTeams){
			prefix = prefix.replaceFirst("NAME", gameName);
			for(Player p : arena.getPlayers()){
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + winner.getName() + " has won!"));
				resetPlayer(p, spawn);
				arenaStarted.put(arena.getName(), false);
				p.teleport(spawn);
			}
		}
	}

	public static void endArena(World arena) {
		arenaStarted.put(arena.getName(), false);
		arenaSize.put(arena.getName(), 0);

	}
	public static PlayerState getPlayerState(Player player){
		return playerState.get(player.getName());
	}

	public static int getSize(World arena) {
		if (arenaSize.containsKey(arena.getName())) {
			return arenaSize.get(arena.getName());
		} else {
			System.out.println("Arena does not exist!");
			return -1;
		}
	}

	public static Boolean hasStarted(World arena) {
		return arenaStarted.get(arena.getName());
	}

	public static void setHasStarted(World arena, boolean started) {
		arenaStarted.put(arena.getName(), started);
	}

	public static void setPlayerState(PlayerState state, Player player) {
		playerState.put(player.getName(), state);
	}
}
