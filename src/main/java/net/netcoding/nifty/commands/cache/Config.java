package net.netcoding.nifty.commands.cache;

import net.netcoding.nifty.common.api.plugin.MinecraftPlugin;
import net.netcoding.nifty.common.yaml.BukkitConfig;
import net.netcoding.nifty.core.util.misc.Vector;

public class Config extends BukkitConfig {

	public static final transient String CHAT_CHANNEL = "NiftyCommands";
	private static final transient String TABLE_PREFIX = CHAT_CHANNEL.toLowerCase() + "_";
	public static final transient String USER_TABLE = TABLE_PREFIX + "users";

	private double speed = 1.0;

	public Config(MinecraftPlugin plugin) {
		super(plugin.getDataFolder(), "config");
	}

	public double getSpeed() {
		return Math.max(this.speed, 0.5);
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