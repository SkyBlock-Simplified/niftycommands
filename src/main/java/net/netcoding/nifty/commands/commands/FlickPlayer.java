package net.netcoding.nifty.commands.commands;

import net.netcoding.nifty.commands.cache.Config;
import net.netcoding.nifty.common.Nifty;
import net.netcoding.nifty.common.api.plugin.Command;
import net.netcoding.nifty.common.api.plugin.MinecraftListener;
import net.netcoding.nifty.common.api.plugin.MinecraftPlugin;
import net.netcoding.nifty.common.minecraft.command.CommandSource;
import net.netcoding.nifty.common.minecraft.entity.living.human.Player;
import net.netcoding.nifty.common.mojang.MinecraftMojangProfile;
import net.netcoding.nifty.core.util.misc.Vector;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FlickPlayer extends MinecraftListener {

	public FlickPlayer(MinecraftPlugin plugin) {
		super(plugin);
	}

	@Command(name = "flick",
			maximumArgs = 4
	)
	protected void onCommand(CommandSource source, String alias, String[] args) throws Exception {
		Set<MinecraftMojangProfile> flickProfiles = new HashSet<>();
		Vector velocity = new Vector(0.0, 1.5, 0.0);
		boolean useDefault = false;

		if (args.length == 1 || args.length == 4) {
			if (args[0].matches("^all|\\*")) {
				MinecraftMojangProfile[] profiles = Nifty.getMojangRepository().searchByPlayer(this.getPlugin().getServer().getPlayerList());
				Collections.addAll(flickProfiles, profiles);
			} else
				flickProfiles.add(Nifty.getMojangRepository().searchByUsername(args[0]));
		} else {
			if (isConsole(source)) {
				this.getLog().error(source, "The console must supply a playername when using this command!");
				return;
			}

			flickProfiles.add(Nifty.getMojangRepository().searchByPlayer((Player)source));
		}

		if (args.length == 3 || args.length == 4) {
			try {
				velocity.setX(Double.parseDouble(args[args.length - 3]));
				velocity.setY(Double.parseDouble(args[args.length - 2]));
				velocity.setZ(Double.parseDouble(args[args.length - 1]));
			} catch (Exception ex) {
				this.showUsage(source);
				return;
			}

			if (velocity.getX() < -9.0 || velocity.getX() > 9.0) {
				this.getLog().error(source, "The X velocity argument must be between -9 and 9!");
				return;
			}

			if (velocity.getY() < -9.0 || velocity.getY() > 9.0) {
				this.getLog().error(source, "The Y velocity argument must be between -9 and 9!");
				return;
			}

			if (velocity.getZ() < -9.0 || velocity.getZ() > 9.0) {
				this.getLog().error(source, "The Z velocity argument must be between -9 and 9!");
				return;
			}
		} else
			useDefault = true;

		for (MinecraftMojangProfile profile : flickProfiles) {
			if (!profile.isOnlineLocally()) continue;
			final Player player = profile.getOfflinePlayer().getPlayer();

			if (useDefault)
				velocity = Config.getPitchVelocity(player.getLocation().getPitch(), 1.5);

			player.leaveVehicle();
			player.setFlying(false);
			player.setVelocity(velocity);
		}

	}

}