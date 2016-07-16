package net.netcoding.nifty.commands;

import net.netcoding.nifty.commands.cache.Config;
import net.netcoding.nifty.commands.commands.*;
import net.netcoding.nifty.commands.listeners.Misc;
import net.netcoding.nifty.common.api.plugin.MinecraftPlugin;
import net.netcoding.nifty.core.database.factory.SQLWrapper;

public class NiftyCommands extends MinecraftPlugin {

	private static Config PLUGIN_CONFIG;

	@Override
	public void onEnable() {
        try {
            this.getLog().console("Loading SQL Config");
            (PLUGIN_CONFIG = new Config(this)).init();

            //if (PLUGIN_CONFIG.getSQL() == null) {
            //    this.getLog().console("Incomplete MySQL Configuration!");
                //this.setEnabled(false);
                //return;
            //}
        } catch (Exception ex) {
            this.getLog().console("Invalid MySQL Configuration!", ex);
            //this.setEnabled(false);
            return;
        }

		PLUGIN_CONFIG.startWatcher();

        /*try {
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

        this.getLog().console("Registering Commands");
		new Derp(this);
		new Earthquake(this);
		//new Elytra(this);
        new FlickPlayer(this);
        new FlickMobs(this);
        new FlipBlocks(this);
        new RainMobs(this);
		new Sudo(this);
		new Teleport(this);
        new Tower(this);

		this.getLog().console("Registering Listeners");
		new Misc(this);
	}

	@Override
	public void onDisable() {
		if (getSQL() != null)
            getSQL().removeListeners();
	}

	public static Config getPluginConfig() {
		return PLUGIN_CONFIG;
	}

    public static SQLWrapper getSQL() {
	    return null;
        //return getPluginConfig().getSQL();
    }

}