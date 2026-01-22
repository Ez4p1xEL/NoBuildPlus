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
import p1xel.nobuildplus.storage.MenuConfig;
import p1xel.nobuildplus.world.WorldManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GUIMain extends GUIAbstract implements InventoryHolder {

    private Inventory inventory;
    private int page;
    private int size;

    public GUIMain(int page) {
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

        List<String> worlds = new ArrayList<>(WorldManager.getWorldsInName());

        if (worlds.size() >= page*28) {
            hasNextPage = true;
        }
        worlds = worlds.subList(28 * (page-1), Math.min((page) * 28, worlds.size()));

        size = (int) Math.max(1,Math.ceil((double)worlds.size() / 7));
        Inventory inventory = Bukkit.createInventory(this, (size+2)*9, Locale.getMessage("gui.main.title"));

        if (hasNextPage) {
            inventory = setNextPage(inventory, "main", size);
        }

        if (hasPreviousPage) {
            inventory = setPreviousPage(inventory, "main", size);
        }

        Material closeButton = Material.matchMaterial(MenuConfig.MAIN_CLOSE);
        Material addButton = Material.matchMaterial(MenuConfig.MAIN_ADD_WORLD);
        Material templateButton = Material.matchMaterial(MenuConfig.MAIN_DEFAULT_TEMPLATE_ENTRY);
        inventory = setItem(inventory, "close", closeButton != null ? closeButton : Material.BARRIER, 9*(size+2)-5);
        inventory = setItem(inventory, "add", addButton != null ? addButton : Material.LIME_STAINED_GLASS_PANE, 4);
        inventory = setItem(inventory, "edit-default", templateButton != null ? templateButton : Material.BOOK, 3);

        this.inventory = inventory;
        update(worlds);

    }

    Inventory setItem(Inventory inventory, String item_name, Material material, int slot) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Locale.getMessage("gui.main.items."+ item_name+".display_name"));
        List<String> lore = Locale.yaml.getStringList("gui.main.items."+ item_name+".lore").stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList());
        itemMeta.setLore(lore);
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(menu_id_key, PersistentDataType.STRING, item_name);
        item.setItemMeta(itemMeta);
        inventory.setItem(slot, item);
        return inventory;
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
            meta.setDisplayName(Locale.getMessage("gui.main.items.world.display_name").replaceAll("%world%", world).replaceAll("%type%", world_type));
            String finalWorld_type = world_type;
            List<String> lore_list = Locale.yaml.getStringList("gui.main.items.world.lore").stream()
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
                Material empty_slot = Material.matchMaterial(MenuConfig.GLOBAL_EMPTY_SLOT);
                ItemStack item = new ItemStack(empty_slot != null ? empty_slot : Material.BLACK_STAINED_GLASS_PANE);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(" ");
                item.setItemMeta(meta);
                inventory.setItem(i, item);
            }
        }
    }

    @Override
    public boolean check(Player player, String name) {return false;}

    @Override
    public boolean check(Player player, String name, ClickType clickType) {

        switch (name) {
            case "close": {
                player.closeInventory();
                player.playSound(player, Sound.BLOCK_CHEST_CLOSE, 0.5f, 0.5f);
                return true;
            }
            case "previous_page": {
                player.openInventory(new GUIMain(Math.min(1, page-1)).getInventory());
                player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 0.5f, 0.5f);
                return true;
            }
            case "next_page": {
                player.openInventory(new GUIMain(page+1).getInventory());
                player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 0.5f, 0.5f);
                return true;
            }
            case "add": {
                player.openInventory(new GUIWorldList(1).getInventory());
                player.playSound(player, Sound.BLOCK_SLIME_BLOCK_PLACE, 0.5f, 0.5f);
                return true;
            }
            case "edit-default": {
                player.openInventory(new GUIDefaultTemplate(1, GUIType.FLAG).getInventory());
                player.playSound(player, Sound.ENTITY_ARMOR_STAND_BREAK, 0.45f, 0.45f);
                return true;
            }
        }

        if (name.startsWith("world:")) {
            String world = name.split(":")[1];
            if (clickType == ClickType.LEFT) {
                if (WorldManager.getWorld(world) == null) {
                    player.sendMessage(Locale.getMessage("cant-find-world"));
                    player.openInventory(new GUIMain(1).getInventory());
                    player.playSound(player, Sound.ENTITY_VILLAGER_NO, 0.5f, 0.5f);
                    return true;
                }
                player.openInventory(new GUIWorld(world, 1, GUIType.FLAG).getInventory());
                player.playSound(player, Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);
            } else if (clickType == ClickType.RIGHT) {
                if (WorldManager.getWorld(world) == null) {
                    player.sendMessage(Locale.getMessage("cant-find-world"));
                    player.openInventory(new GUIMain(1).getInventory());
                    player.playSound(player, Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);
                    return true;
                }
                //Worlds.removeWorld(world);
                WorldManager.removeWorld(world);
                player.sendMessage(Locale.getMessage("remove-success").replaceAll("%world%",world));
                player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 0.5f);
                player.openInventory(new GUIMain(1).getInventory());
            }
            return true;

        }

        return true;
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
