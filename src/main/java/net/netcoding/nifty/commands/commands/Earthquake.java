package net.netcoding.nifty.commands.commands;

import net.netcoding.nifty.common.api.plugin.Command;
import net.netcoding.nifty.common.api.plugin.MinecraftListener;
import net.netcoding.nifty.common.api.plugin.MinecraftPlugin;
import net.netcoding.nifty.common.minecraft.command.CommandSource;

public class Earthquake extends MinecraftListener {

	public Earthquake(MinecraftPlugin plugin) {
		super(plugin);
	}

	@Command(name = "earthquake")
	protected void onCommand(CommandSource source, String alias, String[] args) throws Exception {

	}

}