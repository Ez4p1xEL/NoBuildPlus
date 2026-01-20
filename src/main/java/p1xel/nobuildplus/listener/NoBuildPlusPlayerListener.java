package p1xel.nobuildplus.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.storage.FlagsManager;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

import java.util.HashMap;

public class NoBuildPlusPlayerListener implements Listener {

    private static HashMap<Player, Location> PRESSURE_PLATE_RECORD = new HashMap<>();

    // Flag: Use
    @EventHandler(ignoreCancelled = true)
    public void onUse(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.use.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission(world.getPermission())) {
            return;
        }

        if (Flags.use.getType().equalsIgnoreCase("list")) {

            for (String name : Flags.use.getList()) {
                Material material = Material.matchMaterial(name);
                if (material != null) {
                    Material mat = block.getType();
                    if (mat == material) {
                        WorldManager.sendMessage(p, world);
                        e.setCancelled(true);
                        return;
                    }
                }

            }

        }


    }

    // Flag: button
    @EventHandler(ignoreCancelled = true)
    public void onButtonUse(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.button.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission(world.getPermission())) {
            return;
        }

        if (Flags.button.getType().equalsIgnoreCase("list")) {

            for (String name : Flags.button.getList()) {
                Material material = Material.matchMaterial(name);
                if (material != null) {
                    Material mat = block.getType();
                    if (mat == material) {
                        WorldManager.sendMessage(p, world);
                        e.setCancelled(true);
                        return;
                    }
                }

            }

        }
    }

    // Flag: door interact
    @EventHandler(ignoreCancelled = true)
    public void onDoorInteract(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.door_interact.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission(world.getPermission())) {
            return;
        }

        if (Flags.door_interact.getType().equalsIgnoreCase("list")) {

            for (String name : Flags.door_interact.getList()) {
                Material material = Material.matchMaterial(name);
                if (material != null) {
                    Material mat = block.getType();
                    if (mat == material) {
                        WorldManager.sendMessage(p, world);
                        e.setCancelled(true);
                        return;
                    }
                }

            }


        }

    }

    // Flag: lever
    @EventHandler(ignoreCancelled = true)
    public void onLeverInteract(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null || block.getType() != Material.LEVER) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.lever.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();
        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);

        e.setCancelled(true);

    }

    // Flag: trapdoor interact
    @EventHandler(ignoreCancelled = true)
    public void onTrapDoorInteract(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.trapdoor_interact.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();
        if (player.hasPermission(world.getPermission())) {
            return;
        }


        if (Flags.trapdoor_interact.getType().equalsIgnoreCase("list")) {

            for (String name : Flags.trapdoor_interact.getList()) {
                Material material = Material.matchMaterial(name);
                if (material != null) {
                    Material mat = block.getType();
                    if (mat == material) {
                        WorldManager.sendMessage(player, world);
                        e.setCancelled(true);
                        return;
                    }
                }

            }

        }
    }

    // Flag: fence gate interact
    @EventHandler(ignoreCancelled = true)
    public void onFenceGateInteract(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.fencegate_interact.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();
        if (player.hasPermission(world.getPermission())) {
            return;
        }

        if (Flags.fencegate_interact.getType().equalsIgnoreCase("list")) {

            Material mat = block.getType();
            if (Flags.fencegate_interact.getList().contains(mat.toString().toUpperCase())) {
                WorldManager.sendMessage(player, world);
                e.setCancelled(true);
            }


        }
    }

    // Flag: bone meal
    @EventHandler(ignoreCancelled = true)
    public void onBoneMealUse(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();
        ItemStack item = e.getItem();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null || item == null || item.getType() != Material.matchMaterial("dye/15") || item.getType() != Material.BONE_MEAL) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.bonemeal.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();
        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);

        e.setCancelled(true);
    }

    // Flag: boat
    @EventHandler(ignoreCancelled = true)
    public void onBoatInteract(PlayerInteractEvent e) {
        Action action = e.getAction();
        Block block = e.getClickedBlock();
        ItemStack item = e.getItem();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null || item == null) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.boat.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();
        if (player.hasPermission(world.getPermission())) {
            return;
        }

        if (Flags.boat.getType().equalsIgnoreCase("list")) {

            Material mat = item.getType();
            if (Flags.boat.getList().contains(mat.toString().toUpperCase())) {
                WorldManager.sendMessage(player, world);
                e.setCancelled(true);
            }


        }
    }

    // Flag: flower pot
    @EventHandler(ignoreCancelled = true)
    public void onFlowerPotUse(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.flower_pot.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();
        if (player.hasPermission(world.getPermission())) {
            return;
        }

        Material mat = block.getType();

        if (mat == Material.FLOWER_POT || mat.name().startsWith("POTTED_")) {

            WorldManager.sendMessage(player, world);
            e.setCancelled(true);

        }
    }

    // Flag: minecart
    @EventHandler(ignoreCancelled = true)
    public void onMinecartInteract(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();
        ItemStack item = e.getItem();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null || item == null) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.minecart.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();
        if (player.hasPermission(world.getPermission())) {
            return;
        }

        if (Flags.minecart.getType().equalsIgnoreCase("list")) {

            Material mat = item.getType();
            if (Flags.minecart.getList().contains(mat.toString().toUpperCase())) {
                WorldManager.sendMessage(player, world);
                e.setCancelled(true);
            }


        }
    }

    // Flag: egg throw
    @EventHandler(ignoreCancelled = true)
    public void onEggThrow(PlayerInteractEvent e) {

        Action action = e.getAction();
        ItemStack item = e.getItem();

        if (!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) || item == null || item.getType() != Material.EGG) {
            return;
        }

        Player player = e.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.egg_throw.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);
        e.setCancelled(true);

    }

    // Flag: snowball throw
    @EventHandler(ignoreCancelled = true)
    public void onSnowBallThrow(PlayerInteractEvent e) {

        Action action = e.getAction();
        ItemStack item = e.getItem();

        if (!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) || item == null || !(item.getType() == Material.matchMaterial("snowball") || item.getType() == Material.matchMaterial("snow_ball"))) {
            return;
        }

        Player player = e.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.snowball_throw.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);
        e.setCancelled(true);

    }

    // Flag: potion
    @EventHandler(ignoreCancelled = true)
    public void onPotionUse(PlayerInteractEvent e) {

        Action action = e.getAction();
        ItemStack item = e.getItem();

        if (!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
                || item == null || !(item.getType() == Material.POTION
                || item.getType() == Material.matchMaterial("SPLASH_POTION"))) {
            return;
        }

        Player player = e.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.potion.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);
        e.setCancelled(true);

    }

    // Flag: farmbreak
    @EventHandler(ignoreCancelled = true)
    public void onFarmInteract(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action != Action.PHYSICAL || block == null
                || !(block.getType() == Material.matchMaterial("SOIL") || block.getType() == Material.matchMaterial("FARMLAND"))) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.farmbreak.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();
        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);

        e.setCancelled(true);

    }

    // Flag: container
    @EventHandler(ignoreCancelled = true)
    public void onContainerInteract(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.container.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();
        if (player.hasPermission(world.getPermission())) {
            return;
        }

        if (Flags.container.getType().equalsIgnoreCase("list")) {

            Material mat = block.getType();
            if (Flags.container.getList().contains(mat.toString().toUpperCase())) {
                WorldManager.sendMessage(player, world);
                e.setCancelled(true);
            }


        }

    }

    // Flag: boat
    @EventHandler(ignoreCancelled = true)
    public void onInteractEntity(PlayerInteractEntityEvent e) {

        Player player = e.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.boat.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        if (e.getRightClicked() instanceof Boat) {

            WorldManager.sendMessage(player, world);
            e.setCancelled(true);
            return;
        }

        if (FlagsManager.BoatIsIncludingChestBoat()) {
            if (e.getRightClicked() instanceof ChestBoat) {
                WorldManager.sendMessage(player, world);
                e.setCancelled(true);
            }
        }

    }

    // Flag: Bed
    @EventHandler(ignoreCancelled = true)
    public void onBed(PlayerBedEnterEvent e) {

        Player player = e.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.bed.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);
        e.setCancelled(true);

    }

    // Flag: Chat
    @EventHandler(ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent e) {

        Player player = e.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.chat.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);
        e.setCancelled(true);

    }

    // Flag: Command
    @EventHandler(ignoreCancelled = true)
    public void onCommandExecute(PlayerCommandPreprocessEvent e) {

        Player player = e.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.command.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        if (Flags.command.getType().equalsIgnoreCase("all")) {
            WorldManager.sendMessage(player, world);
            e.setCancelled(true);
            return;
        }

        if (Flags.command.getType().equalsIgnoreCase("list")) {
            if (Flags.command.getList().contains("/" + e.getMessage())) {
                WorldManager.sendMessage(player, world);
                e.setCancelled(true);
            }
        }
    }

    // Flag: armor stand
    @EventHandler(ignoreCancelled = true)
    public void ArmorStand(PlayerArmorStandManipulateEvent e) {

        Player player = e.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.armorstand.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);
        e.setCancelled(true);

    }

    // Flag: bucket place
    @EventHandler(ignoreCancelled = true)
    public void onBucketUse(PlayerBucketEmptyEvent e) {

        Player player = e.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.bucket_place.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);
        e.setCancelled(true);

    }

    @EventHandler(ignoreCancelled = true)
    public void onBucketFill(PlayerBucketFillEvent e) {

        Player player = e.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.bucket_fill.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);
        e.setCancelled(true);

    }

    // Flag: drop-item
    @EventHandler(ignoreCancelled = true)
    public void onDropItem(PlayerDropItemEvent e) {

        Player player = e.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.drop_item.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        Item droppedItem = e.getItemDrop();

        if (Flags.drop_item.getType().equalsIgnoreCase("all")) {
            WorldManager.sendMessage(player, world);
            e.setCancelled(true);
            return;
        }

        if (Flags.drop_item.getType().equalsIgnoreCase("list")) {

            Material mat = droppedItem.getItemStack().getType();
            if (Flags.drop_item.getList().contains(mat.toString().toUpperCase())) {
                WorldManager.sendMessage(player, world);
                e.setCancelled(true);
            }
        }
    }

    // Flag: item-pickup
    @EventHandler(ignoreCancelled = true)
    public void onItemPickUp(PlayerPickupItemEvent e) {

        Player player = e.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.item_pickup.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        Item item = e.getItem();

        if (Flags.item_pickup.getType().equalsIgnoreCase("all")) {
            WorldManager.sendMessage(player, world);
            e.setCancelled(true);
            return;
        }

        if (Flags.item_pickup.getType().equalsIgnoreCase("list")) {

            Material mat = item.getItemStack().getType();
            if (Flags.item_pickup.getList().contains(mat.toString().toUpperCase())) {
                WorldManager.sendMessage(player, world);
                e.setCancelled(true);
            }
        }
    }

    // Flag: fly
    @EventHandler(ignoreCancelled = true)
    public void onFlightToggle(PlayerToggleFlightEvent e) {

        Player player = e.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        if (!player.getAllowFlight()) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.fly.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);
        e.setCancelled(true);

    }

    // Flag: teleport (For Player)
    @EventHandler(ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent e) {

        Player player = e.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.teleport.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        if (e.getFrom().getWorld().getName().equalsIgnoreCase(worldName) || e.getTo().getWorld().getName().equalsIgnoreCase(worldName)) {

            WorldManager.sendMessage(player, world);
            e.setCancelled(true);

        }
    }

    // Flag: potion
    @EventHandler(ignoreCancelled = true)
    public void onPotionEffect(PlayerItemConsumeEvent e) {

        Player player = e.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.potion.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        Material mat = e.getItem().getType();

        if (mat == Material.POTION || mat == Material.SPLASH_POTION || mat == Material.LINGERING_POTION) {

            e.setCancelled(true);
            WorldManager.sendMessage(player, world);

        }

    }


    // Flag: dye
    @EventHandler(ignoreCancelled = true)
    public void onSheepDye(SheepDyeWoolEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.dye.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);
        e.setCancelled(true);

    }

    // Flag: hook (To Players, NOT Fish)
    @EventHandler(ignoreCancelled = true)
    public void onHook(PlayerFishEvent e) {

        Player player = e.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        if (!(e.getCaught() instanceof Player)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.hook.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);
        e.setCancelled(true);

    }

    // Flag: fish (To fish, NOT players)
    @EventHandler(ignoreCancelled = true)
    public void onFish(PlayerFishEvent e) {

        Player player = e.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        if (e.getCaught() instanceof Player) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.fish.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);
        e.setCancelled(true);

    }

    // Flag: hunger
    @EventHandler(ignoreCancelled = true)
    public void onHungerLost(FoodLevelChangeEvent e) {

        Player player = (Player) e.getEntity();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.hunger.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        e.setCancelled(true);

    }

    // Flag: craft
    @EventHandler(ignoreCancelled = true)
    public void onCraft(CraftItemEvent e) {

        Player player = (Player) e.getWhoClicked();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.craft.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        if (Flags.craft.getType().equalsIgnoreCase("list")) {
            if (Flags.craft.getList().contains(e.getRecipe().getResult().getType().toString().toUpperCase())) {
                WorldManager.sendMessage(player, world);
                e.setCancelled(true);
            }
            return;
        }

        WorldManager.sendMessage(player, world);
        e.setCancelled(true);

    }

    // Flag: pressure-plate
    @EventHandler(ignoreCancelled = true)
    public void onInteractPressurePlate(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();
        if (block == null) {
            return;
        }

        if (!Flags.pressure_plate.getList().contains(block.getType().toString())) {
            return;
        }

        if (e.getAction() != Action.PHYSICAL) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        Player player = e.getPlayer();
        boolean sendMessage = true;
        if (PRESSURE_PLATE_RECORD.containsKey(player)) {
            if (PRESSURE_PLATE_RECORD.get(player).distance(player.getLocation()) <= 0.5) {
                sendMessage = false;
            }
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.pressure_plate.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        if (sendMessage) {
            WorldManager.sendMessage(player, world);
            PRESSURE_PLATE_RECORD.put(player, player.getLocation());
        }
        e.setCancelled(true);

    }
}
