package net.netcoding.niftycommands.listeners;

import net.netcoding.niftybukkit.minecraft.BukkitListener;
import net.netcoding.niftybukkit.minecraft.events.PlayerPostLoginEvent;
import net.netcoding.niftycommands.commands.Elytra;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Connections extends BukkitListener {

	public Connections(JavaPlugin plugin) {
		super(plugin);
	}

	@EventHandler
	public void onPlayerPostLoginEvent(PlayerPostLoginEvent event) {
		Player player = event.getProfile().getOfflinePlayer().getPlayer();

		if (this.hasPermissions(player, "elytra", "login"))
			Elytra.enableElytra(player);
	}

}