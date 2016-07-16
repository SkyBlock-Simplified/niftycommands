package net.netcoding.nifty.commands.commands;

import net.netcoding.nifty.common.api.plugin.Command;
import net.netcoding.nifty.common.api.plugin.MinecraftListener;
import net.netcoding.nifty.common.api.plugin.MinecraftPlugin;
import net.netcoding.nifty.common.minecraft.command.CommandSource;
import net.netcoding.nifty.common.minecraft.entity.Entity;
import net.netcoding.nifty.common.minecraft.entity.EntityType;
import net.netcoding.nifty.common.minecraft.entity.living.human.Player;
import net.netcoding.nifty.common.minecraft.region.Location;
import net.netcoding.nifty.core.util.NumberUtil;
import net.netcoding.nifty.core.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class Tower extends MinecraftListener {

	private static final EntityType DEFAULT_MOB = EntityType.SHEEP;

	public Tower(MinecraftPlugin plugin) {
		super(plugin);
	}

	@Command(name = "tower",
            minimumArgs = 0,
            playerOnly = true
	)
	protected void onCommand(CommandSource source, String alias, String[] args) throws Exception {
		Location location = ((Player)source).getTargetBlock(null, 100).getLocation().add(0D, 1D, 0D);
		List<EntityType> mobs = new ArrayList<>();
		String[] mobNames;
		int towers = 1;
		int start = 0;

		if (args.length > 0) {
			if (NumberUtil.isNumber(args[0])) {
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
			EntityType mob = EntityType.getByName(mobName);

			if (mob != null)
				mobs.add(mob);
		}

		for (int i = 0; i < towers; i++) {
			Entity lastEntity = null;

			for (EntityType mob : mobs) {
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