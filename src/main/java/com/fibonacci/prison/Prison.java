package com.fibonacci.prison;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.fibonacci.prison.commands.*;
import com.fibonacci.prison.enchants.CustomEnchants;
import com.fibonacci.prison.rank.Rank;
import com.fibonacci.prison.scoreboard.PlayerScoreboard;
import com.fibonacci.prison.user.PrisonPlayer;
import com.fibonacci.prison.user.PrisonPlayerUtils;
import com.fibonacci.prison.utils.JedisUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import javax.security.auth.login.LoginException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Prison extends JavaPlugin {

	public static Prison instance;
	public static JedisPool pool;

	private ProtocolManager protocolManager;
	private JDA jda;

	public static Map<String, PrisonPlayer> prisonPlayers = new HashMap<>();
	public List<Player> hungerPlayers = new ArrayList<>();

	public List<String> playerCommands = new ArrayList<>();

	@Override
	public void onEnable() {
		instance = this;
		protocolManager = ProtocolLibrary.getProtocolManager();

		try {
			jda = JDABuilder.createDefault("ODM1NjU4MDA2NzI0MDgzNzQy.YISpKg.WmGnpsUFfuJYo1942cG8IB25l5o").build();
		} catch (LoginException e) {
			e.printStackTrace();
		}

		String packageName = getClass().getPackage().getName();
		for (Class<? extends PacketAdapter> clazz : new Reflections(packageName + ".packets").getSubTypesOf(PacketAdapter.class)) {
			try {
				PacketAdapter adapter = clazz.getDeclaredConstructor().newInstance();
				protocolManager.addPacketListener(adapter);

			} catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		for (Class<?> clazz : new Reflections(packageName + ".listener").getSubTypesOf(Listener.class)) {
			try {
				Listener listener = (Listener) clazz.getDeclaredConstructor().newInstance();
				Bukkit.getPluginManager().registerEvents(listener, this);
			} catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		for (Class<? extends ListenerAdapter> clazz : new Reflections(packageName + ".discord").getSubTypesOf(ListenerAdapter.class)) {
			try {
				ListenerAdapter adapter = clazz.getDeclaredConstructor().newInstance();
				jda.addEventListener(adapter);
			} catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		for (Class<? extends FibCommand> clazz : new Reflections(packageName + ".commands").getSubTypesOf(FibCommand.class)) {
			try {
				FibCommand command = clazz.getDeclaredConstructor().newInstance();
				getCommand(command.getInfo().name()).setExecutor(command);
				playerCommands.add(command.getInfo().name());
				playerCommands.addAll(getCommand(command.getInfo().name()).getAliases());
			} catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		for (Class<? extends FibCommand> clazz : new Reflections(packageName + ".admin").getSubTypesOf(FibCommand.class)) {
			try {
				FibCommand command = clazz.getDeclaredConstructor().newInstance();
				getCommand(command.getInfo().name()).setExecutor(command);
			} catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		pool = new JedisPool(new JedisPoolConfig(), "localhost", Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT);

		updateScoreboards();
		saveData3Min();


		Rank.loadRanks();
		CustomEnchants.register();
		JedisUtils.loadMines();
		JedisUtils.loadWarps();
		JedisUtils.loadBannedPlayers();
		JedisUtils.loadMutedPlayers();

		keepHunger();

		Bukkit.getWorlds().forEach((world) -> {
			world.setGameRule(GameRule.KEEP_INVENTORY, true);
		});
	}

	@Override
	public void onDisable() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			PrisonPlayerUtils.savePrisonPlayer(player);
		}
		getJedis().close();
		JedisUtils.savePunishedPlayers();
	}


	public Map<String, PrisonPlayer> getPrisonPlayers() {
		return prisonPlayers;
	}

	public static Prison getInstance() {
		return instance;
	}

	public static Jedis getJedis() {
		try (Jedis jedis = pool.getResource()) {
			return jedis;
		}
	}

	public void updateScoreboards() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
			for (String player : PlayerScoreboard.scoreboards.keySet()) {
				PlayerScoreboard.updateScoreboard(Bukkit.getPlayer(player));
			}
		}, 0, 20);
	}

	public void saveData3Min() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
			for (Player player : Bukkit.getOnlinePlayers()) {
				PrisonPlayerUtils.savePrisonPlayerWithoutUnregistering(player);
			}
			JedisUtils.savePunishedPlayers();
		}, 0, 20 * 5 * 60);
	}

	public void keepHunger() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
			for (Player player : hungerPlayers) {
				player.setFoodLevel(20);
			}
		}, 0, 20);
	}

	public List<String> getPlayerCommands() {
		return playerCommands;
	}

	public JDA getJDA() {
		return jda;
	}




}
