package net.netcoding.niftycommands.commands;

import net.netcoding.niftybukkit.minecraft.BukkitCommand;
import net.netcoding.niftycommands.managers.Mob;
import net.netcoding.niftycore.util.NumberUtil;
import net.netcoding.niftycore.util.StringUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Tower extends BukkitCommand {

    private final static Mob DEFAULT_MOB = Mob.SHEEP;

	public Tower(JavaPlugin plugin) {
		super(plugin, "tower");
        this.setPlayerOnly();
        this.setMinimumArgsLength(0);
	}

	@Override
	protected void onCommand(CommandSender sender, String alias, String[] args) throws Exception {
        Location location = ((Player)sender).getTargetBlock((HashSet<Material>)null, 100).getLocation().add(0D, 1D, 0D);
        List<Mob> mobs = new ArrayList<>();
        String[] mobNames;
        int towers = 1;
        int start = 0;

        if (args.length > 0) {
            if (NumberUtil.isInt(args[0])) {
                towers = Integer.parseInt(args[0]);
                towers = towers > 0 ? towers : 1;
                start = 1;
            }
        }

        if (args.length > start)
            mobNames = StringUtil.split("[ ,]", StringUtil.implode(" ", args, start));
        else
            mobNames = new String[] { DEFAULT_MOB.getName(), DEFAULT_MOB.getName(), DEFAULT_MOB.getName() };

        for (String mobName : mobNames) {
            Mob mob = Mob.fromName(mobName);

            if (mob != null)
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
                } catch (Exception ignore) { }
            }
        }
	}

}