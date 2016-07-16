package net.netcoding.nifty.commands.commands;

import net.netcoding.nifty.common.api.plugin.Command;
import net.netcoding.nifty.common.api.plugin.MinecraftListener;
import net.netcoding.nifty.common.api.plugin.MinecraftPlugin;
import net.netcoding.nifty.common.minecraft.command.CommandSource;
import net.netcoding.nifty.common.minecraft.entity.EntityType;
import net.netcoding.nifty.common.minecraft.entity.living.human.Player;
import net.netcoding.nifty.common.minecraft.material.Material;
import net.netcoding.nifty.common.minecraft.region.Location;
import net.netcoding.nifty.core.util.NumberUtil;
import net.netcoding.nifty.core.util.misc.Vector;

import java.util.Arrays;
import java.util.List;

public class RainMobs extends MinecraftListener {

	private static final List<EntityType> ALLOWED_MOBS = Arrays.asList(
			EntityType.CHICKEN, EntityType.COW, EntityType.PIG, EntityType.SHEEP, EntityType.SQUID,
			EntityType.VILLAGER, EntityType.MUSHROOM_COW, EntityType.SNOWMAN, EntityType.OCELOT
	);

	public RainMobs(MinecraftPlugin plugin) {
		super(plugin);
	}

	@Command(name = "rainmobs",
			minimumArgs = 0,
            playerOnly = true
	)
	protected void onCommand(CommandSource source, String alias, String[] args) throws Exception {
		Location location = ((Player)source).getLocation();
		int total = (args.length == 1 ? Integer.parseInt(args[0]) : 10);

		for (int i = 0; i < total; i++) {
			int rand = NumberUtil.rand(0, ALLOWED_MOBS.size() - 1);
			EntityType mob = ALLOWED_MOBS.get(rand);

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