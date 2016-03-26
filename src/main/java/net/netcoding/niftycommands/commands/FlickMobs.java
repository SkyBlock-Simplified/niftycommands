package net.netcoding.niftycommands.commands;

import net.netcoding.niftybukkit.minecraft.BukkitCommand;
import net.netcoding.niftycommands.cache.Config;
import net.netcoding.niftycommands.managers.Mob;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class FlickMobs extends BukkitCommand {

	public FlickMobs(JavaPlugin plugin) {
		super(plugin, "flickmobs");
		this.setPlayerOnly();
		this.setMinimumArgsLength(0);
		this.setMaximumArgsLength(4);
	}

	@Override
	protected void onCommand(CommandSender sender, String alias, String[] args) throws Exception {
		Location location = ((Player)sender).getLocation();
		Vector velocity = new Vector(0.0, 1.5, 0.0);
		Mob mob = Mob.SHEEP;

		if (args.length == 1 || args.length == 4) {
			mob = Mob.fromName(args[0]);

			if (mob == null) {
				this.getLog().error(sender, "The name {{0}} is an invalid mob name!", args[0]);
				return;
			}
		}

		if (args.length == 3 || args.length == 4) {
			try {
				velocity.setX(Double.parseDouble(args[args.length - 3]));
				velocity.setY(Double.parseDouble(args[args.length - 2]));
				velocity.setZ(Double.parseDouble(args[args.length - 1]));
			} catch (Exception ex) {
				this.showUsage(sender);
				return;
			}

			if (velocity.getX() < -3.0 || velocity.getX() > 3.0) {
				this.getLog().error(sender, "The X velocity argument must be between -3 and 3!");
				return;
			}

			if (velocity.getY() < 0.0 || velocity.getY() > 3.0) {
				this.getLog().error(sender, "The Y velocity argument must be between 0 and 3!");
				return;
			}

			if (velocity.getZ() < -3.0 || velocity.getZ() > 3.0) {
				this.getLog().error(sender, "The Z velocity argument must be between -3 and 3!");
				return;
			}
		} else
			velocity = Config.getPitchVelocity(location.getPitch(), 3.0);

        try {
            Entity entity = mob.spawn(location);
            entity.setVelocity(velocity);
        } catch (Exception ex) {
            this.getLog().error(sender, "Unable to flick {{0}} mob!", ex, mob.getName());
        }
	}

}