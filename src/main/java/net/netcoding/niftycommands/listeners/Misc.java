package net.netcoding.niftycommands.listeners;

import net.netcoding.niftybukkit.NiftyBukkit;
import net.netcoding.niftybukkit.minecraft.BukkitListener;
import net.netcoding.niftybukkit.mojang.BukkitMojangProfile;
import net.netcoding.niftybukkit.util.LocationUtil;
import net.netcoding.niftycommands.NiftyCommands;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class Misc extends BukkitListener {

	private static final List<Material> ELYTRA_ASSIST = Arrays.asList(Material.LAVA, Material.STATIONARY_LAVA, Material.STATIONARY_WATER, Material.WATER);

	public Misc(JavaPlugin plugin) {
		super(plugin);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();

		if (this.hasPermissions(player, "eyltra")) {
			BukkitMojangProfile profile = NiftyBukkit.getMojangRepository().searchByPlayer(player);

			if (profile.isGliding()) {
				Location location = player.getLocation();
				double speed = NiftyCommands.getPluginConfig().getSpeed();
				Vector direction = location.getDirection().multiply(0.4 * speed); // Adjust Speed
				BlockFace facing = LocationUtil.yawToFace(location.getYaw());
				Vector multiplyVector = player.getVelocity();
				boolean adjust = true;

				// Swimming
				if (!this.hasPermissions(player, "elytra", "swim") && ELYTRA_ASSIST.contains(location.getBlock().getType()))
					adjust = false;

				// Maintain Sideways Flight
				if (adjust) {
					if (facing == BlockFace.NORTH || facing == BlockFace.NORTH_EAST || facing == BlockFace.NORTH_WEST)
						multiplyVector.setZ(Math.max(direction.getZ(), multiplyVector.getZ()));

					if (facing == BlockFace.EAST || facing == BlockFace.NORTH_EAST || facing == BlockFace.SOUTH_EAST)
						multiplyVector.setX(Math.min(direction.getX(), multiplyVector.getX()));

					if (facing == BlockFace.SOUTH || facing == BlockFace.SOUTH_EAST || facing == BlockFace.SOUTH_WEST)
						multiplyVector.setZ(Math.min(direction.getZ(), multiplyVector.getZ()));

					if (facing == BlockFace.WEST || facing == BlockFace.NORTH_WEST || facing == BlockFace.SOUTH_WEST)
						multiplyVector.setX(Math.max(direction.getX(), multiplyVector.getX()));
				}

				// Maintain Upward Flight
				if (direction.getY() > 0.0) {
					if (multiplyVector.getY() < direction.getY())
						multiplyVector.setY(direction.getY());
				}

				player.setVelocity(multiplyVector);
			}
		}
	}

}