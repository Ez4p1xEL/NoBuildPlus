package p1xel.nobuildplus.Listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.Hook.Hooks;
import p1xel.nobuildplus.Storage.FlagsManager;
import p1xel.nobuildplus.Storage.Worlds;

public class NoBuildPlusPlayerListener implements Listener {

    // Flag: Use
    // Flag: Container
    @EventHandler
    public void onUse(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null) {
            return;
        }

        if (Hooks.cancel(block)) {
            return;
        }

        String world = block.getWorld().getName();
        if (!Flags.use.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Flags.use.getType().equalsIgnoreCase("list")) {

            for (String name : Flags.use.getList()) {
                Material material = Material.matchMaterial(name);
                if (material != null) {
                    Material mat = block.getType();
                    if (mat == material) {
                        if (Worlds.isDenyMessageExist(world)) {
                            p.sendMessage(Worlds.getDenyMessage(world));
                        }
                        e.setCancelled(true);
                        return;
                    }
                }

            }

        }


    }

    // Flag: button
    @EventHandler
    public void onButtonUse(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null) {
            return;
        }

        if (Hooks.cancel(block)) {
            return;
        }

        String world = block.getWorld().getName();
        if (!Flags.button.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Flags.button.getType().equalsIgnoreCase("list")) {

            for (String name : Flags.button.getList()) {
                Material material = Material.matchMaterial(name);
                if (material != null) {
                    Material mat = block.getType();
                    if (mat == material) {
                        if (Worlds.isDenyMessageExist(world)) {
                            p.sendMessage(Worlds.getDenyMessage(world));
                        }
                        e.setCancelled(true);
                        return;
                    }
                }

            }

        }
    }

    // Flag: door interact
    @EventHandler
    public void onDoorInteract(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null) {
            return;
        }

        if (Hooks.cancel(block)) {
            return;
        }

        String world = block.getWorld().getName();
        if (!Flags.door_interact.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Flags.door_interact.getType().equalsIgnoreCase("list")) {

            for (String name : Flags.door_interact.getList()) {
                Material material = Material.matchMaterial(name);
                if (material != null) {
                    Material mat = block.getType();
                    if (mat == material) {
                        if (Worlds.isDenyMessageExist(world)) {
                            p.sendMessage(Worlds.getDenyMessage(world));
                        }
                        e.setCancelled(true);
                        return;
                    }
                }

            }


        }

    }

    // Flag: lever
    @EventHandler
    public void onLeverInteract(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null || block.getType() != Material.LEVER) {
            return;
        }

        if (Hooks.cancel(block)) {
            return;
        }

        String world = block.getWorld().getName();
        if (!Flags.lever.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }

        e.setCancelled(true);

    }

    // Flag: trapdoor interact
    @EventHandler
    public void onTrapDoorInteract(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null) {
            return;
        }

        if (Hooks.cancel(block)) {
            return;
        }

        String world = block.getWorld().getName();
        if (!Flags.trapdoor_interact.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }


        if (Flags.trapdoor_interact.getType().equalsIgnoreCase("list")) {

            for (String name : Flags.trapdoor_interact.getList()) {
                Material material = Material.matchMaterial(name);
                if (material != null) {
                    Material mat = block.getType();
                    if (mat == material) {
                        if (Worlds.isDenyMessageExist(world)) {
                            p.sendMessage(Worlds.getDenyMessage(world));
                        }
                        e.setCancelled(true);
                        return;
                    }
                }

            }

        }
    }

    // Flag: fence gate interact
    @EventHandler
    public void onFenceGateInteract(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null) {
            return;
        }

        if (Hooks.cancel(block)) {
            return;
        }

        String world = block.getWorld().getName();
        if (!Flags.fencegate_interact.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Flags.fencegate_interact.getType().equalsIgnoreCase("list")) {

            Material mat = block.getType();
            if (Flags.fencegate_interact.getList().contains(mat.toString().toUpperCase())) {
                if (Worlds.isDenyMessageExist(world)) {
                    p.sendMessage(Worlds.getDenyMessage(world));
                }
                e.setCancelled(true);
            }


        }
    }

    // Flag: bone meal
    @EventHandler
    public void onBoneMealUse(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();
        ItemStack item = e.getItem();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null || item == null || item.getType() != Material.BONE_MEAL) {
            return;
        }

        if (Hooks.cancel(block)) {
            return;
        }

        String world = block.getWorld().getName();
        if (!Flags.bonemeal.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }

        e.setCancelled(true);
    }

    // Flag: boat
    @EventHandler
    public void onBoatInteract(PlayerInteractEvent e) {
        Action action = e.getAction();
        Block block = e.getClickedBlock();
        ItemStack item = e.getItem();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null || item == null) {
            return;
        }

        if (Hooks.cancel(block)) {
            return;
        }

        String world = block.getWorld().getName();
        if (!Flags.boat.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Flags.boat.getType().equalsIgnoreCase("list")) {

            Material mat = item.getType();
            if (Flags.boat.getList().contains(mat.toString().toUpperCase())) {
                if (Worlds.isDenyMessageExist(world)) {
                    p.sendMessage(Worlds.getDenyMessage(world));
                }
                e.setCancelled(true);
            }


        }
    }

    // Flag: flower pot
    @EventHandler
    public void onFlowerPotUse(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null) {
            return;
        }

        if (Hooks.cancel(block)) {
            return;
        }

        String world = block.getWorld().getName();
        if (!Flags.flower_pot.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        Material mat = block.getType();

        if (mat == Material.FLOWER_POT || mat == Material.LEGACY_FLOWER_POT || mat.name().startsWith("POTTED_")) {

            if (Worlds.isDenyMessageExist(world)) {
                p.sendMessage(Worlds.getDenyMessage(world));
            }
            e.setCancelled(true);

        }
    }

    // Flag: minecart
    @EventHandler
    public void onMinecartInteract(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();
        ItemStack item = e.getItem();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null || item == null) {
            return;
        }

        if (Hooks.cancel(block)) {
            return;
        }

        String world = block.getWorld().getName();
        if (!Flags.minecart.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Flags.minecart.getType().equalsIgnoreCase("list")) {

            Material mat = item.getType();
            if (Flags.minecart.getList().contains(mat.toString().toUpperCase())) {
                if (Worlds.isDenyMessageExist(world)) {
                    p.sendMessage(Worlds.getDenyMessage(world));
                }
                e.setCancelled(true);
            }


        }
    }

    // Flag: egg throw
    @EventHandler
    public void onEggThrow(PlayerInteractEvent e) {

        Action action = e.getAction();
        ItemStack item = e.getItem();

        if (!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) || item == null || item.getType() != Material.EGG) {
            return;
        }

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        String world = p.getWorld().getName();
        if (!Flags.egg_throw.isEnabled(world)) {
            return;
        }


        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }
        e.setCancelled(true);

    }

    // Flag: snowball throw
    @EventHandler
    public void onSnowBallThrow(PlayerInteractEvent e) {

        Action action = e.getAction();
        ItemStack item = e.getItem();

        if (!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) || item == null || item.getType() != Material.SNOWBALL) {
            return;
        }

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        String world = p.getWorld().getName();
        if (!Flags.snowball_throw.isEnabled(world)) {
            return;
        }


        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }
        e.setCancelled(true);

    }

    // Flag: potion
    @EventHandler
    public void onPotionUse(PlayerInteractEvent e) {

        Action action = e.getAction();
        ItemStack item = e.getItem();

        if (!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
                || item == null || !(item.getType() == Material.POTION
                || item.getType() == Material.SPLASH_POTION)) {
            return;
        }

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        String world = p.getWorld().getName();
        if (!Flags.potion.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }
        e.setCancelled(true);

    }

    // Flag: farmbreak
    @EventHandler
    public void onFarmInteract(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action != Action.PHYSICAL || block == null
                || !(block.getType() == Material.matchMaterial("SOIL") || block.getType() == Material.matchMaterial("FARMLAND"))) {
            return;
        }

        if (Hooks.cancel(block)) {
            return;
        }

        String world = block.getWorld().getName();
        if (!Flags.farmbreak.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }

        e.setCancelled(true);

    }

    // Flag: container
    @EventHandler
    public void onContainerInteract(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null) {
            return;
        }

        if (Hooks.cancel(block)) {
            return;
        }

        String world = block.getWorld().getName();
        if (!Flags.container.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Flags.container.getType().equalsIgnoreCase("list")) {

            Material mat = block.getType();
            if (Flags.container.getList().contains(mat.toString().toUpperCase())) {
                if (Worlds.isDenyMessageExist(world)) {
                    p.sendMessage(Worlds.getDenyMessage(world));
                }
                e.setCancelled(true);
            }


        }

    }

    // Flag: boat
    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent e) {

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        String world = p.getWorld().getName();
        if (!Flags.boat.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (e.getRightClicked() instanceof Boat) {

            if (Worlds.isDenyMessageExist(world)) {
                p.sendMessage(Worlds.getDenyMessage(world));
            }
            e.setCancelled(true);
            return;
        }

        if (FlagsManager.BoatIsIncludingChestBoat()) {
            if (e.getRightClicked() instanceof ChestBoat) {
                if (Worlds.isDenyMessageExist(world)) {
                    p.sendMessage(Worlds.getDenyMessage(world));
                }
                e.setCancelled(true);
            }
        }

    }

    // Flag: Bed
    @EventHandler
    public void onBed(PlayerBedEnterEvent e) {

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        String world = p.getWorld().getName();
        if (!Flags.bed.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }
        e.setCancelled(true);

    }

    // Flag: Chat
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        String world = p.getWorld().getName();
        if (!Flags.chat.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }
        e.setCancelled(true);

    }

    // Flag: Command
    @EventHandler
    public void onCommandExecute(PlayerCommandPreprocessEvent e) {

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        String world = p.getWorld().getName();
        if (!Flags.command.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Flags.command.getType().equalsIgnoreCase("all")) {
            if (Worlds.isDenyMessageExist(world)) {
                p.sendMessage(Worlds.getDenyMessage(world));
            }
            e.setCancelled(true);
            return;
        }

        if (Flags.command.getType().equalsIgnoreCase("list")) {
            if (Flags.command.getList().contains("/" + e.getMessage())) {
                if (Worlds.isDenyMessageExist(world)) {
                    p.sendMessage(Worlds.getDenyMessage(world));
                }
                e.setCancelled(true);
            }
        }
    }

    // Flag: armor stand
    @EventHandler
    public void ArmorStand(PlayerArmorStandManipulateEvent e) {

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        String world = p.getWorld().getName();
        if (!Flags.armorstand.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }
        e.setCancelled(true);

    }

    // Flag: bucket place
    @EventHandler
    public void onBucketUse(PlayerBucketEmptyEvent e) {

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        String world = p.getWorld().getName();
        if (!Flags.bucket_place.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }
        e.setCancelled(true);

    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent e) {

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        String world = p.getWorld().getName();
        if (!Flags.bucket_fill.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }
        e.setCancelled(true);

    }

    // Flag: drop-item
    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        String world = p.getWorld().getName();
        if (!Flags.drop_item.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        Item droppedItem = e.getItemDrop();

        if (Flags.drop_item.getType().equalsIgnoreCase("all")) {
            if (Worlds.isDenyMessageExist(world)) {
                p.sendMessage(Worlds.getDenyMessage(world));
            }
            e.setCancelled(true);
            return;
        }

        if (Flags.drop_item.getType().equalsIgnoreCase("list")) {

            Material mat = droppedItem.getItemStack().getType();
            if (Flags.drop_item.getList().contains(mat.toString().toUpperCase())) {
                if (Worlds.isDenyMessageExist(world)) {
                    p.sendMessage(Worlds.getDenyMessage(world));
                }
                e.setCancelled(true);
            }
        }
    }

    // Flag: item-pickup
    @EventHandler
    public void onItemPickUp(PlayerPickupItemEvent e) {

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        String world = p.getWorld().getName();
        if (!Flags.item_pickup.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        Item item = e.getItem();

        if (Flags.item_pickup.getType().equalsIgnoreCase("all")) {
            if (Worlds.isDenyMessageExist(world)) {
                p.sendMessage(Worlds.getDenyMessage(world));
            }
            e.setCancelled(true);
            return;
        }

        if (Flags.item_pickup.getType().equalsIgnoreCase("list")) {

            Material mat = item.getItemStack().getType();
            if (Flags.item_pickup.getList().contains(mat.toString().toUpperCase())) {
                if (Worlds.isDenyMessageExist(world)) {
                    p.sendMessage(Worlds.getDenyMessage(world));
                }
                e.setCancelled(true);
            }
        }
    }

    // Flag: fly
    @EventHandler
    public void onFlightToggle(PlayerToggleFlightEvent e) {

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        if (!p.getAllowFlight()) {
            return;
        }

        String world = p.getWorld().getName();
        if (!Flags.fly.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }
        e.setCancelled(true);

    }

    // Flag: teleport (For Player)
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        String world = p.getWorld().getName();
        if (!Flags.teleport.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (e.getFrom().getWorld().getName().equalsIgnoreCase(world) || e.getTo().getWorld().getName().equalsIgnoreCase(world)) {

            if (Worlds.isDenyMessageExist(world)) {
                p.sendMessage(Worlds.getDenyMessage(world));
            }
            e.setCancelled(true);

        }
    }

    // Flag: potion
    @EventHandler
    public void onPotionEffect(PlayerItemConsumeEvent e) {

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        String world = p.getWorld().getName();
        if (!Flags.potion.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        Material mat = e.getItem().getType();

        if (mat == Material.POTION || mat == Material.SPLASH_POTION || mat == Material.LINGERING_POTION) {

            e.setCancelled(true);
            if (Worlds.isDenyMessageExist(world)) {
                p.sendMessage(Worlds.getDenyMessage(world));
            }

        }

    }


    // Flag: dye
    @EventHandler
    public void onSheepDye(SheepDyeWoolEvent e) {

        Entity entity = e.getEntity();

        if (Hooks.cancel(entity)) {
            return;
        }

        String world = entity.getWorld().getName();
        if (!Flags.dye.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }
        e.setCancelled(true);

    }

    // Flag: hook (To Players, NOT Fish)
    @EventHandler
    public void onHook(PlayerFishEvent e) {

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        if (!(e.getCaught() instanceof Player)) {
            return;
        }

        String world = p.getWorld().getName();
        if (!Flags.hook.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }
        e.setCancelled(true);

    }

    // Flag: fish (To fish, NOT players)
    @EventHandler
    public void onFish(PlayerFishEvent e) {

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        if (e.getCaught() instanceof Player) {
            return;
        }

        String world = p.getWorld().getName();
        if (!Flags.fish.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }
        e.setCancelled(true);

    }

    // Flag: hunger
    @EventHandler
    public void onHungerLost(FoodLevelChangeEvent e) {

        Player p = (Player) e.getEntity();

        if (Hooks.cancel(p)) {
            return;
        }

        String world = p.getWorld().getName();
        if (!Flags.hunger.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        e.setCancelled(true);

    }
}
