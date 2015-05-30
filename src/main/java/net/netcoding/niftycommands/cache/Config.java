package net.netcoding.niftycommands.cache;

import net.netcoding.niftycore.database.MySQL;
import net.netcoding.niftycore.yaml.SQLConfig;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Config extends SQLConfig<MySQL> {

	public static final transient String CHAT_CHANNEL = "NiftyCommands";
	private static final transient String TABLE_PREFIX = CHAT_CHANNEL.toLowerCase() + "_";
	public static final transient String USER_TABLE = TABLE_PREFIX + "users";

	public Config(JavaPlugin plugin) {
		super(plugin.getDataFolder(), "config");
	}

    public static Vector getPitchVelocity(float pitch, double velocity) {
        Vector vector = new Vector();

        if (pitch >= 0 && pitch <= 45.0)
            vector.setZ(velocity);

        if (pitch >= 45.0 && pitch <= 135.0)
            vector.setX(-velocity);

        if (pitch >= 135.0 && pitch <= 225.0)
            vector.setZ(-velocity);

        if (pitch >= 225.0 && pitch <= 315.0)
            vector.setX(velocity);

        if (pitch >= 315.0 && pitch <= 360.0)
            vector.setZ(velocity);

        return vector;
    }

}