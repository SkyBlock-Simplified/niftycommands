package net.netcoding.niftycommands.commands;

import net.netcoding.niftybukkit.minecraft.BukkitCommand;
import net.netcoding.niftycommands.managers.Mob;
import net.netcoding.niftycore.util.NumberUtil;
import net.netcoding.niftycore.util.StringUtil;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Tower extends BukkitCommand {

    private final static Mob DEFAULT_MOB = Mob.SHEEP;

	public Tower(JavaPlugin plugin) {
		super(plugin, "tower");
        this.setPlayerOnly();
	}

	@Override
	protected void onCommand(CommandSender sender, String alias, String[] args) throws Exception {
        Location location = ((Player)sender).getLocation();
        int towers = 1;
        int start = 0;

        if (NumberUtil.isInt(args[0])) {
            towers = Integer.parseInt(args[0]);
            start = 1;
        }

        String[] mobNames = StringUtil.split(" ", StringUtil.implode(" ", args, start));
        List<Mob> mobs = new ArrayList<>();

        for (String mobName : mobNames) {
            Mob mob = Mob.fromName(mobName);
            if (mob == null) continue;
            mobs.add(mob);
        }

        for (int i = 0; i < towers; i++) {
            Entity lastEntity = null;

            for (Mob mob : mobs) {
                try {
                    Entity entity = mob.spawn(location);

                    if (lastEntity != null)
                        lastEntity.setPassenger(entity);

                    lastEntity = entity;
                } catch (Exception ex) { }
            }
        }
	}

}