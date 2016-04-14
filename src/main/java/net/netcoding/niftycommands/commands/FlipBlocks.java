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
		final Vector velocity = new Vector(0, 1.5, 0);

		if (!manager.isPluginEnabled(WORLDEDIT)) {
			this.getLog().error(sender, "This command requires {{0}}!", WORLDEDIT);
			return;
		}

		if (args.length == 3) {
			try {
				velocity.setX(Double.parseDouble(args[0]));
				velocity.setY(Double.parseDouble(args[1]));
				velocity.setZ(Double.parseDouble(args[2]));
			} catch (Exception ex) {
				this.showUsage(sender);
				return;
			}

			if (velocity.getX() < -9.0 || velocity.getX() > 9.0) {
				this.getLog().error(sender, "The X velocity argument must be between -9 and 9!");
				return;
			}

			if (velocity.getY() < 0.0 || velocity.getY() > 5.0) {
				this.getLog().error(sender, "The Y velocity argument must be between 0 and 5!");
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
		final World world = player.getWorld();
		final Location minimum;
		final Location maximum;

		try {
			minimum = selection.getMinimumPoint();
			maximum = selection.getMaximumPoint();
		} catch (Exception ex) {
			this.getLog().error(sender, "You must make a selection first!");
			return;
		}

		for (int y = minimum.getBlockY(); y <= maximum.getBlockY(); y++) {
			for (int x = maximum.getBlockX(); x >= minimum.getBlockX(); x--) {
				for (int z = maximum.getBlockZ(); z >= minimum.getBlockZ(); z--) {
					Block block = world.getBlockAt(x, y, z);

					if (block == null || Material.AIR == block.getType())
						continue;

					Material material = block.getType();
					byte data = block.getData();
					block.setType(Material.AIR);
					block.breakNaturally();
					block.getDrops().clear();
					FallingBlock falling = world.spawnFallingBlock(block.getLocation(), material, data);
					falling.setDropItem(false);
					falling.setVelocity(velocity);
				}
			}
		}
	}

}