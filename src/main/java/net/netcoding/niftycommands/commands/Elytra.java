package net.netcoding.niftycommands.commands;

import net.netcoding.niftybukkit.minecraft.BukkitCommand;
import net.netcoding.niftybukkit.reflection.MinecraftProtocol;
import net.netcoding.niftybukkit.util.LocationUtil;
import net.netcoding.niftycommands.NiftyCommands;
import net.netcoding.niftycore.minecraft.scheduler.MinecraftScheduler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class Elytra extends BukkitCommand {

	private static final List<Material> ELYTRA_ASSIST = Arrays.asList(Material.LAVA, Material.STATIONARY_LAVA, Material.STATIONARY_WATER, Material.WATER);
	private static final String INFINITE_ELYTRA = "NC_ELYTRA";

	public Elytra(JavaPlugin plugin) {
		super(plugin, "elytra");
		this.setPlayerOnly();
		this.setMinimumArgsLength(0);
	}

	public static void enableElytra(final Player player) {
		int taskId = MinecraftScheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					if (player.isOnline()) {
						if (player.isGliding()) {
							Location location = player.getLocation();
							Vector velocity = player.getVelocity().clone();
							Vector vector = location.getDirection().clone();
							BlockFace facing = LocationUtil.yawToFace(location.getYaw());
							Material material = location.getBlock().getType();
							NiftyCommands plugin = NiftyCommands.getPlugin(NiftyCommands.class);

							// Ascending
							if (vector.getY() >= 0.0 || (plugin.hasPermissions(player, "elytra", "swim") && ELYTRA_ASSIST.contains(material))) {
								// Temporarily Maintain Increased Velocity
								if (facing == BlockFace.NORTH || facing == BlockFace.NORTH_EAST || facing == BlockFace.NORTH_WEST)
									vector.setZ(Math.max(vector.getZ(), velocity.getZ()));

								if (facing == BlockFace.EAST || facing == BlockFace.NORTH_EAST || facing == BlockFace.SOUTH_EAST)
									vector.setX(Math.min(vector.getX(), velocity.getX()));

								if (facing == BlockFace.SOUTH || facing == BlockFace.SOUTH_EAST || facing == BlockFace.SOUTH_WEST)
									vector.setZ(Math.min(vector.getZ(), velocity.getZ()));

								if (facing == BlockFace.WEST || facing == BlockFace.NORTH_WEST || facing == BlockFace.SOUTH_WEST)
									vector.setX(Math.max(vector.getX(), velocity.getX()));

								// Maintain Ascending Velocity
								velocity = vector;
							}

							player.setVelocity(velocity);
						}
					}
				} catch (Exception ignore) {
					disableElytra(player);
				}
			}
		}, 0L, 1L).getId();

		MetadataValue metadata = new FixedMetadataValue(NiftyCommands.getPlugin(NiftyCommands.class), taskId);
		player.setMetadata(INFINITE_ELYTRA, metadata);
	}

	private static void disableElytra(Player player) {
		List<MetadataValue> metadataList = player.getMetadata(INFINITE_ELYTRA);
		MinecraftScheduler.cancel(metadataList.get(0).asInt());
		player.removeMetadata(INFINITE_ELYTRA, NiftyCommands.getPlugin(NiftyCommands.class));
	}

	@Override
	protected void onCommand(CommandSender sender, String alias, String[] args) throws Exception {
		Player player = (Player)sender;

		if (MinecraftProtocol.getCurrentProtocol() < MinecraftProtocol.v1_9_pre1.getProtocol()) {
			this.getLog().error(sender, "This command requires a server running 1.9 or newer!");
			return;
		}

		if (player.hasMetadata(INFINITE_ELYTRA)) {
			disableElytra(player);
			this.getLog().message(sender, "Infinite elytra flight is now disabled!");
		} else {
			enableElytra(player);
			this.getLog().message(sender, "You now have infinite elytra flight!");
		}
	}

}