package net.netcoding.niftycommands.managers;

import net.netcoding.niftycore.util.StringUtil;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.*;

public enum Mob {

	CHICKEN("Chicken", Behavior.FRIENDLY, EntityType.CHICKEN),
	COW("Cow", Behavior.FRIENDLY, EntityType.COW),
	CREEPER("Creeper", Behavior.ENEMY, EntityType.CREEPER),
	GHAST("Ghast", Behavior.ENEMY, EntityType.GHAST),
	GIANT("Giant", Behavior.ENEMY, EntityType.GIANT),
	HORSE("Horse", Behavior.FRIENDLY, EntityType.HORSE),
	PIG("Pig", Behavior.FRIENDLY, EntityType.PIG),
	PIGZOMB("PigZombie", Behavior.NEUTRAL, EntityType.PIG_ZOMBIE),
	SHEEP("Sheep", Behavior.FRIENDLY, "", EntityType.SHEEP),
	SKELETON("Skeleton", Behavior.ENEMY, EntityType.SKELETON),
	SLIME("Slime", Behavior.ENEMY, EntityType.SLIME),
	SPIDER("Spider", Behavior.ENEMY, EntityType.SPIDER),
	SQUID("Squid", Behavior.FRIENDLY, EntityType.SQUID),
	ZOMBIE("Zombie", Behavior.ENEMY, EntityType.ZOMBIE),
	WOLF("Wolf", Behavior.NEUTRAL, "", EntityType.WOLF),
	CAVESPIDER("CaveSpider", Behavior.ENEMY, EntityType.CAVE_SPIDER),
	ENDERMAN("Enderman", Behavior.ENEMY, "", EntityType.ENDERMAN),
	SILVERFISH("Silverfish", Behavior.ENEMY, "", EntityType.SILVERFISH),
	ENDERDRAGON("EnderDragon", Behavior.ENEMY, EntityType.ENDER_DRAGON),
	VILLAGER("Villager", Behavior.FRIENDLY, EntityType.VILLAGER),
	BLAZE("Blaze", Behavior.ENEMY, EntityType.BLAZE),
	MUSHROOMCOW("MushroomCow", Behavior.FRIENDLY, EntityType.MUSHROOM_COW),
	MAGMACUBE("MagmaCube", Behavior.ENEMY, EntityType.MAGMA_CUBE),
	SNOWMAN("Snowman", Behavior.FRIENDLY, "", EntityType.SNOWMAN),
	OCELOT("Ocelot", Behavior.NEUTRAL, EntityType.OCELOT),
	IRONGOLEM("IronGolem", Behavior.NEUTRAL, EntityType.IRON_GOLEM),
	WITHER("Wither", Behavior.ENEMY, EntityType.WITHER),
	BAT("Bat", Behavior.FRIENDLY, EntityType.BAT),
	WITCH("Witch", Behavior.ENEMY, EntityType.WITCH),
	BOAT("Boat", Behavior.NEUTRAL, EntityType.BOAT),
	MINECART("Minecart", Behavior.NEUTRAL, EntityType.MINECART),
	MINECART_CHEST("ChestMinecart", Behavior.NEUTRAL, EntityType.MINECART_CHEST),
	MINECART_FURNACE("FurnaceMinecart", Behavior.NEUTRAL, EntityType.MINECART_FURNACE),
	MINECART_TNT("TNTMinecart", Behavior.NEUTRAL, EntityType.MINECART_TNT),
	MINECART_HOPPER("HopperMinecart", Behavior.NEUTRAL, EntityType.MINECART_HOPPER),
	MINECART_MOB_SPAWNER("SpawnerMinecart", Behavior.NEUTRAL, EntityType.MINECART_MOB_SPAWNER),
	ENDERCRYSTAL("EnderCrystal", Behavior.NEUTRAL, EntityType.ENDER_CRYSTAL),
	EXPERIENCEORB("ExperienceOrb", Behavior.NEUTRAL, EntityType.EXPERIENCE_ORB);

    private final static Map<String, Mob> NAME_MAP = new HashMap<>();
    private final static Map<EntityType, Mob> BUKKIT_MAP = new HashMap<>();
    private final Behavior behavior;
	private final String name;
	private final String suffix;
	private final EntityType type;

    static {
        for (Mob mob : values()) {
            NAME_MAP.put(mob.getName().toLowerCase(), mob);
            BUKKIT_MAP.put(mob.getType(), mob);
        }
    }

	private Mob(String name, Behavior behavior, EntityType type) {
		this(name, behavior, "s", type);
	}

	private Mob(String nname, Behavior behavior, String suffix, EntityType type) {
		this.name = nname;
		this.behavior = behavior;
		this.suffix = suffix;
		this.type = type;
	}

    public static Mob fromType(EntityType type) {
        return BUKKIT_MAP.get(type);
    }

    public static Mob fromName(String name) {
        return NAME_MAP.get(name.toLowerCase());
    }

    public Behavior getBehavior() {
        return this.behavior;
    }

    public static Set<String> getMobList() {
        return Collections.unmodifiableSet(NAME_MAP.keySet());
    }

    public String getName() {
        return this.name;
    }

	public EntityType getType() {
		return this.type;
	}

    public Entity spawn(Location location) {
        Entity entity = location.getWorld().spawn(location, this.getType().getEntityClass());

        if (entity == null)
            throw new RuntimeException(StringUtil.format("Unable to spawn {{0}} at {{1}}!", this.getName(), location));

        return entity;
    }

	public enum Behavior {

		FRIENDLY("friendly"),
		NEUTRAL("neutral"),
		ENEMY("enemy");

		private final String type;

		private Behavior(String type) {
			this.type = type;
		}

	}

}