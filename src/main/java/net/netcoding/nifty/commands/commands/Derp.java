package net.netcoding.nifty.commands.commands;

import net.netcoding.nifty.common.Nifty;
import net.netcoding.nifty.common.api.plugin.Command;
import net.netcoding.nifty.common.api.plugin.MinecraftListener;
import net.netcoding.nifty.common.api.plugin.MinecraftPlugin;
import net.netcoding.nifty.common.minecraft.command.CommandSource;
import net.netcoding.nifty.common.minecraft.entity.living.human.Player;
import net.netcoding.nifty.common.minecraft.region.Location;
import net.netcoding.nifty.common.mojang.MinecraftMojangProfile;
import net.netcoding.nifty.core.mojang.exceptions.ProfileNotFoundException;
import net.netcoding.nifty.core.util.ListUtil;
import net.netcoding.nifty.core.util.NumberUtil;

public class Derp extends MinecraftListener {

	public Derp(MinecraftPlugin plugin) {
		super(plugin);
	}

	@Command(name = "derp",
			minimumArgs = 0,
			playerOnly = true
	)
	protected void onCommand(CommandSource source, String alias, String[] args) throws Exception {
		final MinecraftMojangProfile profile;

		if (ListUtil.notEmpty(args)) {
			try {
				profile = Nifty.getMojangRepository().searchByUsername(args[0]);
			} catch (ProfileNotFoundException pnfex) {
				this.getLog().error(source, "Unable to locate the profile for {{0}}!", args[0]);
				return;
			}
		} else
			profile = Nifty.getMojangRepository().searchByPlayer((Player)source);

		Nifty.getScheduler().schedule(() -> {
			for (int i = 0; i < 50; i++) {
				if (!profile.isOnlineLocally()) continue;
				float yaw = NumberUtil.rand(0, 359);
				float pitch = NumberUtil.rand(0, 90) * (i % 2 == 0 ? -1 : 1);
				Location location = profile.getOfflinePlayer().getPlayer().getLocation();
				// TODO

				try {
					Thread.sleep(50);
				} catch (Exception ignore) {
				}
			}
		});
	}

}