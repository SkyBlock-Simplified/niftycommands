package net.netcoding.niftycommands.commands;

import net.netcoding.niftybukkit.NiftyBukkit;
import net.netcoding.niftybukkit.minecraft.BukkitCommand;
import net.netcoding.niftybukkit.mojang.BukkitMojangProfile;
import net.netcoding.niftycommands.cache.Config;
import net.netcoding.niftycore.minecraft.scheduler.MinecraftScheduler;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FlickPlayer extends BukkitCommand {

	public FlickPlayer(JavaPlugin plugin) {
		super(plugin, "flick");
		this.setMaximumArgsLength(4);
	}

	@Override
	protected void onCommand(CommandSender sender, String alias, String[] args) throws Exception {
		Set<BukkitMojangProfile> flickProfiles = new HashSet<>();
		Vector velocity = new Vector(0.0, 1.5, 0.0);
		boolean useDefault = true;

		if (args.length == 1 || args.length == 4) {
			if (args[0].matches("^all|\\*")) {
				BukkitMojangProfile[] profiles = NiftyBukkit.getMojangRepository().searchByPlayer(this.getPlugin().getServer().getOnlinePlayers());
				Collections.addAll(flickProfiles, profiles);
			} else
				flickProfiles.add(NiftyBukkit.getMojangRepository().searchByUsername(args[0]));
		} else {
			if (isConsole(sender)) {
				this.getLog().error(sender, "The console must supply a playername when using this command!");
				return;
			}

			flickProfiles.add(NiftyBukkit.getMojangRepository().searchByPlayer((Player)sender));
		}

		if (args.length == 3 || args.length == 4) {
			try {
				velocity.setX(Double.parseDouble(args[args.length - 3]));
				velocity.setY(Double.parseDouble(args[args.length - 2]));
				velocity.setZ(Double.parseDouble(args[args.length - 1]));
			} catch (Exception ex) {
				this.showUsage(sender);
				return;
			}

			if (velocity.getX() < -9.0 || velocity.getX() > 9.0) {
				this.getLog().error(sender, "The X velocity argument must be between -9 and 9!");
				return;
			}

			if (velocity.getY() < -9.0 || velocity.getY() > 9.0) {
				this.getLog().error(sender, "The Y velocity argument must be between -9 and 9!");
				return;
			}

			if (velocity.getZ() < -9.0 || velocity.getZ() > 9.0) {
				this.getLog().error(sender, "The Z velocity argument must be between -9 and 9!");
				return;
			}
		} else
			useDefault = false;

		for (BukkitMojangProfile profile : flickProfiles) {
			if (!profile.isOnlineLocally()) continue;
			final Player player = profile.getOfflinePlayer().getPlayer();

			if (!useDefault)
				velocity = Config.getPitchVelocity(player.getLocation().getPitch(), 9.0);

			final boolean isFlying = player.isFlying();
			player.setFlying(false);
			player.setFlying(isFlying);
			player.leaveVehicle();
			player.setVelocity(velocity);

			MinecraftScheduler.schedule(new Runnable() {
				@Override
				public void run() {
					player.setFlying(isFlying);
				}
			}, 1);
		}

	}

}