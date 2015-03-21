package net.netcoding.niftycommands.listeners;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.netcoding.niftybukkit.NiftyBukkit;
import net.netcoding.niftybukkit.database.factory.ResultCallback;
import net.netcoding.niftybukkit.database.notifications.DatabaseListener;
import net.netcoding.niftybukkit.database.notifications.DatabaseNotification;
import net.netcoding.niftybukkit.database.notifications.TriggerEvent;
import net.netcoding.niftycommands.cache.Config;

public class Notifications implements DatabaseListener {

	@Override
	public void onDatabaseNotification(final DatabaseNotification databaseNotification) throws SQLException {
		TriggerEvent event = databaseNotification.getEvent();
		String table = databaseNotification.getTable();
	}

}