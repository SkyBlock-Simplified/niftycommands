package net.netcoding.niftycommands.cache;

import net.netcoding.niftycore.database.MySQL;
import net.netcoding.niftycore.yaml.SQLConfig;

import org.bukkit.plugin.java.JavaPlugin;

public class Config extends SQLConfig<MySQL> {

	public static final transient String CHAT_CHANNEL = "NiftyCommands";
	private static final transient String TABLE_PREFIX = CHAT_CHANNEL.toLowerCase() + "_";
	public static final transient String USER_TABLE = TABLE_PREFIX + "users";

	public Config(JavaPlugin plugin) {
		super(plugin.getDataFolder(), "config");
	}

}
