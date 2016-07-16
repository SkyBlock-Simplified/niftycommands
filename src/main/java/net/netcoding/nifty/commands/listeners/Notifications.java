package net.netcoding.nifty.commands.listeners;

import net.netcoding.nifty.core.database.notifications.DatabaseListener;
import net.netcoding.nifty.core.database.notifications.DatabaseNotification;

import java.sql.SQLException;

public class Notifications implements DatabaseListener {

	@Override
	public void onDatabaseNotification(final DatabaseNotification databaseNotification) throws SQLException {

	}

}