package p1xel.nobuildplus;

import com.google.common.collect.Maps;
import p1xel.nobuildplus.Storage.FlagsManager;
import p1xel.nobuildplus.Storage.Settings;
import p1xel.nobuildplus.Storage.Worlds;

import java.util.*;

public enum Flags {
    // Default value
    destroy(true, "IRON_PICKAXE", 10, "all", Collections.singletonList("GRASS_BLOCK")),
    build(true, "GRASS_BLOCK", 11, "all", Collections.singletonList("GRASS_BLOCK")),
    use(true, "CRAFTING_TABLE", 12, "list", Arrays.asList("ENCHANTING_TABLE", "CRAFTING_TABLE", "ANVIL", "CHIPPED_ANVIL", "DAMAGED_ANVIL", "ENDER_PEARL")),
    container(true, "CHEST", 13, "list", Arrays.asList("CHEST", "ENDER_CHEST", "TRAPPED_CHEST", "FURNACE")),
    mob_damage(true, "ZOMBIE_SPAWN_EGG", 14, "all", Collections.singletonList("ZOMBIE")),
    mob_explode(true, "CREEPER_SPAWN_EGG",15, "all", Collections.singletonList("CREEPER")),
    pvp(true, "DIAMOND_SWORD", 16, null, null),
    shoot(true, "BOW", 19, null, null),
    tnt(true, "TNT",20,null,null),
    tnt_damage(true, "TNT_MINECART", 21, null, null),
    frame(true, "ITEM_FRAME",22, null, null),
    bed(true, "RED_BED", 23, null, null),
    voidtp(true, "ENDER_EYE", 24,null, null),
    villager(true, "VILLAGER_SPAWN_EGG", 25, null, null),
    command(true, "COMMAND_BLOCK", 28, "list", Collections.singletonList("spawn")),
    chat(true, "PLAYER_HEAD", 29, null, null),
    leaf_decay(true, "OAK_LEAVES", 30, null, null),
    melt(true, "LEATHER_BOOTS", 32, null, null),
    fall_damage(true, "LEATHER_BOOTS", 32, null, null),
    armorstand(true, "ARMOR_STAND", 33, null, null),
    farmbreak(true, "FARMLAND", 34, null, null),
    ride(true, "SADDLE", 37, "list", Arrays.asList("HORSE", "MINECART")),
    painting(true, "PAINTING", 38, null, null),
    bucket_place(true, "BUCKET", 39, null, null),
    bucket_fill(true, "POWDER_SNOW_BUCKET", 40, null, null),
    boat(true, "OAK_BOAT", 41, "list", Arrays.asList("OAK_BOAT", "SPRUCE_BOAT", "BIRCH_BOAT", "JUNGLE_BOAT", "ACACIA_BOAT", "DARK_OAK_BOAT", "MANGROVE_BOAT", "OAK_CHEST_BOAT", "SPRUCE_CHEST_BOAT", "BIRCH_CHEST_BOAT", "JUNGLE_CHEST_BOAT", "ACACIA_CHEST_BOAT", "DARK_OAK_CHEST_BOAT", "MANGROVE_CHEST_BOAT")),
    button(true, "STONE_BUTTON", 42, "list", Arrays.asList("STONE_BUTTON", "OAK_BUTTON", "SPRUCE_BUTTON", "BIRCH_BUTTON", "JUNGLE_BUTTON", "ACACIA_BUTTON", "DARK_OAK_BUTTON", "MANGROVE_BUTTON", "CRIMSON_BUTTON", "WARPED_BUTTON", "POLISHED_BLACKSTONE_BUTTON")),
    door_interact(true, "SPRUCE_DOOR", 43, "list", Arrays.asList("OAK_DOOR", "SPRUCE_DOOR", "BIRCH_DOOR", "JUNGLE_DOOR", "ACACIA_DOOR", "DARK_OAK_DOOR", "MANGROVE_DOOR", "CRIMSON_DOOR", "WARPED_DOOR")),
    lever(true, "LEVER", 10, null, null),
    trapdoor_interact(true, "ACACIA_TRAPDOOR", 11, "list", Arrays.asList("OAK_TRAPDOOR", "SPRUCE_TRAPDOOR", "BIRCH_TRAPDOOR", "JUNGLE_TRAPDOOR", "ACACIA_TRAPDOOR", "DARK_OAK_TRAPDOOR", "MANGROVE_TRAPDOOR", "CRIMSON_TRAPDOOR", "WARPED_TRAPDOOR")),
    fencegate_interact(true, "JUNGLE_FENCE_GATE", 12, "list", Arrays.asList("OAK_FENCE_GATE", "SPRUCE_FENCE_GATE", "BIRCH_FENCE_GATE", "JUNGLE_FENCE_GATE", "ACACIA_FENCE_GATE", "DARK_OAK_FENCE_GATE", "MANGROVE_FENCE_GATE", "CRIMSON_FENCE_GATE", "WARPED_FENCE_GATE")),
    drop_item(true, "DIRT", 13, "all", Collections.singletonList("STONE")),
    egg_throw(true, "EGG", 14, null, null),
    snowball_throw(true, "SNOWBALL", 15, null, null),
    water_spread(false, "WATER_BUCKET", 16, null, null),
    lava_spread(false, "LAVA_BUCKET", 19, null, null),
    fly(true, "FEATHER", 20, null, null),
    teleport(true, "ENDER_PEARL", 21, null, null),
    mob_spawn(true, "PIG_SPAWN_EGG", 22, "list", Collections.singletonList("ZOMBIE")),
    minecart(true, "MINECART", 23, "list", Collections.singletonList("MINECART")),
    item_pickup(true, "STONE", 24, "all", Collections.singletonList("STONE")),
    potion(true, "POTION", 25, null, null),
    bonemeal(true, "BONE_MEAL", 28, null, null),
    elytra(true, "ELYTRA", 29, null, null),
    nether(true, "NETHER_BRICKS", 30, null, null),
    coral_decay(true, "TUBE_CORAL_FAN", 31, "list", Arrays.asList("TUBE_CORAL_BLOCK", "BRAIN_CORAL_BLOCK", "BUBBLE_CORAL_BLOCK", "FIRE_CORAL_BLOCK", "HORN_CORAL_BLOCK", "TUBE-CORAL", "BRAIN_CORAL", "BUBBLE_CORAL", "FIRE_CORAL", "HORN_CORAL", "TUBE_CORAL_FAN", "bRAIN_CORAL_FAN", "BUBBLE_CORAL_FAN", "FIRE_CORAL_FAN", "HORN_CORAL_FAN")),
    fire_spawn(true, "FIRE_CHARGE", 32, null, null),
    sign_edit(true, "OAK_SIGN", 33, null, null),
    dye(true, "BLUE_DYE", 34, null, null),
    piston(true, "PISTON", 37, null, null),
    ice_form(true, "ICE", 38, null, null),
    fish(true, "COD", 39, null, null),
    hook(true, "FISHING_ROD", 40, null, null),
    crystal(true, "END_CRYSTAL", 41, null, null),
    flower_pot(true, "FLOWER_POT", 42, null, null),
    books_interact(true, "CHISELED_BOOKSHELF", 43, null, null),
    hunger(true, "COOKED_CHICKEN", 10, null, null),
    berries(true, "GLOW_BERRIES", 11, null, null),
    craft(true, "CRAFTING_TABLE", 12, "all", Collections.singletonList("DIAMOND_SWORD")),
    heal(true, "GOLDEN_APPLE", 13, null, null),
    turtle_egg(true, "TURTLE_EGG", 14, null, null);

