package p1xel.nobuildplus.listener.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import p1xel.nobuildplus.storage.Locale;
import p1xel.nobuildplus.storage.MenuConfig;

import java.util.List;
import java.util.stream.Collectors;

public abstract class GUIAbstract {

    public abstract void init();
    public abstract boolean check(Player player, String name);
    public abstract boolean check(Player player, String name, ClickType clickType);
    public abstract Inventory getInventory();
    public abstract int getPage();

    protected final NamespacedKey menu_id_key = new NamespacedKey("nobuildplus", "menu_id");
    protected Inventory setNextPage(Inventory inventory, String name, int rows) {
        Material material = Material.matchMaterial(MenuConfig.GLOBAL_NEXT_PAGE);
        ItemStack item = new ItemStack(material != null ? material : Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Locale.getMessage("gui." + name + ".items.next_page.display_name"));
        List<String> lore = Locale.yaml.getStringList("gui." + name + ".items.next_page.lore").stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList());
        meta.setLore(lore);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(menu_id_key, PersistentDataType.STRING, "next_page");
        item.setItemMeta(meta);
        inventory.setItem(9 * (rows+2) - 4, item);
        return inventory;
    }

    protected Inventory setPreviousPage(Inventory inventory, String name, int rows) {
        Material material = Material.matchMaterial(MenuConfig.GLOBAL_PREVIOUS_PAGE);
        ItemStack item = new ItemStack(material != null ? material : Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Locale.getMessage("gui."+ name +".items.previous_page.display_name"));
        List<String> lore = Locale.yaml.getStringList("gui."+ name +".items.previous_page.lore").stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList());
        meta.setLore(lore);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(menu_id_key, PersistentDataType.STRING, "previous_page");
        item.setItemMeta(meta);
        inventory.setItem(9 * (rows+2) - 6, item);
        return inventory;
    }

    protected Inventory setBackTo(Inventory inventory, String name, int rows) {
        Material material = Material.matchMaterial(MenuConfig.GLOBAL_BACK_TO_MAIN);
        ItemStack item = new ItemStack(material != null ? material : Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Locale.getMessage("gui." + name + ".items.back_to_main.display_name"));
        List<String> lore = Locale.yaml.getStringList("gui."+name+".items.back_to_main.lore").stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList());
        meta.setLore(lore);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(menu_id_key, PersistentDataType.STRING, "back_to_main");
        item.setItemMeta(meta);
        inventory.setItem(9 * (rows+2) - 9, item);
        return inventory;
    }

    protected Inventory setTypeButton(Inventory inventory, String name, GUIType type) {
        Material flag = Material.matchMaterial(MenuConfig.WORLD_SETTING_TYPE_BUTTON_FLAG);
        Material gamerule = Material.matchMaterial(MenuConfig.WORLD_SETTING_TYPE_BUTTON_GAMERULE);
        Material material = type == GUIType.FLAG ? (flag != null ? flag : Material.PAPER) : (gamerule != null ? gamerule : Material.PAPER);
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Locale.getMessage("gui." + name + ".items.type.display_name").replace("%type%", Locale.getMessage(type.getName().toLowerCase() + ".name")));
        List<String> lore = Locale.yaml.getStringList("gui."+name+".items.type.lore").stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line)
                        .replace("%type%", Locale.getMessage(type.getName().toLowerCase() + ".name")))
                .collect(Collectors.toList());
        meta.setLore(lore);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(menu_id_key, PersistentDataType.STRING, "type");
        item.setItemMeta(meta);
        inventory.setItem(4, item);
        return inventory;
    }

    protected ItemStack getWikiButton() {
        Material wiki = Material.matchMaterial(MenuConfig.BUTTON_WIKI);
        Material material = wiki != null ? wiki : Material.BOOK;
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Locale.getMessage("gui.buttons.wiki.display_name"));
        List<String> lore = Locale.yaml.getStringList("gui.buttons.wiki.lore").stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                .collect(Collectors.toList());
        meta.setLore(lore);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(menu_id_key, PersistentDataType.STRING, "wiki");
        item.setItemMeta(meta);
        return item;
    }

}
