package net.netcoding.nifty.commands.commands;

import net.netcoding.nifty.commands.cache.Config;
import net.netcoding.nifty.common.api.plugin.Command;
import net.netcoding.nifty.common.api.plugin.MinecraftListener;
import net.netcoding.nifty.common.api.plugin.MinecraftPlugin;
import net.netcoding.nifty.common.minecraft.command.CommandSource;
import net.netcoding.nifty.common.minecraft.entity.Entity;
import net.netcoding.nifty.common.minecraft.entity.EntityType;
import net.netcoding.nifty.common.minecraft.entity.living.human.Player;
import net.netcoding.nifty.common.minecraft.region.Location;
import net.netcoding.nifty.core.util.misc.Vector;

public class FlickMobs extends MinecraftListener {

	public FlickMobs(MinecraftPlugin plugin) {
		super(plugin);
	}

	@Command(name = "flickmobs",
			minimumArgs = 0,
			maximumArgs = 4,
			playerOnly = true
	)
	protected void onCommand(CommandSource source, String alias, String[] args) throws Exception {
		Location location = ((Player)source).getLocation();
		Vector velocity = new Vector(0.0, 1.5, 0.0);
		EntityType mob = EntityType.SHEEP;

		if (args.length == 1 || args.length == 4) {
			mob = EntityType.getByName(args[0]);

			if (mob == null) {
				this.getLog().error(source, "The name {{0}} is an invalid mob name!", args[0]);
				return;
			}
		}

		if (args.length == 3 || args.length == 4) {
			try {
				velocity.setX(Double.parseDouble(args[args.length - 3]));
				velocity.setY(Double.parseDouble(args[args.length - 2]));
				velocity.setZ(Double.parseDouble(args[args.length - 1]));
			} catch (Exception ex) {
				this.showUsage(source);
				return;
			}

			if (velocity.getX() < -3.0 || velocity.getX() > 3.0) {
				this.getLog().error(source, "The X velocity argument must be between -3 and 3!");
				return;
			}

			if (velocity.getY() < 0.0 || velocity.getY() > 3.0) {
				this.getLog().error(source, "The Y velocity argument must be between 0 and 3!");
				return;
			}

			if (velocity.getZ() < -3.0 || velocity.getZ() > 3.0) {
				this.getLog().error(source, "The Z velocity argument must be between -3 and 3!");
				return;
			}
		} else
			velocity = Config.getPitchVelocity(location.getPitch(), 3.0);

        try {
            Entity entity = mob.spawn(location);
            entity.setVelocity(velocity);
        } catch (Exception ex) {
            this.getLog().error(source, "Unable to flick {{0}} mob!", ex, mob.getName());
        }
	}

}