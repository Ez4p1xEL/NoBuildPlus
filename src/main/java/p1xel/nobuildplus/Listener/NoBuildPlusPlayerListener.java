package p1xel.nobuildplus.Listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import p1xel.nobuildplus.Hook.HRes;
import p1xel.nobuildplus.Storage.FlagsManager;
import p1xel.nobuildplus.Storage.Settings;
import p1xel.nobuildplus.Storage.Worlds;

public class NoBuildPlusPlayerListener implements Listener {

    // Flag: Use
    // Flag: Container
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();
        Action action = e.getAction();

        if (e.getClickedBlock() != null) {
            if (HRes.isInRes(e.getClickedBlock())) {
                return;
            }
        }

        // RIGHT CLICK BLOCK
        if (action == Action.RIGHT_CLICK_BLOCK) {

            boolean contain = Settings.getEnableWorldList().contains(world);
            Material clicked = e.getClickedBlock().getType();

            boolean hasPerm = false;

            if (contain) {
                hasPerm = p.hasPermission(Worlds.getPermission(world));
            }

            // Flag: Use
            if (Settings.canExecute(world, "use")) {

                if (!Worlds.getFlag(world, "use")) {

                    if (!hasPerm) {

                        if (FlagsManager.getFlagsType("use").equalsIgnoreCase("list")) {

                            for (String name : FlagsManager.getFlagsList("use")) {
                                Material block = Material.matchMaterial(name);
                                if (block != null) {
                                    if (clicked == block) {
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
                }

            }

            // Flag: button
            if (Settings.canExecute(world, "button")) {

                if (!Worlds.getFlag(world, "button")) {

                    if (!hasPerm) {

                        if (FlagsManager.getFlagsType("button").equalsIgnoreCase("list")) {

                            for (String name : FlagsManager.getFlagsList("button")) {
                                Material block = Material.matchMaterial(name);
                                if (block != null) {
                                    if (clicked == block) {
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
                }

            }

            // Flag: door-interact
            if (Settings.canExecute(world, "door-interact")) {

                if (!Worlds.getFlag(world, "door-interact")) {

                    if (!hasPerm) {

                        if (FlagsManager.getFlagsType("door-interact").equalsIgnoreCase("list")) {

                            for (String name : FlagsManager.getFlagsList("door-interact")) {
                                Material block = Material.matchMaterial(name);
                                if (block != null) {
                                    if (clicked == block) {
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
                }

            }

            // Flag: lever
            if (clicked == Material.LEVER) {
                if (Settings.canExecute(world, "lever")) {

                    if (!Worlds.getFlag(world, "lever")) {

                        if (!hasPerm) {
                            if (Worlds.isDenyMessageExist(world)) {
                                p.sendMessage(Worlds.getDenyMessage(world));
                            }
                            e.setCancelled(true);
                            return;
                        }
                    }

                }
            }

            // Flag: trapdoor-interact
            if (Settings.canExecute(world, "trapdoor-interact")) {

                if (!Worlds.getFlag(world, "trapdoor-interact")) {

                    if (!hasPerm) {

                        if (FlagsManager.getFlagsType("trapdoor-interact").equalsIgnoreCase("list")) {

                            for (String name : FlagsManager.getFlagsList("trapdoor-interact")) {
                                Material block = Material.matchMaterial(name);
                                if (block != null) {
                                    if (clicked == block) {
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
                }
            }

            // Flag: fencegate-interact
            if (Settings.canExecute(world, "fencegate-interact")) {

                if (!Worlds.getFlag(world, "fencegate-interact")) {

                    if (!hasPerm) {

                        if (FlagsManager.getFlagsType("fencegate-interact").equalsIgnoreCase("list")) {

                            Block block = e.getClickedBlock();
                            if (block != null) {
                                Material mat = block.getType();
                                if (FlagsManager.getFlagsList("fencegate-interact").contains(mat.toString().toUpperCase())) {
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
            }

            // Flag: bonemeal
            if (Settings.canExecute(world, "bonemeal")) {

                if (!Worlds.getFlag(world, "bonemeal")) {

                    if (!hasPerm) {

                        ItemStack item = e.getItem();
                        if (item != null) {
                            Material mat = item.getType();
                            if (mat == Material.BONE_MEAL) {
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

            // Flag: boat
            ItemStack item = e.getItem();
            if (item != null) {
                for (String key : FlagsManager.getFlagsList("boat")) {

                    if (item.getType() == Material.matchMaterial(key)) {

                        if (Settings.canExecute(world, "boat")) {

                            if (!Worlds.getFlag(world, "boat")) {

                                if (!hasPerm) {

                                    if (Worlds.isDenyMessageExist(world)) {
                                        p.sendMessage(Worlds.getDenyMessage(world));
                                    }
                                    e.setCancelled(true);
                                    return;

                                }
                            }

                        }
                        return;
                    }
                }
            }

        } // THE END OF THE "RIGHT CLICK BLOCK"

        // RIGHT CLICK BLOCK OR RIGHT CLICK AIR
        if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {

            ItemStack item = e.getItem();

            if (item != null) {

                // Flag: minecart
                for (String key : FlagsManager.getFlagsList("minecart")) {

                    if (e.getItem().getType() == Material.matchMaterial(key)) {

                        if (Settings.canExecute(world, "minecart")) {

                            boolean hasPerm = p.hasPermission(Worlds.getPermission(world));

                            if (!Worlds.getFlag(world, "minecart")) {

                                if (!hasPerm) {

                                    if (Worlds.isDenyMessageExist(world)) {
                                        p.sendMessage(Worlds.getDenyMessage(world));
                                    }
                                    e.setCancelled(true);
                                    return;

                                }
                            }
                        }

                        return;
                    }
                }

                // Flag: egg-throw
                if (Settings.canExecute(world, "egg-throw")) {

                    boolean hasPerm = p.hasPermission(Worlds.getPermission(world));

                    if (!Worlds.getFlag(world, "egg-throw")) {

                        if (!hasPerm) {
                            if (e.getItem().getType() == Material.EGG) {
                                if (Worlds.isDenyMessageExist(world)) {
                                    p.sendMessage(Worlds.getDenyMessage(world));
                                }
                                e.setCancelled(true);
                                return;
                            }
                        }
                    }

                }

                // Flag: snowball-throw
                if (Settings.canExecute(world, "snowball-throw")) {

                    boolean hasPerm = p.hasPermission(Worlds.getPermission(world));

                    if (!Worlds.getFlag(world, "snowball-throw")) {

                        if (!hasPerm) {
                            if (e.getItem().getType() == Material.matchMaterial("SNOWBALL")) {
                                if (Worlds.isDenyMessageExist(world)) {
                                    p.sendMessage(Worlds.getDenyMessage(world));
                                }
                                e.setCancelled(true);
                                return;
                            }
                        }
                    }
                }

                if (Settings.canExecute(world, "potion")) {

                    if (!Worlds.getFlag(world, "potion")) {

                        Material mat = item.getType();

                        if (mat == Material.LINGERING_POTION || mat == Material.SPLASH_POTION ) {

                            if (!p.hasPermission(Worlds.getPermission(world))) {

                                e.setCancelled(true);
                                p.sendMessage(Worlds.getDenyMessage(world));

                            }

                            return;
                        }

                    }

                }

            }

        } // THE END OF THE "RIGHT CLICK BLOCK OR RIGHT CLICK AIR"

        // Flag: farmbreak
        if (action == Action.PHYSICAL) {
            if (Settings.canExecute(world, "farmbreak")) {

                if (!Worlds.getFlag(world, "farmbreak")) {
                    Material clicked = e.getClickedBlock().getType();

                    if (clicked == Material.matchMaterial("SOIL") || clicked == Material.matchMaterial("FARMLAND")) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }



        // Flag: Container
        if (Settings.canExecute(world, "container")) {

            if (Worlds.getFlag(world, "container")) {
                return;
            }

            if (p.hasPermission(Worlds.getPermission(world))) {
                return;
            }

            if (FlagsManager.getFlagsType("container").equalsIgnoreCase("list")) {

                Block block = e.getClickedBlock();

                if (block != null) {
                    Material mat = block.getType();
                    if (e.getAction() == Action.RIGHT_CLICK_BLOCK && FlagsManager.getFlagsList("container").contains(mat.toString().toUpperCase())) {
                        if (Worlds.isDenyMessageExist(world)) {
                            p.sendMessage(Worlds.getDenyMessage(world));
                        }
                        e.setCancelled(true);
                    }
                }
            }

        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        // Flag: boat (Use)
        if (Settings.canExecute(world, "boat")) {

            if (!Worlds.getFlag(world, "boat")) {

                if (!p.hasPermission(Worlds.getPermission(world))) {

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
                            return;
                        }
                    }

                }
            }
        }
    }

    // Flag: Bed
    @EventHandler
    public void onBed(PlayerBedEnterEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (HRes.isInRes(p)) {
            return;
        }


        if (Settings.canExecute(world, "bed")) {

            if (!Worlds.getFlag(world, "bed")) {

                if (!p.hasPermission(Worlds.getPermission(world))) {

                    if (Worlds.isDenyMessageExist(world)) {
                        p.sendMessage(Worlds.getDenyMessage(world));
                    }
                    e.setCancelled(true);

                }
            }


        }

    }

    // Flag: Chat
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (HRes.isInRes(p)) {
            return;
        }

        if (Settings.canExecute(world, "chat")) {

            if (!Worlds.getFlag(world, "chat")) {

                if (!p.hasPermission(Worlds.getPermission(world))) {

                    if (Worlds.isDenyMessageExist(world)) {
                        p.sendMessage(Worlds.getDenyMessage(world));
                    }
                    e.setCancelled(true);

                }
            }

        }

    }

    // Flag: Command
    @EventHandler
    public void onCommandExecute(PlayerCommandPreprocessEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (HRes.isInRes(p)) {
            return;
        }

        if (Settings.canExecute(world, "command")) {

            if (!Worlds.getFlag(world, "command")) {

                if (!p.hasPermission(Worlds.getPermission(world))) {

                    if (FlagsManager.getFlagsType("command").equalsIgnoreCase("all")) {
                        if (Worlds.isDenyMessageExist(world)) {
                            p.sendMessage(Worlds.getDenyMessage(world));
                        }
                        e.setCancelled(true);
                        return;
                    }

                    if (FlagsManager.getFlagsType("command").equalsIgnoreCase("list")) {
                        if (FlagsManager.getFlagsList("command").contains("/" + e.getMessage())) {
                            if (Worlds.isDenyMessageExist(world)) {
                                p.sendMessage(Worlds.getDenyMessage(world));
                            }
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void ArmorStand(PlayerArmorStandManipulateEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (HRes.isInRes(p)) {
            return;
        }

        if (Settings.canExecute(world, "armorstand")) {

            if (!Worlds.getFlag(world, "armorstand")) {

                if (!p.hasPermission(Worlds.getPermission(world))) {

                    if (Worlds.isDenyMessageExist(world)) {
                        p.sendMessage(Worlds.getDenyMessage(world));
                    }
                    e.setCancelled(true);

                }

            }

        }

    }

    @EventHandler
    public void onBucketUse(PlayerBucketEmptyEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (HRes.isInRes(p)) {
            return;
        }

        if (Settings.canExecute(world, "bucket-place")) {

            if (!Worlds.getFlag(world, "bucket-place")) {

                if (!p.hasPermission(Worlds.getPermission(world))) {

                    if (Worlds.isDenyMessageExist(world)) {
                        p.sendMessage(Worlds.getDenyMessage(world));
                    }
                    e.setCancelled(true);

                }

            }

        }

    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (HRes.isInRes(p)) {
            return;
        }

        if (Settings.canExecute(world, "bucket-fill")) {

            if (!Worlds.getFlag(world, "bucket-fill")) {

                if (!p.hasPermission(Worlds.getPermission(world))) {

                    if (Worlds.isDenyMessageExist(world)) {
                        p.sendMessage(Worlds.getDenyMessage(world));
                    }
                    e.setCancelled(true);
                }
            }
        }
    }

    // Flag: drop-item
    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();
        Item droppedItem = e.getItemDrop();


        if (HRes.isInRes(p)) {
            return;
        }

        // Flag: drop-item (For Player)
        if (Settings.canExecute(world, "drop-item")) {

            boolean hasPerm = p.hasPermission(Worlds.getPermission(world));

            if (!Worlds.getFlag(world, "drop-item")) {

                if (!hasPerm) {

                    String type = FlagsManager.getFlagsType("drop-item");
                    String m = Worlds.getDenyMessage(world);

                    if (type.equalsIgnoreCase("all")) {
                        if (Worlds.isDenyMessageExist(world)) {
                            p.sendMessage(m);
                        }
                        e.setCancelled(true);
                        return;
                    }

                    if (type.equalsIgnoreCase("list")) {

                        Material mat = droppedItem.getItemStack().getType();
                        if (FlagsManager.getFlagsList("drop-item").contains(mat.toString().toUpperCase())) {
                            if (Worlds.isDenyMessageExist(world)) {
                                p.sendMessage(m);
                            }
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    // Flag: item-pickup
    @EventHandler
    public void onItemPickUp(EntityPickupItemEvent e) {

        Entity p = e.getEntity();
        Item item = e.getItem();
        String world = p.getWorld().getName();

        if (HRes.isInRes(p)) {
            return;
        }

        // Flag: drop-item (For Player)
        if (Settings.canExecute(world, "item-pickup")) {

            boolean hasPerm = p.hasPermission(Worlds.getPermission(world));

            if (!Worlds.getFlag(world, "item-pickup")) {

                if (!hasPerm) {

                    String type = FlagsManager.getFlagsType("item-pickup");
                    String m = Worlds.getDenyMessage(world);

                    if (type.equalsIgnoreCase("all")) {
                        if (Worlds.isDenyMessageExist(world)) {
                            p.sendMessage(m);
                        }
                        e.setCancelled(true);
                        return;
                    }

                    if (type.equalsIgnoreCase("list")) {


                        Material mat = item.getItemStack().getType();
                        if (FlagsManager.getFlagsList("item-pickup").contains(mat.toString().toUpperCase())) {
                            if (Worlds.isDenyMessageExist(world)) {
                                p.sendMessage(m);
                            }
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onFlightToggle(PlayerToggleFlightEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (HRes.isInRes(p)) {
            return;
        }

        // Flag: fly
        if (p.getAllowFlight()) {

            if (Settings.canExecute(world, "fly")) {

                if (!Worlds.getFlag(world, "fly")) {

                    if (!p.hasPermission(Worlds.getPermission(world))) {

                        if (Worlds.isDenyMessageExist(world)) {
                            p.sendMessage(Worlds.getDenyMessage(world));
                        }
                        e.setCancelled(true);

                    }
                }
            }

        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (HRes.isInRes(p)) {
            return;
        }

        // Flag: teleport (For Player)

        if (Settings.canExecute(world, "teleport")) {

            if (!Worlds.getFlag(world, "teleport")) {

                if (!p.hasPermission(Worlds.getPermission(world))) {

                    if (e.getFrom().getWorld().getName().equalsIgnoreCase(world) || e.getTo().getWorld().getName().equalsIgnoreCase(world)) {

                        if (Worlds.isDenyMessageExist(world)) {
                            p.sendMessage(Worlds.getDenyMessage(world));
                        }
                        e.setCancelled(true);

                    }

                }

            }
        }
    }

    // Flag: potion
    @EventHandler
    public void onPotionEffect(PlayerItemConsumeEvent e) {

        Player p = e.getPlayer();
        String world = p.getWorld().getName();

        if (HRes.isInRes(p)) {
            return;
        }

        if (Settings.canExecute(world, "potion")) {

            if (!Worlds.getFlag(world, "potion")) {

                Material mat = e.getItem().getType();

                if (mat == Material.POTION || mat == Material.SPLASH_POTION || mat == Material.LINGERING_POTION) {

                    if (!p.hasPermission(Worlds.getPermission(world))) {

                        e.setCancelled(true);
                        if (Worlds.isDenyMessageExist(world)) {
                            p.sendMessage(Worlds.getDenyMessage(world));
                        }

                    }

                }

            }
        }


    }

    // Flag: sign-edit
    @EventHandler
    public void onSignOpen(PlayerSignOpenEvent e) {
        Player p = e.getPlayer();
        String world = p.getWorld().getName();

        if (HRes.isInRes(p)) {
            return;
        }
        if (e.getCause() == PlayerSignOpenEvent.Cause.INTERACT) {
            if (Settings.canExecute(world, "sign-edit")) {
                if (!Worlds.getFlag(world, "sign-edit")) {
                    if (!p.hasPermission(Worlds.getPermission(world))) {
                        e.setCancelled(true);
                        if (Worlds.isDenyMessageExist(world)) {
                            p.sendMessage(Worlds.getDenyMessage(world));
                        }

                    }
                }
            }
        }
    }

    // Flag: dye
    @EventHandler
    public void onSheepDye(SheepDyeWoolEvent e) {
        Player p = e.getPlayer();
        String world = p.getWorld().getName();

        if (HRes.isInRes(p)) {
            return;
        }

        if (Settings.canExecute(world, "dye")) {
            if (!Worlds.getFlag(world, "dye")) {

                if (!p.hasPermission(Worlds.getPermission(world))) {
                    e.setCancelled(true);
                    if (Worlds.isDenyMessageExist(world)) {
                        p.sendMessage(Worlds.getDenyMessage(world));
                    }

                }

            }
        }
    }

    // Flag: fish
    // Flag: hook
    @EventHandler
    public void onFish(PlayerFishEvent e) {

        Player p = e.getPlayer();
        String world = p.getWorld().getName();

        if (HRes.isInRes(p)) {
            return;
        }

        Entity target = e.getCaught();

        // Flag: hook (To Players, NOT Fish)
        if (target instanceof Player) {

            if (Settings.canExecute(world, "hook")) {
                if (!Worlds.getFlag(world, "hook")) {
                    if (!p.hasPermission(Worlds.getPermission(world))) {
                        if (Worlds.isDenyMessageExist(world)) {
                            p.sendMessage(Worlds.getDenyMessage(world));
                        }
                        e.setCancelled(true);
                    }
                }
            }
            return;
        }

        // Flag: fish

        if (Settings.canExecute(world, "fish")) {

            if (!Worlds.getFlag(world, "fish")) {
                if (!p.hasPermission(Worlds.getPermission(world))) {
                    if (Worlds.isDenyMessageExist(world)) {
                        p.sendMessage(Worlds.getDenyMessage(world));
                    }
                    e.setCancelled(true);
                }
            }

        }

    }
}
