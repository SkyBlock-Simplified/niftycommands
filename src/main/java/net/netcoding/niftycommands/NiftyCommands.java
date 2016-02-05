package net.netcoding.niftycommands;

import net.netcoding.niftybukkit.minecraft.BukkitPlugin;
import net.netcoding.niftycommands.cache.Config;
import net.netcoding.niftycommands.commands.*;
import net.netcoding.niftycore.database.factory.SQLWrapper;

public class NiftyCommands extends BukkitPlugin {

	private static Config PLUGIN_CONFIG;

	@Override
	public void onEnable() {
        /*try {
            this.getLog().console("Loading SQL Config");
            (PLUGIN_CONFIG = new Config(this)).init();

            if (PLUGIN_CONFIG.getSQL() == null) {
                this.getLog().console("Incomplete MySQL Configuration!");
                this.setEnabled(false);
                return;
            }
        } catch (Exception ex) {
            this.getLog().console("Invalid MySQL Configuration!", ex);
            this.setEnabled(false);
            return;
        }

        try {
            Notifications notifications = new Notifications();
            getSQL().addListener(Config.FORMAT_TABLE, notifications);
            getSQL().addListener(Config.CENSOR_TABLE, notifications);
            getSQL().addListener(Config.USER_TABLE, notifications);
            getSQL().addListener(Config.USER_FLAGS_TABLE, notifications);
        } catch (Exception ex) {
            this.getLog().console(ex);
            this.setEnabled(false);
            return;
        }

        this.getLog().console("Updating MySQL Tables & Data");
        if (!this.setupTables()) {
            this.getLog().console("Unable to setup MySQL Tables & Data!");
            this.setEnabled(false);
            return;
        }*/

        try {
            this.getLog().console("Loading Config");
            (PLUGIN_CONFIG = new Config(this)).init();
        } catch (Exception ignore) { }

        this.getLog().console("Registering Commands");
        new FlickPlayer(this);
        new FlickMobs(this);
        new FlipBlocks(this);
        new RainMobs(this);
        new Tower(this);
	}

	@Override
	public void onDisable() {
		if (getSQL() != null)
            getSQL().removeListeners();
	}

    public static SQLWrapper getSQL() {
        return PLUGIN_CONFIG.getSQL();
    }

}