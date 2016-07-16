package net.netcoding.nifty.commands.commands;

import net.netcoding.nifty.common.api.plugin.Command;
import net.netcoding.nifty.common.api.plugin.MinecraftListener;
import net.netcoding.nifty.common.api.plugin.MinecraftPlugin;
import net.netcoding.nifty.common.minecraft.command.CommandSource;

public class Speed extends MinecraftListener {

	public Speed(MinecraftPlugin plugin) {
		super(plugin);
	}

	@Command(name = "speed",
			playerOnly = true
	)
	protected void onCommand(CommandSource source, String alias, String[] args) throws Exception {

	}

}