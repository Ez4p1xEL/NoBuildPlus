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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import p1xel.nobuildplus.listener.version.FeatureListener;

public class GUIListener implements FeatureListener {

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
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                PersistentDataContainer container = meta.getPersistentDataContainer();
                if (container.has(menu_id_key, PersistentDataType.STRING)) {
                    GUIMain gui = (GUIMain) holder;
                    gui.check(p, container.get(menu_id_key, PersistentDataType.STRING), e.getClick());
                }
            }
            e.setCancelled(true);
            return;
        }

        if (holder instanceof GUIWorld) {

            ItemStack item = inventory.getItem(e.getSlot());
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                PersistentDataContainer container = meta.getPersistentDataContainer();
                if (container.has(menu_id_key, PersistentDataType.STRING)) {
                    GUIWorld gui = (GUIWorld) holder;
                    gui.check(p, container.get(menu_id_key, PersistentDataType.STRING), e.getSlot());
                }
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
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                PersistentDataContainer container = meta.getPersistentDataContainer();
                if (container.has(menu_id_key, PersistentDataType.STRING)) {
                    GUIDefaultTemplate gui = (GUIDefaultTemplate) holder;
                    gui.check(p, container.get(menu_id_key, PersistentDataType.STRING), e.getSlot());
                }
            }
            e.setCancelled(true);
            return;

        }


    }

    @Override
    public String getName() {
        return "GUIListener 1.15.2+";
    }

    @Override
    public boolean matchRequirement(int[] version) {
        return version[0] > 1 || (version[0] == 1 && version[1] >= 15 && version[2] >= 2);
    }
}
