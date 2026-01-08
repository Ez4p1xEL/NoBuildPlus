package p1xel.nobuildplus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import p1xel.nobuildplus.storage.FlagsManager;
import p1xel.nobuildplus.storage.Locale;

public enum Flags implements Flag {
    // Default value
    destroy("break", true, "IRON_PICKAXE", 10, "all", Collections.singletonList("GRASS_BLOCK")),
    build("build", true, "GRASS_BLOCK", 11, "all", Collections.singletonList("GRASS_BLOCK")),
    use("use", true, "CRAFTING_TABLE", 12, "list", Arrays.asList("ENCHANTING_TABLE", "CRAFTING_TABLE", "ANVIL", "CHIPPED_ANVIL", "DAMAGED_ANVIL", "ENDER_PEARL")),
    container("container", true, "CHEST", 13, "list", Arrays.asList("CHEST", "ENDER_CHEST", "TRAPPED_CHEST", "FURNACE")),
    mob_damage("mob-damage", true, "ZOMBIE_SPAWN_EGG", 14, "all", Collections.singletonList("ZOMBIE")),
    mob_explode("mob-explode", true, "CREEPER_SPAWN_EGG", 15, "all", Collections.singletonList("CREEPER")),
    pvp("pvp", true, "DIAMOND_SWORD", 16, null, null),
    shoot("shoot", true, "BOW", 19, null, null),
    tnt("tnt", true, "TNT", 20, null, null),
    tnt_damage("tnt-damage", true, "TNT_MINECART", 21, null, null),
    frame("frame", true, "ITEM_FRAME", 22, null, null),
    bed("bed", true, "RED_BED", 23, null, null),
    voidtp("voidtp", true, "ENDER_EYE", 24, null, null),
    villager("villager", true, "VILLAGER_SPAWN_EGG", 25, null, null),
    command("command", true, "COMMAND_BLOCK", 28, "list", Collections.singletonList("spawn")),
    chat("chat", true, "PLAYER_HEAD", 29, null, null),
    leaf_decay("leaf-decay", true, "OAK_LEAVES", 30, null, null),
    melt("melt", true, "PACKED_ICE", 31, null, null),
    fall_damage("fall-damage", true, "LEATHER_BOOTS", 32, null, null),
    armorstand("armorstand", true, "ARMOR_STAND", 33, null, null),
    farmbreak("farmbreak", true, "FARMLAND", 34, null, null),
    ride("ride", true, "SADDLE", 37, "list", Arrays.asList("HORSE", "MINECART")),
    painting("painting", true, "PAINTING", 38, null, null),
    bucket_place("bucket-place", true, "BUCKET", 39, null, null),
    bucket_fill("bucket-fill", true, "POWDER_SNOW_BUCKET", 40, null, null),
    boat("boat", true, "OAK_BOAT", 41, "list", Arrays.asList("OAK_BOAT", "SPRUCE_BOAT", "BIRCH_BOAT", "JUNGLE_BOAT", "ACACIA_BOAT", "DARK_OAK_BOAT", "MANGROVE_BOAT", "OAK_CHEST_BOAT", "SPRUCE_CHEST_BOAT", "BIRCH_CHEST_BOAT", "JUNGLE_CHEST_BOAT", "ACACIA_CHEST_BOAT", "DARK_OAK_CHEST_BOAT", "MANGROVE_CHEST_BOAT", "BOAT")),
    button("button", true, "STONE_BUTTON", 42, "list", Arrays.asList("STONE_BUTTON", "OAK_BUTTON", "SPRUCE_BUTTON", "BIRCH_BUTTON", "JUNGLE_BUTTON", "ACACIA_BUTTON", "DARK_OAK_BUTTON", "MANGROVE_BUTTON", "CRIMSON_BUTTON", "WARPED_BUTTON", "POLISHED_BLACKSTONE_BUTTON", "BUTTON")),
    door_interact("door-interact", true, "SPRUCE_DOOR", 43, "list", Arrays.asList("OAK_DOOR", "SPRUCE_DOOR", "BIRCH_DOOR", "JUNGLE_DOOR", "ACACIA_DOOR", "DARK_OAK_DOOR", "MANGROVE_DOOR", "CRIMSON_DOOR", "WARPED_DOOR", "DOOR")),
    lever("lever", true, "LEVER", 10, null, null),
    trapdoor_interact("trapdoor-interact", true, "ACACIA_TRAPDOOR", 11, "list", Arrays.asList("OAK_TRAPDOOR", "SPRUCE_TRAPDOOR", "BIRCH_TRAPDOOR", "JUNGLE_TRAPDOOR", "ACACIA_TRAPDOOR", "DARK_OAK_TRAPDOOR", "MANGROVE_TRAPDOOR", "CRIMSON_TRAPDOOR", "WARPED_TRAPDOOR", "TRAPDOOR")),
    fencegate_interact("fencegate-interact", true, "JUNGLE_FENCE_GATE", 12, "list", Arrays.asList("OAK_FENCE_GATE", "SPRUCE_FENCE_GATE", "BIRCH_FENCE_GATE", "JUNGLE_FENCE_GATE", "ACACIA_FENCE_GATE", "DARK_OAK_FENCE_GATE", "MANGROVE_FENCE_GATE", "CRIMSON_FENCE_GATE", "WARPED_FENCE_GATE", "FENCE_GATE")),
    drop_item("drop-item", true, "DIRT", 13, "all", Collections.singletonList("STONE")),
    egg_throw("egg-throw", true, "EGG", 14, null, null),
    snowball_throw("snowball-throw", true, "SNOWBALL", 15, null, null),
    water_spread("water-spread", false, "WATER_BUCKET", 16, null, null),
    lava_spread("lava-spread", false, "LAVA_BUCKET", 19, null, null),
    fly("fly", true, "FEATHER", 20, null, null),
    teleport("teleport", true, "ENDER_PEARL", 21, null, null),
    mob_spawn("mob-spawn", true, "PIG_SPAWN_EGG", 22, "list", Collections.singletonList("ZOMBIE")),
    minecart("minecart", true, "MINECART", 23, "list", Collections.singletonList("MINECART")),
    item_pickup("item-pickup", true, "STONE", 24, "all", Collections.singletonList("STONE")),
    potion("potion", true, "POTION", 25, null, null),
    bonemeal("bonemeal", true, "BONE_MEAL", 28, null, null),
    elytra("elytra", true, "ELYTRA", 29, null, null),
    nether("nether", true, "NETHER_BRICKS", 30, null, null),
    coral_decay("coral-decay", true, "TUBE_CORAL_FAN", 31, "list", Arrays.asList("TUBE_CORAL_BLOCK", "BRAIN_CORAL_BLOCK", "BUBBLE_CORAL_BLOCK", "FIRE_CORAL_BLOCK", "HORN_CORAL_BLOCK", "TUBE-CORAL", "BRAIN_CORAL", "BUBBLE_CORAL", "FIRE_CORAL", "HORN_CORAL", "TUBE_CORAL_FAN", "bRAIN_CORAL_FAN", "BUBBLE_CORAL_FAN", "FIRE_CORAL_FAN", "HORN_CORAL_FAN")),
    fire_spawn("fire-spawn", true, "FIRE_CHARGE", 32, null, null),
    sign_edit("sign-edit", true, "OAK_SIGN", 33, null, null),
    dye("dye", true, "BLUE_DYE", 34, null, null),
    piston("piston", true, "PISTON", 37, null, null),
    ice_form("ice-form", true, "ICE", 38, null, null),
    fish("fish", true, "COD", 39, null, null),
    hook("hook", true, "FISHING_ROD", 40, null, null),
    crystal("crystal", true, "END_CRYSTAL", 41, null, null),
    flower_pot("flower-pot", true, "FLOWER_POT", 42, null, null),
    books_interact("books-interact", true, "CHISELED_BOOKSHELF", 43, null, null),
    hunger("hunger", true, "COOKED_CHICKEN", 10, null, null),
    berries("berries", true, "GLOW_BERRIES", 11, null, null),
    craft("craft", true, "CRAFTING_TABLE", 12, "all", Collections.singletonList("DIAMOND_SWORD")),
    heal("heal", true, "GOLDEN_APPLE", 13, null, null),
    turtle_egg("turtle-egg", true, "TURTLE_EGG", 14, null, null),
    lightning("lightning", true, "LIGHTNING_ROD", 15, null, null),
    tnt_prime("tnt-prime", true, "TNT", 16, null, null),
    pressure_plate("pressure-plate", true, "OAK_PRESSURE_PLATE", 19, "list", Arrays.asList("OAK_PRESSURE_PLATE", "SPRUCE_PRESSURE_PLATE", "BIRCH_PRESSURE_PLATE", "JUNGLE_PRESSURE_PLATE", "ACACIA_PRESSURE_PLATE", "DARK_OAK_PRESSURE_PLATE", "MANGROVE_PRESSURE_PLATE", "CHERRY_PRESSURE_PLATE", "PALE_OAK_PRESSURE_PLATE", "BAMBOO_PRESSURE_PLATE", "CRIMSON_PRESSURE_PLATE", "WARPED_PRESSURE_PLATE", "STONE_PRESSURE_PLATE", "POLISHED_BLACKSTONE_PRESSURE_PLATE", "LIGHT_WEIGHTED_PRESSURE_PLATE", "HEAVY_WEIGHTED_PRESSURE_PLATE", "PRESSURE_PLATE")),
    fire_spread("fire-spread", true, "FLINT_AND_STEEL", 20, null, null);

