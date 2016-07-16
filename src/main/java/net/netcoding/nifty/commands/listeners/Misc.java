package net.netcoding.nifty.commands.listeners;

import net.netcoding.nifty.commands.NiftyCommands;
import net.netcoding.nifty.common.Nifty;
import net.netcoding.nifty.common.api.plugin.MinecraftListener;
import net.netcoding.nifty.common.api.plugin.Event;
import net.netcoding.nifty.common.api.plugin.MinecraftPlugin;
import net.netcoding.nifty.common.minecraft.block.BlockFace;
import net.netcoding.nifty.common.minecraft.entity.living.human.Player;
import net.netcoding.nifty.common.minecraft.event.player.PlayerMoveEvent;
import net.netcoding.nifty.common.minecraft.material.Material;
import net.netcoding.nifty.common.minecraft.region.Location;
import net.netcoding.nifty.common.mojang.MinecraftMojangProfile;
import net.netcoding.nifty.common.util.LocationUtil;
import net.netcoding.nifty.core.util.misc.Vector;

import java.util.Arrays;
import java.util.List;

public class Misc extends MinecraftListener {

	private static final List<Material> ELYTRA_ASSIST = Arrays.asList(Material.LAVA, Material.STATIONARY_LAVA, Material.STATIONARY_WATER, Material.WATER);

	public Misc(MinecraftPlugin plugin) {
		super(plugin);
	}

	@Event
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();

		if (this.hasPermissions(player, "eyltra")) {
			MinecraftMojangProfile profile = Nifty.getMojangRepository().searchByPlayer(player);

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