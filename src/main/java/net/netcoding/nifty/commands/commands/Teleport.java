package net.netcoding.nifty.commands.commands;

import net.netcoding.nifty.common.Nifty;
import net.netcoding.nifty.common.api.plugin.Command;
import net.netcoding.nifty.common.api.plugin.MinecraftListener;
import net.netcoding.nifty.common.api.plugin.MinecraftPlugin;
import net.netcoding.nifty.common.minecraft.command.CommandSource;
import net.netcoding.nifty.common.minecraft.entity.living.human.Player;
import net.netcoding.nifty.common.minecraft.event.player.PlayerTeleportEvent;
import net.netcoding.nifty.common.minecraft.region.Location;
import net.netcoding.nifty.common.minecraft.region.World;
import net.netcoding.nifty.common.mojang.MinecraftMojangProfile;
import net.netcoding.nifty.core.mojang.exceptions.ProfileNotFoundException;
import net.netcoding.nifty.core.util.NumberUtil;
import net.netcoding.nifty.core.util.StringUtil;

public class Teleport extends MinecraftListener {

	public Teleport(MinecraftPlugin plugin) {
		super(plugin);
	}

	@Command(name = "teleport",
			usages = {
					@Command.Usage(index = 0, match = "tpt(oggle)?")
			}
	)
	protected void onCommand(CommandSource source, String alias, String[] args) throws Exception {
		MinecraftMojangProfile target = null;
		String targetName = args[0];
		boolean tryTarget;

		if (isConsole(source) && args.length < 2) {
			this.getLog().error(source, "You must pass a target player and destination from console!");
			return;
		}

		if (args.length > 2 && (NumberUtil.isNumber(args[0]) || NumberUtil.isNumber(args[1]))) {
			tryTarget = isConsole(source) || !NumberUtil.isNumber(args[0]);

			if (!tryTarget) {
				target = Nifty.getMojangRepository().searchByPlayer((Player)source);
				args = StringUtil.split(",", StringUtil.implode(",", args, 1, args.length));
			}
		} else
			tryTarget = true;

		if (tryTarget) {
			try {
				target = Nifty.getMojangRepository().searchByUsername(targetName);
			} catch (ProfileNotFoundException pnfex) {
				this.getLog().error(source, "Unable to locate the profile of {{0}}!", targetName);
				return;
			}

			args = StringUtil.split(",", StringUtil.implode(",", args, 1, args.length));
		}

		if (args.length == 0) {
			Player player = (Player)source;
			player.teleport(target.getOfflinePlayer().getPlayer().getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
			this.getLog().message(source, "You have teleported to {{0}}.", target.getName());
		} else if (args.length == 1) {
			String destinationName = args[0];

			try {
				MinecraftMojangProfile destination = Nifty.getMojangRepository().searchByUsername(destinationName);
				target.getOfflinePlayer().getPlayer().teleport(destination.getOfflinePlayer().getPlayer().getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);

				if (!source.getName().equals(target.getName()))
					this.getLog().message(source, "You have teleported {{0}} to {{1}}.", target.getName(), destination.getName());

				this.getLog().message(target.getOfflinePlayer().getPlayer(), "You have teleported to {{0}}.", destination.getName());
			} catch (ProfileNotFoundException pnfex) {
				this.getLog().error(source, "Unable to teleport {{0}} to {{1}}! {{1}} not found!", target, destinationName);
			}
		} else if (args.length == 3) {
			try {
				Player player = target.getOfflinePlayer().getPlayer();
				double x = Double.parseDouble(args[0]);
				double y = Double.parseDouble(args[1]);
				double z = Double.parseDouble(args[2]);
				World world = args.length > 3 ? Nifty.getServer().getWorld(args[3]) : player.getWorld();
				player.teleport(Location.of(world, x, y, z));
			} catch (Exception ex) {
				this.showUsage(source);
			}
		} else
			this.showUsage(source);
	}

}