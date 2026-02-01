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
import p1xel.nobuildplus.storage.Locale;
import p1xel.nobuildplus.world.WorldManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GUIWorldList extends GUIAbstract implements InventoryHolder {

    private Inventory inventory;
    private int page;
    private int size;

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

        List<String> enabledWorlds = WorldManager.getWorldsInName();

        List<String> worlds = new ArrayList<>();
        try {
            // 1.17+
            Class<?> bukkit = Class.forName("org.bukkit.generator.WorldInfo");
            List<World> bukkitWorlds = Bukkit.getWorlds();
            worlds = bukkitWorlds.stream()
                .map(World::getName) // Convert to String
                .filter(worldName -> !enabledWorlds.contains(worldName)) // Check if existed
                .collect(Collectors.toList());
        } catch (Exception e) {
            // 1.16-
            try {
                Class<?> bukkit = Class.forName("org.bukkit.Bukkit");
                List<Object> worldsList = (List<Object>) bukkit.getMethod("getWorlds").invoke(null);
                for (Object world : worldsList) {
                    Class<?> worldClass = Class.forName("org.bukkit.World");
                    String worldName = (String) worldClass.getMethod("getName").invoke(world);
                    if (enabledWorlds.contains(worldName)) {
                        continue;
                    }
                    worlds.add(worldName);
                }
            } catch (Exception ignored) {
            }
        }

        if (worlds.size() >= page*28) {
            hasNextPage = true;
        }

        worlds = worlds.subList(28 * (page-1), Math.min((page) * 28, worlds.size()));

        size = (int) Math.max(1,Math.ceil((double)worlds.size() / 7));
        Inventory inventory = Bukkit.createInventory(this, (size+2)*9, Locale.getMessage("gui.list.title"));

        if (hasNextPage) {
            inventory = setNextPage(inventory, "list", size);
        }

        if (hasPreviousPage) {
            inventory = setPreviousPage(inventory, "list", size);
        }

        inventory = setBackTo(inventory, "list", size);

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
    public boolean check(Player player, String name, ClickType type) {
        switch (name) {
            case "back_to_main": {
                player.openInventory(new GUIMain(1).getInventory());
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 0.5f, 0.5f);
                return true;
            }
            case "previous_page": {
                player.openInventory(new GUIWorldList(Math.max(1, page-1)).getInventory());
                player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 0.5f, 0.5f);
                return true;
            }
            case "next_page": {
                player.openInventory(new GUIWorldList(page+1).getInventory());
                player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 0.5f, 0.5f);
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

            if (type == ClickType.RIGHT) {
                player.openInventory(new GUIWorld(world, 1, GUIType.GAMERULE).getInventory());
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);
                return true;
            }
            if (WorldManager.getWorld(world) == null) {

                //Worlds.createWorld(world);
                WorldManager.enableWorld(world);
                player.sendMessage(Locale.getMessage("add-success").replaceAll("%world%", world));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 0.5f, 0.5f);
                player.openInventory(new GUIWorldList(1).getInventory());

            } else {
                player.sendMessage(Locale.getMessage("already-exists"));
                player.openInventory(new GUIWorldList(1).getInventory());
            }

        }
        return true;

    }

    @Override
    public boolean check(Player player, String name) {

        return false;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public int getPage() {
        return page;
    }

}
