package p1xel.nobuildplus.listener.gui;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class GUIListener implements Listener {

    private final NamespacedKey menu_id_key = new NamespacedKey("nobuildplus", "menu_id");

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        HumanEntity entity = e.getWhoClicked();

        if (!(entity instanceof Player)) {
            return;
        }

        Inventory inventory = e.getClickedInventory();

        if (inventory == null) {
            return;
        }

        Player p = (Player) entity;

        InventoryHolder holder = inventory.getHolder();
        if (holder instanceof GUIMain) {

            ItemStack item = inventory.getItem(e.getSlot());
            PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
            if (container.has(menu_id_key, PersistentDataType.STRING)) {
                GUIMain gui = (GUIMain) holder;
                gui.check(p, container.get(menu_id_key, PersistentDataType.STRING), e.getClick());
            }
            e.setCancelled(true);
            return;
        }

        if (holder instanceof GUIWorld) {

            ItemStack item = inventory.getItem(e.getSlot());
            PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
            if (container.has(menu_id_key, PersistentDataType.STRING)) {
                GUIWorld gui = (GUIWorld) holder;
                gui.check(p, container.get(menu_id_key, PersistentDataType.STRING), e.getSlot());
            }
            e.setCancelled(true);
            return;
        }

        if (holder instanceof GUIWorldList) {

            ItemStack item = inventory.getItem(e.getSlot());
            PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
            if (container.has(menu_id_key, PersistentDataType.STRING)) {
                GUIWorldList gui = (GUIWorldList) holder;
                gui.check(p, container.get(menu_id_key, PersistentDataType.STRING), e.getClick());
            }
            e.setCancelled(true);
            return;
        }

        if (holder instanceof GUIDefaultTemplate) {

            ItemStack item = inventory.getItem(e.getSlot());
            PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
            if (container.has(menu_id_key, PersistentDataType.STRING)) {
                GUIDefaultTemplate gui = (GUIDefaultTemplate) holder;
                gui.check(p, container.get(menu_id_key, PersistentDataType.STRING), e.getSlot());
            }
            e.setCancelled(true);
            return;

        }

//        if (p.getOpenInventory().getTitle().contains(Locale.getMessage("gui.world.title"))) {
//
//            ItemStack id = inventory.getItem(0);
//            NamespacedKey keyId = new NamespacedKey(NoBuildPlus.getInstance(), "inventory");
//            String world = id.getItemMeta().getPersistentDataContainer().get(keyId, PersistentDataType.STRING);
//
//            int slot = e.getSlot();
//            ItemStack item = e.getCurrentItem();
//
//            // If it's the first slot
//            if (slot == 0 || slot == 8) {
//                e.setCancelled(true);
//                return;
//
//            }
//
//            if (slot == 49) {
//                p.openInventory(new GUIMain(1).getInventory());
//                e.setCancelled(true);
//                return;
//            }
//
//            if (slot == 45) {
//
//                if (item == null) {
//                    e.setCancelled(true);
//                    return;
//                }
//
//                NamespacedKey page_id = new NamespacedKey(NoBuildPlus.getInstance(), "page");
//                int page = Integer.parseInt(inventory.getItem(8).getItemMeta().getPersistentDataContainer().get(page_id, PersistentDataType.STRING));
//
//                if (page-1 == 0) {
//                    e.setCancelled(true);
//                    return;
//                }
//
//                p.openInventory(GUIManager.instance.getGUI(world + "_page" + (page-1)));
//                e.setCancelled(true);
//                return;
//
//            }
//
//            if (slot == 53) {
//
//                if (item == null) {
//                    e.setCancelled(true);
//                    return;
//                }
//
//                NamespacedKey page_id = new NamespacedKey(NoBuildPlus.getInstance(), "page");
//                int page = Integer.parseInt(inventory.getItem(8).getItemMeta().getPersistentDataContainer().get(page_id, PersistentDataType.STRING));
//                int max_page = FlagsManager.getMaxPage(FlagsManager.getFlags());
//
//                if (page+1 > max_page) {
//                    e.setCancelled(true);
//                    return;
//                }
//
//                p.openInventory(GUIManager.instance.getGUI(world + "_page" + (page+1)));
//                e.setCancelled(true);
//                return;
//
//            }
//
//            if (item != null) {
//
//                ItemMeta meta = item.getItemMeta();
//                NamespacedKey key = new NamespacedKey(NoBuildPlus.getInstance(), "flag");
//                String flag = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
//                boolean bool = Worlds.getFlag(world, flag);
//                bool = !bool;
//                Worlds.setFlag(world, flag, bool);
//
//                // Update GUI item
//                meta.setDisplayName(Locale.getMessage("gui.world.display_name").replaceAll("%flag%", flag).replaceAll("%bool%", Locale.getMessage(String.valueOf(bool))));
//                item.setItemMeta(meta);
//                inventory.setItem(slot, item);
//
//
//                e.setCancelled(true);
//                return;
//
//            }


    }
}
