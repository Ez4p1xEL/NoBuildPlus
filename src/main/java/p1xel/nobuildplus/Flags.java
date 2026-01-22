package p1xel.nobuildplus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import p1xel.nobuildplus.storage.FlagsManager;
import p1xel.nobuildplus.storage.Locale;

public enum Flags implements Flag {
    // Default value
    destroy("break", "IRON_PICKAXE", "all", Collections.singletonList("GRASS_BLOCK")),
    build("build", "GRASS_BLOCK", "all", Collections.singletonList("GRASS_BLOCK")),
    use("use", "CRAFTING_TABLE", "list", Arrays.asList("ENCHANTING_TABLE", "CRAFTING_TABLE", "ANVIL", "CHIPPED_ANVIL", "DAMAGED_ANVIL", "ENDER_PEARL")),
    container("container", "CHEST", "list", Arrays.asList("CHEST", "ENDER_CHEST", "TRAPPED_CHEST", "FURNACE")),
    mob_damage("mob-damage", "ZOMBIE_SPAWN_EGG", "all", Collections.singletonList("ZOMBIE")),
    mob_explode("mob-explode", "CREEPER_SPAWN_EGG", "all", Collections.singletonList("CREEPER")),
    pvp("pvp", "DIAMOND_SWORD", null, null),
    shoot("shoot", "BOW", null, null),
    tnt("tnt", "TNT", null, null),
    tnt_damage("tnt-damage", "TNT_MINECART", null, null),
    frame("frame", "ITEM_FRAME", null, null),
    bed("bed", "RED_BED",  null, null),
    voidtp("voidtp", "ENDER_EYE", null, null),
    villager("villager", "VILLAGER_SPAWN_EGG", null, null),
    command("command", "COMMAND_BLOCK", "list", Collections.singletonList("spawn")),
    chat("chat", "PLAYER_HEAD", null, null),
    leaf_decay("leaf-decay", "OAK_LEAVES", null, null),
    melt("melt", "PACKED_ICE", null, null),
    fall_damage("fall-damage", "LEATHER_BOOTS", null, null),
    armorstand("armorstand", "ARMOR_STAND", null, null),
    farmbreak("farmbreak", "FARMLAND", null, null),
    ride("ride", "SADDLE", "list", Arrays.asList("HORSE", "MINECART")),
    painting("painting", "PAINTING", null, null),
    bucket_place("bucket-place", "BUCKET", null, null),
    bucket_fill("bucket-fill", "POWDER_SNOW_BUCKET", null, null),
    boat("boat", "OAK_BOAT", "list", Arrays.asList("OAK_BOAT", "SPRUCE_BOAT", "BIRCH_BOAT", "JUNGLE_BOAT", "ACACIA_BOAT", "DARK_OAK_BOAT", "MANGROVE_BOAT", "OAK_CHEST_BOAT", "SPRUCE_CHEST_BOAT", "BIRCH_CHEST_BOAT", "JUNGLE_CHEST_BOAT", "ACACIA_CHEST_BOAT", "DARK_OAK_CHEST_BOAT", "MANGROVE_CHEST_BOAT", "BOAT")),
    button("button", "STONE_BUTTON", "list", Arrays.asList("STONE_BUTTON", "OAK_BUTTON", "SPRUCE_BUTTON", "BIRCH_BUTTON", "JUNGLE_BUTTON", "ACACIA_BUTTON", "DARK_OAK_BUTTON", "MANGROVE_BUTTON", "CRIMSON_BUTTON", "WARPED_BUTTON", "POLISHED_BLACKSTONE_BUTTON", "BUTTON")),
    door_interact("door-interact", "SPRUCE_DOOR", "list", Arrays.asList("OAK_DOOR", "SPRUCE_DOOR", "BIRCH_DOOR", "JUNGLE_DOOR", "ACACIA_DOOR", "DARK_OAK_DOOR", "MANGROVE_DOOR", "CRIMSON_DOOR", "WARPED_DOOR", "DOOR")),
    lever("lever", "LEVER", null, null),
    trapdoor_interact("trapdoor-interact", "ACACIA_TRAPDOOR", "list", Arrays.asList("OAK_TRAPDOOR", "SPRUCE_TRAPDOOR", "BIRCH_TRAPDOOR", "JUNGLE_TRAPDOOR", "ACACIA_TRAPDOOR", "DARK_OAK_TRAPDOOR", "MANGROVE_TRAPDOOR", "CRIMSON_TRAPDOOR", "WARPED_TRAPDOOR", "TRAPDOOR")),
    fencegate_interact("fencegate-interact", "JUNGLE_FENCE_GATE", "list", Arrays.asList("OAK_FENCE_GATE", "SPRUCE_FENCE_GATE", "BIRCH_FENCE_GATE", "JUNGLE_FENCE_GATE", "ACACIA_FENCE_GATE", "DARK_OAK_FENCE_GATE", "MANGROVE_FENCE_GATE", "CRIMSON_FENCE_GATE", "WARPED_FENCE_GATE", "FENCE_GATE")),
    drop_item("drop-item", "DIRT", "all", Collections.singletonList("STONE")),
    egg_throw("egg-throw", "EGG", null, null),
    snowball_throw("snowball-throw", "SNOWBALL", null, null),
    water_spread("water-spread", "WATER_BUCKET", null, null),
    lava_spread("lava-spread", "LAVA_BUCKET", null, null),
    fly("fly", "FEATHER", null, null),
    teleport("teleport", "ENDER_PEARL", null, null),
    mob_spawn("mob-spawn", "PIG_SPAWN_EGG", "list", Collections.singletonList("ZOMBIE")),
    minecart("minecart", "MINECART", "list", Collections.singletonList("MINECART")),
    item_pickup("item-pickup", "STONE", "all", Collections.singletonList("STONE")),
    potion("potion", "POTION", null, null),
    bonemeal("bonemeal", "BONE_MEAL", null, null),
    elytra("elytra", "ELYTRA", null, null),
    nether("nether", "NETHER_BRICKS", null, null),
    coral_decay("coral-decay", "TUBE_CORAL_FAN", "list", Arrays.asList("TUBE_CORAL_BLOCK", "BRAIN_CORAL_BLOCK", "BUBBLE_CORAL_BLOCK", "FIRE_CORAL_BLOCK", "HORN_CORAL_BLOCK", "TUBE-CORAL", "BRAIN_CORAL", "BUBBLE_CORAL", "FIRE_CORAL", "HORN_CORAL", "TUBE_CORAL_FAN", "bRAIN_CORAL_FAN", "BUBBLE_CORAL_FAN", "FIRE_CORAL_FAN", "HORN_CORAL_FAN")),
    fire_spawn("fire-spawn", "FIRE_CHARGE", null, null),
    sign_edit("sign-edit", "OAK_SIGN", null, null),
    dye("dye", "BLUE_DYE", null, null),
    piston("piston", "PISTON", null, null),
    ice_form("ice-form", "ICE", null, null),
    fish("fish", "COD", null, null),
    hook("hook", "FISHING_ROD", null, null),
    crystal("crystal", "END_CRYSTAL", null, null),
    flower_pot("flower-pot", "FLOWER_POT", null, null),
    books_interact("books-interact", "CHISELED_BOOKSHELF", null, null),
    hunger("hunger", "COOKED_CHICKEN", null, null),
    berries("berries", "GLOW_BERRIES", null, null),
    craft("craft", "CRAFTING_TABLE", "all", Collections.singletonList("DIAMOND_SWORD")),
    heal("heal", "GOLDEN_APPLE", null, null),
    turtle_egg("turtle-egg", "TURTLE_EGG", null, null),
    lightning("lightning", "LIGHTNING_ROD", null, null),
    tnt_prime("tnt-prime", "TNT", null, null),
    pressure_plate("pressure-plate", "OAK_PRESSURE_PLATE", "list", Arrays.asList("OAK_PRESSURE_PLATE", "SPRUCE_PRESSURE_PLATE", "BIRCH_PRESSURE_PLATE", "JUNGLE_PRESSURE_PLATE", "ACACIA_PRESSURE_PLATE", "DARK_OAK_PRESSURE_PLATE", "MANGROVE_PRESSURE_PLATE", "CHERRY_PRESSURE_PLATE", "PALE_OAK_PRESSURE_PLATE", "BAMBOO_PRESSURE_PLATE", "CRIMSON_PRESSURE_PLATE", "WARPED_PRESSURE_PLATE", "STONE_PRESSURE_PLATE", "POLISHED_BLACKSTONE_PRESSURE_PLATE", "LIGHT_WEIGHTED_PRESSURE_PLATE", "HEAVY_WEIGHTED_PRESSURE_PLATE", "PRESSURE_PLATE")),
    fire_spread("fire-spread", "CRIMSON_STEM", null, null);

    private final String flag_name;
    private final String show_item;
    private final String type;
    private final List<String> list;
    private final static Map<String, Flags> NAMEMAP = Maps.newHashMap();
    private List<String> configList;
    Flags(String flag_name, String show_item, String type, List<String> list) {
        this.flag_name = flag_name;
        this.show_item = show_item;
        this.type = type;
        this.list = list;
        this.configList = list;
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
