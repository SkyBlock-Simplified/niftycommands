package net.netcoding.niftycommands.commands;

import net.netcoding.niftybukkit.minecraft.BukkitCommand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class FlipBlocks extends BukkitCommand {

	private final static String WORLDEDIT = "WorldEdit";

	public FlipBlocks(JavaPlugin plugin) {
		super(plugin, "flipblocks");
		this.setPlayerOnly();
		this.setMinimumArgsLength(0);
		this.setMaximumArgsLength(3);
	}

	@Override
	protected void onCommand(CommandSender sender, String alias, String[] args) throws Exception {
		PluginManager manager = this.getPlugin().getServer().getPluginManager();
		Vector velocity = new Vector(0, 1.5, 0);

		if (!manager.isPluginEnabled(WORLDEDIT)) {
			this.getLog().error(sender, "This command requires {{0}}!", WORLDEDIT);
			return;
		}

		if (args.length == 3) {
			try {
				velocity.setX(Double.parseDouble(args[0]));
				velocity.setY(Double.parseDouble(args[0]));
				velocity.setZ(Double.parseDouble(args[0]));
			} catch (Exception ex) {
				this.showUsage(sender);
				return;
			}

			if (velocity.getX() < -9.0 || velocity.getX() > 9.0) {
				this.getLog().error(sender, "The X velocity argument must be between -9 and 9!");
				return;
			}

			if (velocity.getY() < -0.0 || velocity.getY() > 9.0) {
				this.getLog().error(sender, "The Y velocity argument must be between 0 and 9!");
				return;
			}

			if (velocity.getZ() < -9.0 || velocity.getZ() > 9.0) {
				this.getLog().error(sender, "The Z velocity argument must be between -9 and 9!");
				return;
			}
		}

		Player player = (Player)sender;
		com.sk89q.worldedit.bukkit.WorldEditPlugin wePlugin = (com.sk89q.worldedit.bukkit.WorldEditPlugin)manager.getPlugin(WORLDEDIT);
		com.sk89q.worldedit.bukkit.selections.Selection selection = wePlugin.getSelection(player);
		Location minimum = selection.getMinimumPoint();
		Location maximum = selection.getMaximumPoint();
		World world = player.getWorld();

		for (int x = maximum.getBlockX() - 1; x >= minimum.getBlockX(); x--) {
			for (int z = maximum.getBlockZ() - 1; z >= minimum.getBlockZ(); z--) {
				for (int y = maximum.getBlockY() - 1; y >= minimum.getBlockY(); y--) {
					Block block = world.getBlockAt(x, y, z);
					if (block == null || Material.AIR.equals(block.getType())) continue;
					block.setType(Material.AIR);
					block.breakNaturally();
					block.getDrops().clear();
					FallingBlock falling = world.spawnFallingBlock(block.getLocation(), block.getType(), block.getData());
					falling.setVelocity(velocity);
				}
			}
		}
	}

}