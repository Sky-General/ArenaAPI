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
		player.setMaxHealth(1.0);
		player.setHealth(1.0);
		player.teleport(spawn);
		for (Player p : Bukkit.getOnlinePlayers()) {
			player.showPlayer(p);
			p.showPlayer(player);
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
