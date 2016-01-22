package net.netcoding.niftycommands.listeners;

import java.sql.SQLException;

import net.netcoding.niftycore.database.notifications.DatabaseListener;
import net.netcoding.niftycore.database.notifications.DatabaseNotification;

public class Notifications implements DatabaseListener {

	@Override
	public void onDatabaseNotification(final DatabaseNotification databaseNotification) throws SQLException {
		
	}

}