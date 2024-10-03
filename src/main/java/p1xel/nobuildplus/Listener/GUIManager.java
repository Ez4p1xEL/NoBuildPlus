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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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

            // If it is page2+
            if (slot == 45) {
                if (item == null) {
                    e.setCancelled(true);
                    return;
                }

                NamespacedKey key = new NamespacedKey(NoBuildPlus.getInstance(), "page");
                ItemStack nb = inventory.getItem(8);
                if (nb != null) {
                    String page = nb.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);
                    // If the current page is 3
                    // Back to page 2
                    if (page != null && page.equalsIgnoreCase("3")) {

                        p.openInventory(GUIManager.instance.getGUI(world + "_page2"));
                        e.setCancelled(true);
                        return;

                    }

                }

                // If current page is 2
                // Back to page 1

                p.openInventory(GUIManager.instance.getGUI(world));
                e.setCancelled(true);
                return;

            }


            // Go Next (If it is page1+)
            if (slot == 53) {
                if (item == null) {
                    e.setCancelled(true);
                    return;
                }

                NamespacedKey key = new NamespacedKey(NoBuildPlus.getInstance(), "page");
                ItemStack nb = inventory.getItem(8);
                if ( nb != null) {
                    String page = nb.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);
                    // If the current page is 2
                    // Go to page 3
                    if (page != null && page.equalsIgnoreCase("2")) {

                        p.openInventory(GUIManager.instance.getGUI(world + "_page3"));
                        e.setCancelled(true);
                        return;

                    }
                }
                // If the current page is 1
                // Go to page 2

                p.openInventory(GUIManager.instance.getGUI(world+"_page2"));
                e.setCancelled(true);
                return;
            }

            int page = 0;
            ItemStack pageItem = inventory.getItem(8);
            if (pageItem == null) {
                page = 1;
            } else {
                NamespacedKey key = new NamespacedKey(NoBuildPlus.getInstance(), "page");
                page = Integer.parseInt(pageItem.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING));
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

            createInventory(world);

        }

        Bukkit.getLogger().info("Initialization completed: " + guis.toString());

    }

    public void createInventory(String world) {

        List<String> flags = FlagsManager.getFlags();
        Inventory gui = Bukkit.createInventory(null, 54, Locale.getMessage("gui.title") + " " + world);
        Inventory gui2 = Bukkit.createInventory(null, 54, Locale.getMessage("gui.title") + " " + world);
        Inventory gui3 = Bukkit.createInventory(null, 54, Locale.getMessage("gui.title") + " " + world);

        int a = 1;

        for (String flag : flags) {

            int slot = FlagsManager.getSlot(flag);

            // Page 1
            if (a <= 28) {
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

            }

            // Page 2
            if (a > 28 && a <= 56) {

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
                gui2.setItem(slot, item);

            }

            // Page 3

            if (a > 56 && a <= 84) {

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
                gui3.setItem(slot, item);

            }

            a++;

        }

        // NEXT button
        ItemStack next = new ItemStack(Material.PAPER);
        ItemMeta nextMeta = next.getItemMeta();
        NamespacedKey keyNext = new NamespacedKey(NoBuildPlus.getInstance(), "function");
        nextMeta.getPersistentDataContainer().set(keyNext, PersistentDataType.STRING, "next");
        nextMeta.setDisplayName(Locale.getMessage("gui.page_next"));
        next.setItemMeta(nextMeta);

        // PREVIOUS button
        ItemStack previous = new ItemStack(Material.PAPER);
        ItemMeta previousMeta = previous.getItemMeta();
        NamespacedKey keyPrevious = new NamespacedKey(NoBuildPlus.getInstance(), "function");
        previousMeta.getPersistentDataContainer().set(keyPrevious, PersistentDataType.STRING, "previous");
        previousMeta.setDisplayName(Locale.getMessage("gui.page_previous"));
        previous.setItemMeta(previousMeta);

        // Set NEXT & PREVIOUS button
        gui.setItem(53, next);
        gui2.setItem(45, previous);
        gui2.setItem(53, next);
        gui3.setItem(45, previous);

        // Set item to be identified (World)
        ItemStack id = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta idMeta = id.getItemMeta();
        NamespacedKey keyId = new NamespacedKey(NoBuildPlus.getInstance(), "inventory");
        idMeta.getPersistentDataContainer().set(keyId, PersistentDataType.STRING, world);
        id.setItemMeta(idMeta);

        // Set item to be identified (Page)
        ItemStack page2 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta page2Meta = page2.getItemMeta();
        NamespacedKey keypage2 = new NamespacedKey(NoBuildPlus.getInstance(), "page");
        page2Meta.getPersistentDataContainer().set(keypage2, PersistentDataType.STRING, "2");
        page2.setItemMeta(page2Meta);

        // Set item to be identified (Page) For Page 3
        ItemStack page3 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta page3Meta = page3.getItemMeta();
        NamespacedKey keypage3 = new NamespacedKey(NoBuildPlus.getInstance(), "page");
        page3Meta.getPersistentDataContainer().set(keypage3, PersistentDataType.STRING, "3");
        page3.setItemMeta(page3Meta);

        gui.setItem(0, id);
        gui2.setItem(0, id);
        gui2.setItem(8, page2);
        gui3.setItem(0, id);
        gui3.setItem(8, page3);


        guis.putIfAbsent(world, gui);
        guis.putIfAbsent(world+"_page2", gui2);
        guis.putIfAbsent(world+"_page3", gui3);

        if (guis.get(world) != null) {
            guis.replace(world, gui);
        }

        if (guis.get(world + "_page2") != null) {
            guis.replace(world+"_page2", gui2);
        }

        if (guis.get(world + "_page3") != null) {
            guis.replace(world+"_page3", gui3);
        }

    }

    public void updateFlag(String world, String flag, boolean bool) {

        Inventory gui = guis.get(world);

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
                        guis.replace(world, gui);
                        return;
                    }
                }
            }

        }

        Inventory gui2 = guis.get(world+ "_page2");

        for (ItemStack item : gui2.getContents()) {
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
                        gui2.setItem(FlagsManager.getSlot(flag), item);
                        guis.replace(world+"_page2", gui2);
                        return;
                    }
                }
            }
        }

        Inventory gui3 = guis.get(world+ "_page3");

        for (ItemStack item : gui3.getContents()) {
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
                        gui3.setItem(FlagsManager.getSlot(flag), item);
                        guis.replace(world+"_page3", gui3);
                        return;
                    }
                }
            }
        }

    }

    public void removeWorld(String world) {

        guis.remove(world);
        guis.remove(world+"_page2");
        guis.remove(world+"_page3");

    }

    public void reloadGUIs() {

        guis.clear();
        GUIManager.instance.initialization();
    }

}