    private boolean enabled;
    private String show_item;
    private int slot;
    private String type;
    private List<String> list;
    private final static Map<String, Flags> NAMEMAP = Maps.newHashMap();

    private Flags(boolean enabled, String show_item, int slot, String type, List<String> list) {
        this.enabled = enabled;
        this.show_item = show_item;
        this.slot = slot;
        this.type = type;
        this.list = list;
    }

    public boolean getDefaultFlagEnabled() {
        return this.enabled;
    }

    public String getDefaultShowItem() {
        return this.show_item;
    }

    public int getDefaultSlot() {
        return this.slot;
    }

    public String getDefaultType() {
        return this.type;
    }

    public List<String> getDefaultList() {
        return this.list;
    }

    public String getName() {
        return this.name().replaceAll("_", "-").replaceAll("destroy", "break");
    }

    public boolean isEnabled(String world) {
        if (!Settings.canExecute(world, getName())) {
            return false;
        }

        if (Worlds.getFlag(world, getName())) {
            return false;
        }

        return true;
    }

    public String getType() {
        return FlagsManager.getFlagsType(getName());
    }

    public List<String> getList() {
        if (FlagsManager.yaml.isSet("flags." + getName() + ".list")) {
            return FlagsManager.getFlagsList(getName());
        }
        return new ArrayList<>();
    }

    public static Flags matchFlag(final String name) {
        Flags result;

        String filtered = name.toUpperCase();

        filtered = filtered.replaceAll("-", "_");
        result = NAMEMAP.get(filtered);

        return result;
    }

    public static Map<String, Flags> getMaps() {
        return NAMEMAP;
    }

    static {
        for (Flags flag : values()) {

            NAMEMAP.put(flag.getName(), flag);
        }
    }

}
