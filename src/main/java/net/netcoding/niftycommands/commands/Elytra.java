package net.netcoding.niftycommands.commands;

import net.netcoding.niftybukkit.minecraft.BukkitCommand;
import net.netcoding.niftybukkit.reflection.MinecraftProtocol;
import net.netcoding.niftycore.minecraft.scheduler.MinecraftScheduler;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.List;

public class Elytra extends BukkitCommand {

	private static final String INFINITE_ELYTRA = "NC_ELYTRA";

	public Elytra(JavaPlugin plugin) {
		super(plugin, "elytra");
		this.setPlayerOnly();
		this.setMinimumArgsLength(0);
	}

	private void removeElytra(Player player) {
		List<MetadataValue> metadataList = player.getMetadata(INFINITE_ELYTRA);
		MinecraftScheduler.cancel(metadataList.get(0).asInt());
		player.removeMetadata(INFINITE_ELYTRA, this.getPlugin());
	}

	@Override
	protected void onCommand(CommandSender sender, String alias, String[] args) throws Exception {
		final Player player = (Player)sender;

		if (MinecraftProtocol.getCurrentProtocol() < MinecraftProtocol.v1_9_pre1.getProtocol()) {
			this.getLog().error(sender, "This command requires a server running 1.9 or newer!");
			return;
		}

		if (player.hasMetadata(INFINITE_ELYTRA)) {
			this.removeElytra(player);
			this.getLog().message(sender, "Infinite elytra flight is now disabled!");
		} else {
			int taskId = MinecraftScheduler.schedule(new Runnable() {
				@Override
				public void run() {
					try {
						if (player.isOnline()) {
							if (player.isGliding()) {
								Location location = player.getLocation();
								Vector velocity = player.getVelocity().clone();
								Vector vector = location.getDirection();

								if (vector.getY() > 0.0)
									velocity = vector;

								player.setVelocity(velocity);
							}
						}
					} catch (Exception ignore) {
						removeElytra(player);
					}
				}
			}, 0L, 3L).getId();

			MetadataValue metadata = new FixedMetadataValue(this.getPlugin(), taskId);
			player.setMetadata(INFINITE_ELYTRA, metadata);
			this.getLog().message(sender, "You now have infinite elytra flight!");
		}
	}

}