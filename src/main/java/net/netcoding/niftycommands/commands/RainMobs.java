package net.netcoding.niftycommands.commands;

import net.netcoding.niftybukkit.minecraft.BukkitCommand;
import net.netcoding.niftycommands.managers.Mob;
import net.netcoding.niftycore.util.NumberUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class RainMobs extends BukkitCommand {

    private final static List<Mob> ALLOWED_MOBS = Arrays.asList(
        Mob.CHICKEN, Mob.COW, Mob.PIG, Mob.SHEEP, Mob.SQUID,
        Mob.VILLAGER, Mob.MUSHROOMCOW, Mob.SNOWMAN, Mob.OCELOT
    );

	public RainMobs(JavaPlugin plugin) {
        super(plugin, "rainmobs");
        this.setPlayerOnly();
        this.setMinimumArgsLength(0);
	}

	@Override
	protected void onCommand(CommandSender sender, String alias, String[] args) throws Exception {
        Location location = ((Player)sender).getLocation();
        int total = (args.length == 1 ? Integer.parseInt(args[0]) : 10);

        for (int i = 0; i < total; i++) {
            int rand = NumberUtil.rand(0, ALLOWED_MOBS.size() - 1);
            Mob mob = ALLOWED_MOBS.get(rand);

            while (true) {
                double x = NumberUtil.rand(-15, 15);
                double y = NumberUtil.rand(10, 15);
                double z = NumberUtil.rand(-15, 15);
                Vector position = new Vector(x, y, z);

                try {
                    Location mobLocation = location.add(position);

                    if (Material.AIR == mobLocation.getBlock().getType()) {
                        mob.spawn(mobLocation);
                        break;
                    }
                } catch (Exception ignore) { }
            }
        }
    }

}