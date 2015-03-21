package net.netcoding.niftycommands.cache;

import net.netcoding.niftybukkit.NiftyBukkit;
import net.netcoding.niftybukkit.yaml.annotations.Path;

import org.bukkit.plugin.java.JavaPlugin;

public class Config extends net.netcoding.niftybukkit.yaml.Config {

	public static final transient String CHAT_CHANNEL = "NiftyCommands";
	private static final transient String TABLE_PREFIX = CHAT_CHANNEL.toLowerCase() + "_";
	public static final transient String USER_TABLE = TABLE_PREFIX + "users";

	@Path("mysql.host")
	private String hostname = "localhost";

	@Path("mysql.user")
	private String username = "minecraft";

	@Path("mysql.pass")
	private String password = "";

	@Path("mysql.port")
	private int port = 3306;

	@Path("mysql.schema")
	private String schema = "nifty";

	public Config(JavaPlugin plugin) {
		super(plugin, "config");
	}

	public String getHost() {
		return this.hostname;
	}

	public String getUser() {
		return this.username;
	}

	public String getPass() {
		return this.password;
	}

	public int getPort() {
		return this.port;
	}

	public String getSchema() {
		return this.schema;
	}

	public static boolean isForcedCommand(String alias) {
		return alias.matches("^(g|global)?un[\\w]+");
	}

	public static boolean isGlobalCommand(String alias, String server) {
		return alias.matches("^g(lobal)?(un)?[\\w]+") || server.matches("^global|all|\\*$");
	}

	public static String getServerNameFromArgs(String[] args, boolean check) {
		String server = "*";

		if (NiftyBukkit.getBungeeHelper().isDetected()) {
			server = NiftyBukkit.getBungeeHelper().getServerName();

			if (check) {
				if (NiftyBukkit.getBungeeHelper().getServer(args[args.length - 1]) != null)
					server = args[args.length - 1];
			}
		}

		return server;
	}

}
