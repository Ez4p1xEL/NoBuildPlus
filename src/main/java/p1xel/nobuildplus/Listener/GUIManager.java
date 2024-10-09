package p1xel.nobuildplus.Listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.Storage.FlagsManager;
import p1xel.nobuildplus.Storage.Locale;
import p1xel.nobuildplus.Storage.Worlds;

import java.util.*;

public class GUIManager implements Listener {

    public static GUIManager instance = new GUIManager();

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

        if (p.getOpenInventory().getTitle().contains(Locale.getMessage("gui.title"))) {

            ItemStack id = inventory.getItem(0);
            NamespacedKey keyId = new NamespacedKey(NoBuildPlus.getInstance(), "inventory");
            String world = id.getItemMeta().getPersistentDataContainer().get(keyId, PersistentDataType.STRING);

            int slot = e.getSlot();
            ItemStack item = e.getCurrentItem();

            // If it's the first slot
            if (slot == 0 || slot == 8) {
                e.setCancelled(true);
                return;

            }

            if (slot == 45) {

                if (item == null) {
                    e.setCancelled(true);
                    return;
                }

                NamespacedKey page_id = new NamespacedKey(NoBuildPlus.getInstance(), "page");
                int page = Integer.parseInt(inventory.getItem(8).getItemMeta().getPersistentDataContainer().get(page_id, PersistentDataType.STRING));

                if (page-1 == 0) {
                    e.setCancelled(true);
                    return;
                }

                p.openInventory(GUIManager.instance.getGUI(world + "_page" + (page-1)));
                e.setCancelled(true);
                return;

            }

            if (slot == 53) {

                if (item == null) {
                    e.setCancelled(true);
                    return;
                }

                NamespacedKey page_id = new NamespacedKey(NoBuildPlus.getInstance(), "page");
                int page = Integer.parseInt(inventory.getItem(8).getItemMeta().getPersistentDataContainer().get(page_id, PersistentDataType.STRING));
                int max_page = FlagsManager.getMaxPage(FlagsManager.getFlags());

                if (page+1 > max_page) {
                    e.setCancelled(true);
                    return;
                }

                p.openInventory(GUIManager.instance.getGUI(world + "_page" + (page+1)));
                e.setCancelled(true);
                return;

            }

            if (item != null) {

                ItemMeta meta = item.getItemMeta();
                NamespacedKey key = new NamespacedKey(NoBuildPlus.getInstance(), "flag");
                String flag = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
                boolean bool = Worlds.getFlag(world, flag);
                bool = !bool;
                Worlds.setFlag(world, flag, bool);

                // Update GUI item
                meta.setDisplayName(Locale.getMessage("gui.display_name").replaceAll("%flag%", flag).replaceAll("%bool%", Locale.getMessage(String.valueOf(bool))));
                item.setItemMeta(meta);
                inventory.setItem(slot, item);


                e.setCancelled(true);
                return;

            }

            e.setCancelled(true);


        }
    }

    HashMap<String, Inventory> guis = new HashMap<>();

    public Inventory getGUI(String world) {
        return guis.get(world);
    }

    public void initialization() {

        for (String world : Worlds.yaml.getKeys(false)) {

            createInv(world);

        }

        Bukkit.getLogger().info("Initialization completed: " + guis.toString());

    }

    ItemStack getIconItem(ItemStack item, String locale_path, String key, String data) {

        ItemMeta meta = item.getItemMeta();
        NamespacedKey n_key = new NamespacedKey(NoBuildPlus.getInstance(), key);
        meta.getPersistentDataContainer().set(n_key, PersistentDataType.STRING, data);
        meta.setDisplayName(Locale.getMessage("gui."+locale_path));
        item.setItemMeta(meta);
        return item;

    }

    public void createInv(String world) {

        List<String> flags = FlagsManager.getFlags();
        int max_page = FlagsManager.getMaxPage(flags);

        int a = 1;

        for (int i = 1; i <= max_page;i++) {


            Inventory gui = Bukkit.createInventory(null,54,Locale.getMessage("gui.title") + " " + world);

            if (i != max_page) {
                // "Next" Icon
                gui.setItem(53, getIconItem(new ItemStack(Material.PAPER),"page_next", "function", "next"));
            }

            if (i != 1) {
                // "Previous" Icon
                gui.setItem(45, getIconItem(new ItemStack(Material.PAPER),"page_previous", "function", "previous"));
            }

            // Set Icons as ids
            gui.setItem(0, getIconItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE), "world", "inventory", world));
            gui.setItem(8, getIconItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE), "page", "page", String.valueOf(i)));

            for (int c =0;c < 28; c++) {

                String flag = flags.get(a-1);

                int slot = FlagsManager.getSlot(flag);

                // Each page
                Material mat = Material.matchMaterial(FlagsManager.getShowedItem(flag).toUpperCase());
                if (mat == null) {
                    Bukkit.getLogger().info("[NBP] Could not find show-item for " + flag + "!");
                    break;
                }
                ItemStack item = new ItemStack(mat);
                ItemMeta meta = item.getItemMeta();

                boolean bool = Worlds.getFlag(world, flag);

                assert meta != null;
                meta.setDisplayName(Locale.getMessage("gui.display_name").replaceAll("%flag%", flag).replaceAll("%bool%", Locale.getMessage(String.valueOf(bool))));
                List<String> lores = new ArrayList<>();
                for (String l : Locale.yaml.getStringList("gui.lore")) {

                    l = Locale.translate(l);
                    l = l.replaceAll("%flag%", flag);
                    l = l.replaceAll("%bool%", String.valueOf(bool));
                    l = l.replaceAll("%description%", Locale.getMessage("gui.description." + flag));
                    lores.add(l);

                }
                meta.setLore(lores);

                NamespacedKey key = new NamespacedKey(NoBuildPlus.getInstance(), "flag");
                meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, flag);

                item.setItemMeta(meta);
                gui.setItem(slot, item);
                a++;
                if ((a/(i*28))>1) {
                    break;
                }

                if (a > flags.size()) {
                    break;
                }


            }


            guis.put(world+"_page" + i, gui);


        }

    }

    public void updateFlag(String world, String flag, boolean bool) {

        int max_page = FlagsManager.getMaxPage(FlagsManager.getFlags());

        for (int i = 1;i<=max_page;i++) {

            Inventory gui = guis.get(world+"_page"+i);

            for (ItemStack item : gui.getContents()) {

                if (item != null) {
                    ItemMeta meta = item.getItemMeta();
                    if (meta != null) {
                        NamespacedKey key = new NamespacedKey(NoBuildPlus.getInstance(), "flag");
                        if (meta.getPersistentDataContainer().has(key, PersistentDataType.STRING) && Objects.equals(meta.getPersistentDataContainer().get(key, PersistentDataType.STRING), flag)) {
                            meta.setDisplayName(Locale.getMessage("gui.display_name").replaceAll("%flag%", flag).replaceAll("%bool%", Locale.getMessage(String.valueOf(bool))));
                            List<String> lores = new ArrayList<>();
                            for (String l : Locale.yaml.getStringList("gui.lore")) {

                                l = Locale.translate(l);
                                l = l.replaceAll("%flag%", flag);
                                l = l.replaceAll("%bool%", String.valueOf(bool));
                                l = l.replaceAll("%description%", Locale.getMessage("gui.description." + flag));
                                lores.add(l);

                            }
                            meta.setLore(lores);
                            item.setItemMeta(meta);
                            gui.setItem(FlagsManager.getSlot(flag), item);
                            guis.replace(world+"_page"+i, gui);
                            return;
                        }
                    }
                }

            }
        }

    }

    public void removeWorld(String world) {

        int max_page = FlagsManager.getMaxPage(FlagsManager.getFlags());

        for (int i=1;i<=max_page;i++) {
            guis.remove(world+"_page" +i);
        }

    }

    public void reloadGUIs() {

        guis.clear();
        GUIManager.instance.initialization();
    }

}
