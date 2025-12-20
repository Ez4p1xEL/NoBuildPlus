package p1xel.nobuildplus.listener.gui;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.storage.Locale;
import p1xel.nobuildplus.storage.Settings;
import p1xel.nobuildplus.storage.Worlds;

import java.util.List;
import java.util.stream.Collectors;

public class GUIWorldList extends GUIAbstract implements InventoryHolder {

    private Inventory inventory;
    private int page;
    private int size;
    private final NamespacedKey menu_id_key = new NamespacedKey("nobuildplus", "menu_id");

    public GUIWorldList(int page) {
        this.page = page;
        init();
    }

    @Override
    public void init() {

        boolean hasPreviousPage = false;
        boolean hasNextPage = false;
        if (page > 1) {
            hasPreviousPage = true;
        }

        List<String> enabledWorlds = Settings.getEnableWorldList();
        List<String> worlds = Bukkit.getWorlds().stream()
                .map(World::getName) // Convert to String
                .filter(worldName -> !enabledWorlds.contains(worldName)) // Check if existed
                .collect(Collectors.toList());

        if (worlds.size() >= page*28) {
            hasNextPage = true;
        }

        worlds = worlds.subList(28 * (page-1), Math.min((page) * 28, worlds.size()));

        size = (int) Math.max(1,Math.ceil((double)worlds.size() / 7));
        Inventory inventory = Bukkit.createInventory(this, (size+2)*9, Locale.getMessage("gui.list.title"));

        if (hasNextPage) {

            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Locale.getMessage("gui.list.items.next_page.display_name"));
            List<String> lore = Locale.yaml.getStringList("gui.list.items.next_page.lore").stream()
                    .map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList());
            meta.setLore(lore);
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.set(menu_id_key, PersistentDataType.STRING, "next_page");
            item.setItemMeta(meta);
            inventory.setItem(9 * (size+2) - 1, item);
        }

        if (hasPreviousPage) {

            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Locale.getMessage("gui.list.items.previous_page.display_name"));
            List<String> lore = Locale.yaml.getStringList("gui.list.items.previous_page.lore").stream()
                    .map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList());
            meta.setLore(lore);
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.set(menu_id_key, PersistentDataType.STRING, "previous_page");
            item.setItemMeta(meta);
            inventory.setItem(9 * (size+2) - 9, item);
        }

        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Locale.getMessage("gui.list.items.back_to_main.display_name"));
        List<String> lore = Locale.yaml.getStringList("gui.list.items.back_to_main.lore").stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList());
        meta.setLore(lore);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(menu_id_key, PersistentDataType.STRING, "back_to_main");
        item.setItemMeta(meta);
        inventory.setItem(9 * (size+2) - 5, item);

        this.inventory = inventory;
        update(worlds);

    }

    public void update(List<String> worlds) {
        int slot = 10; // starting slot
        int base = 17;
        for (String world : worlds) {
            if ((slot-base)%9 == 0) {
                slot+=2;
            }

            World bukkit_world = Bukkit.getWorld(world);
            if (bukkit_world == null) {
                continue;
            }
            String world_type;
            Material material;
            switch (bukkit_world.getEnvironment()) {
                case NETHER:
                    world_type = Locale.getMessage("nether");
                    material = Material.NETHERRACK;
                    break;
                case THE_END:
                    world_type = Locale.getMessage("the_end");
                    material = Material.END_STONE;
                    break;
                default:
                    world_type = Locale.getMessage("overworld");
                    material = Material.GRASS_BLOCK;
            }

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Locale.getMessage("gui.list.items.world.display_name").replaceAll("%world%", world).replaceAll("%type%", world_type));
            String finalWorld_type = world_type;
            List<String> lore_list = Locale.yaml.getStringList("gui.list.items.world.lore").stream()
                    .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                    .map(line -> line.replaceAll("%world%",world)
                            .replaceAll("%type%", finalWorld_type))
                    .collect(Collectors.toList());
            meta.setLore(lore_list);
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.set(menu_id_key, PersistentDataType.STRING, "world:" + world);
            item.setItemMeta(meta);
            inventory.setItem(slot, item);
            slot++;
            // closing slot
            if (slot >= 9 * (size+2) - 7) {
                break;
            }

        }

        for (int i = 0; i < 9*(size+2); i++) {
            if (inventory.getItem(i) == null) {
                ItemStack item = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(" ");
                item.setItemMeta(meta);
                inventory.setItem(i, item);
            }
        }
    }

    @Override
    public boolean check(Player player, String name, ClickType type) { return false; }

    @Override
    public boolean check(Player player, String name) {

        switch (name) {
            case "back_to_main": {
                player.openInventory(new GUIMain(1).getInventory());
                return true;
            }
            case "previous_page": {
                player.openInventory(new GUIWorldList(Math.max(1, page-1)).getInventory());
                return true;
            }
            case "next_page": {
                player.openInventory(new GUIWorldList(page+1).getInventory());
                return true;
            }
        }

        if (name.startsWith("world:")) {
            String world = name.split(":")[1];
            if (Bukkit.getWorld(world) == null) {
                player.sendMessage(Locale.getMessage("cant-find-world"));
                player.openInventory(new GUIWorldList(1).getInventory());
                return true;
            }
            if (!Settings.getEnableWorldList().contains(world)) {

                Worlds.createWorld(world);
                player.sendMessage(Locale.getMessage("add-success").replaceAll("%world%", world));
                player.openInventory(new GUIWorldList(1).getInventory());

            } else {
                player.sendMessage(Locale.getMessage("already-exists"));
                player.openInventory(new GUIWorldList(1).getInventory());
            }

        }

        return true;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

}