    private final String flag_name;
    private final boolean enabled;
    private final String show_item;
    private final int slot;
    private final String type;
    private final List<String> list;
    private final static Map<String, Flags> NAMEMAP = Maps.newHashMap();
    private List<String> configList;

    private Flags(String flag_name, boolean enabled, String show_item, int slot, String type, List<String> list) {
        this.flag_name = flag_name;
        this.enabled = enabled;
        this.show_item = show_item;
        this.slot = slot;
        this.type = type;
        this.list = list;
        this.configList = list;
    }

    @Deprecated
    public boolean getDefaultFlagEnabled() {
        return this.enabled;
    }

    @Deprecated
    public int getSlot() {
        return this.slot;
    }

    @Override
    public String getDefaultType() {
        return this.type;
    }

    @Override
    public List<String> getDefaultList() {
        return this.list;
    }

    @Override
    public String getName() {
        return this.flag_name;
    }

    @Override
    public boolean inLocalFile() {
        return true;
    }

    @Override
    public String getType() {
        return FlagsManager.getFlagsType(getName());
    }

    @Override
    public List<String> getList() {
        return configList;
    }

    @Deprecated
    public static Flags matchFlag(final String name) {
        return NAMEMAP.get(name.toLowerCase());
    }

    @Override
    public void refreshMap() {
        for (Flags flag : values()) {
            List<String> list = new ArrayList<>();
            if (FlagsManager.yaml.isSet("flags." + flag.flag_name + ".list")) {
                list = FlagsManager.getFlagsList(flag.flag_name);
            }
            flag.configList = list;
        }
    }

    static {
        for (Flags flag : values()) {
            NAMEMAP.put(flag.flag_name.toLowerCase(), flag);
        }
    }

    @Override
    public String getShowItem() {
        return this.show_item;
    }

    @Override
    public String getDescription(String language) {
        return Locale.getMessage("flag.description." + flag_name);
    }

}
