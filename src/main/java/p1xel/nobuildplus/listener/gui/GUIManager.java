package p1xel.nobuildplus.listener.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.storage.FlagsManager;
import p1xel.nobuildplus.storage.Locale;
import p1xel.nobuildplus.storage.Worlds;

import java.util.*;

public class GUIManager {

    public static GUIManager instance = new GUIManager();
    private HashMap<String, Inventory> worlds = new HashMap<>();

    public Inventory getGUI(String world) {
        return worlds.get(world);
    }

    public void initialization() {

        for (String world : Worlds.yaml.getKeys(false)) {
            createInv(world);
        }

//        List<String> enabledWorlds = Settings.getEnableWorldList();
//        int main_page = (int) Math.max(1,Math.ceil((double) enabledWorlds.size() / 28));
//        for (int i = 1; i <= main_page;i++) {
//            GUIMain main = new GUIMain(i);
//            map_main.put(i, main);
//        }
//
//        List<String> disabledWorlds = Bukkit.getWorlds().stream()
//                .map(World::getName) // Convert to String
//                .filter(worldName -> !enabledWorlds.contains(worldName)) // Check if existed
//                .collect(Collectors.toList());
//        int list_page = (int) Math.max(1, Math.ceil((double) disabledWorlds.size() / 28));
//        for (int i = 1; i <= list_page;i++) {
//            GUIWorldList worldList = new GUIWorldList(i);
//            map_list.put(i, worldList);
//        }

        Bukkit.getLogger().info("Initialization completed: " + worlds.toString());

    }

    ItemStack getIconItem(ItemStack item, String locale_path, String key, String data) {

        ItemMeta meta = item.getItemMeta();
        NamespacedKey n_key = new NamespacedKey(NoBuildPlus.getInstance(), key);
        meta.getPersistentDataContainer().set(n_key, PersistentDataType.STRING, data);
        meta.setDisplayName(Locale.getMessage("gui.world."+locale_path));
        item.setItemMeta(meta);
        return item;

    }

    public void createInv(String world) {

        List<String> flags = FlagsManager.getFlags();
        int max_page = FlagsManager.getMaxPage(flags);

        int a = 1;

        for (int i = 1; i <= max_page;i++) {


            Inventory gui = Bukkit.createInventory(null,54,Locale.getMessage("gui.world.title") + " " + world);

            if (i != max_page) {
                // "Next" Icon
                gui.setItem(53, getIconItem(new ItemStack(Material.PAPER),"page_next", "function", "next"));
            }

            if (i != 1) {
                // "Previous" Icon
                gui.setItem(45, getIconItem(new ItemStack(Material.PAPER),"page_previous", "function", "previous"));
            }

            gui.setItem(49, getIconItem(new ItemStack(Material.ARROW), "back_to_main", "function", "back_to_main"));

            // Set Icons as ids
            gui.setItem(0, getIconItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE), "world", "inventory", world));
            gui.setItem(8, getIconItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE), "page", "page", String.valueOf(i)));

            for (int c =0;c < 28; c++) {

                String flag = flags.get(a-1);
                Flags f = Flags.matchFlag(flag);
                int slot = f.getSlot();

                // Each page
                Material mat = Material.valueOf(f.getShowItem());
                ItemStack item = new ItemStack(mat);
                ItemMeta meta = item.getItemMeta();

                boolean bool = Worlds.getFlag(world, flag);

                assert meta != null;
                meta.setDisplayName(Locale.getMessage("gui.world.display_name").replaceAll("%flag%", flag).replaceAll("%bool%", Locale.getMessage(String.valueOf(bool))));
                List<String> lores = new ArrayList<>();
                for (String l : Locale.yaml.getStringList("gui.world.lore")) {

                    l = Locale.translate(l);
                    l = l.replaceAll("%flag%", flag);
                    l = l.replaceAll("%bool%", String.valueOf(bool));
                    l = l.replaceAll("%description%", Locale.getMessage("gui.world.description." + flag));
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


            worlds.put(world+"_page" + i, gui);


        }

    }

    public void updateFlag(String world, String flag, boolean bool) {

        int max_page = FlagsManager.getMaxPage(FlagsManager.getFlags());

        for (int i = 1;i<=max_page;i++) {

            Inventory gui = worlds.get(world+"_page"+i);

            for (ItemStack item : gui.getContents()) {

                if (item != null) {
                    ItemMeta meta = item.getItemMeta();
                    if (meta != null) {
                        NamespacedKey key = new NamespacedKey(NoBuildPlus.getInstance(), "flag");
                        if (meta.getPersistentDataContainer().has(key, PersistentDataType.STRING) && Objects.equals(meta.getPersistentDataContainer().get(key, PersistentDataType.STRING), flag)) {
                            meta.setDisplayName(Locale.getMessage("gui.world.display_name").replaceAll("%flag%", flag).replaceAll("%bool%", Locale.getMessage(String.valueOf(bool))));
                            List<String> lores = new ArrayList<>();
                            for (String l : Locale.yaml.getStringList("gui.world.lore")) {

                                l = Locale.translate(l);
                                l = l.replaceAll("%flag%", flag);
                                l = l.replaceAll("%bool%", String.valueOf(bool));
                                l = l.replaceAll("%description%", Locale.getMessage("gui.world.description." + flag));
                                lores.add(l);

                            }
                            meta.setLore(lores);
                            item.setItemMeta(meta);
                            gui.setItem(FlagsManager.getSlot(flag), item);
                            worlds.replace(world+"_page"+i, gui);
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
            worlds.remove(world+"_page" +i);
        }

    }

    public void reloadGUIs() {

        worlds.clear();
        GUIManager.instance.initialization();
    }

}
