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
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.storage.FlagsManager;
import p1xel.nobuildplus.storage.Locale;
import p1xel.nobuildplus.storage.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GUIDefaultTemplate extends GUIAbstract implements InventoryHolder {

    private Inventory inventory;
    private int page;
    private final NamespacedKey menu_id_key = new NamespacedKey("nobuildplus", "menu_id");

    public GUIDefaultTemplate(int page) {
        this.page = page;
        init();
    }

    @Override
    public void init() {

        boolean hasPreviousPage = page > 1;
        boolean hasNextPage;

        List<String> flags = FlagsManager.getFlags();
        hasNextPage = flags.size() > page * 28;
        flags = flags.subList(28 * (page-1), Math.min((page) * 28, flags.size()));

        Inventory inventory = Bukkit.createInventory(this, 54, Locale.getMessage("gui.edit-default.title"));

        if (hasNextPage) {

            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Locale.getMessage("gui.edit-default.items.next_page.display_name"));
            List<String> lore = Locale.yaml.getStringList("gui.edit-default.items.next_page.lore").stream()
                    .map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList());
            meta.setLore(lore);
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.set(menu_id_key, PersistentDataType.STRING, "next_page");
            item.setItemMeta(meta);
            inventory.setItem(53, item);
        }

        if (hasPreviousPage) {

            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Locale.getMessage("gui.edit-default.items.previous_page.display_name"));
            List<String> lore = Locale.yaml.getStringList("gui.edit-default.items.previous_page.lore").stream()
                    .map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList());
            meta.setLore(lore);
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.set(menu_id_key, PersistentDataType.STRING, "previous_page");
            item.setItemMeta(meta);
            inventory.setItem(45, item);
        }

        this.inventory = inventory;

        setItem(Material.ARROW, "back_to_main", 49);
        setItem(Material.IRON_CHESTPLATE, "edit-permission", 48);
        setItem(Material.FEATHER, "edit-deny-message", 50);

        update(flags);

    }

    private void setItem(Material material, String menu_id, int slot) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        String display_name = null;
        List<String> lore = new ArrayList<>();

        switch (menu_id) {

            case "back_to_main": {

                display_name = Locale.getMessage("gui.edit-default.items." + menu_id + ".display_name");
                lore = Locale.yaml.getStringList("gui.edit-default.items." + menu_id + ".lore").stream()
                        .map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList());
                break;
            }

            case "edit-permission": {
                display_name = Locale.getMessage("gui.edit-default.items." + menu_id + ".display_name");
                lore = Locale.yaml.getStringList("gui.edit-default.items." + menu_id + ".lore").stream()
                        .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                        .map(line -> line.replaceAll("%permission%", Settings.getPermission())).collect(Collectors.toList());
                break;
            }

            case "edit-deny-message": {
                display_name = Locale.getMessage("gui.edit-default.items." + menu_id + ".display_name");
                lore = Locale.yaml.getStringList("gui.edit-default.items." + menu_id + ".lore").stream()
                        .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                        .map(line -> line.replaceAll("%message%", Settings.getDenyMessageString())).collect(Collectors.toList());
                break;
            }
        }

        meta.setDisplayName(display_name);
        meta.setLore(lore);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(menu_id_key, PersistentDataType.STRING, menu_id);
        item.setItemMeta(meta);
        inventory.setItem(slot, item);
    }

    public void update(List<String> flags) {
        int slot = 10; // starting slot
        int base = 17;
        for (String f : flags) {
            if ((slot-base)%9 == 0) {
                slot+=2;
            }

            Flags flag = Flags.matchFlag(f);
            Material material = Material.valueOf(flag.getShowItem());
            String flagName = flag.getName();
            boolean bool = Settings.getDefaultFlag(flagName);
            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Locale.getMessage("gui.edit-default.items.flag.display_name").replaceAll("%flag%", flagName).replaceAll("%bool%", Locale.getMessage(String.valueOf(bool))));
            List<String> lore_list = Locale.yaml.getStringList("gui.edit-default.items.flag.lore").stream()
                    .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                    .map(line -> line.replaceAll("%flag%",flagName)
                            .replaceAll("%bool%", Locale.getMessage(String.valueOf(bool)))
                            .replaceAll("%description%", Locale.getMessage("flag.description." + flagName)))
                    .collect(Collectors.toList());
            meta.setLore(lore_list);
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.set(menu_id_key, PersistentDataType.STRING, "flag:" + flagName);
            item.setItemMeta(meta);
            inventory.setItem(slot, item);
            slot++;
            // closing slot
            if (slot >= 44) {
                break;
            }

        }

        for (int i = 0; i < 54; i++) {
            if (inventory.getItem(i) == null) {
                ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(" ");
                item.setItemMeta(meta);
                inventory.setItem(i, item);
            }
        }
    }

    public void updateSlot(int slot) {
        ItemStack item = inventory.getItem(slot);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        String flagName = container.get(menu_id_key, PersistentDataType.STRING).split(":")[1];
        boolean bool = Settings.getDefaultFlag(flagName);
        meta.setDisplayName(Locale.getMessage("gui.edit-default.items.flag.display_name").replaceAll("%flag%", flagName).replaceAll("%bool%", Locale.getMessage(String.valueOf(bool))));
        List<String> lore_list = Locale.yaml.getStringList("gui.edit-default.items.flag.lore").stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                .map(line -> line.replaceAll("%flag%",flagName)
                        .replaceAll("%bool%", Locale.getMessage(String.valueOf(bool)))
                        .replaceAll("%description%", Locale.getMessage("flag.description." + flagName)))
                .collect(Collectors.toList());
        meta.setLore(lore_list);
        item.setItemMeta(meta);
        inventory.setItem(slot, item);
    }

    @Override
    public boolean check(Player player, String name) {
        return false;
    }

    public boolean check(Player player, String name, int slot) {
        switch (name) {
            case "back_to_main": {
                player.openInventory(new GUIMain(1).getInventory());
                return true;
            }
            case "previous_page": {
                player.openInventory(new GUIDefaultTemplate(Math.max(1, page-1)).getInventory());
                return true;
            }
            case "next_page": {
                player.openInventory(new GUIDefaultTemplate(page+1).getInventory());
                return true;
            }

            case "edit-permission": {
                NoBuildPlus.getTextEditMode().setPlayerAction(player, "edit-default-permission");
                player.closeInventory();
                player.sendMessage(Locale.getMessage("join-mode").replaceAll("%cancel%", NoBuildPlus.getTextEditMode().cancelWord));
                return true;
            }

            case "edit-deny-message": {
                NoBuildPlus.getTextEditMode().setPlayerAction(player, "edit-default-deny-message");
                player.closeInventory();
                player.sendMessage(Locale.getMessage("join-mode").replaceAll("%cancel%", NoBuildPlus.getTextEditMode().cancelWord));
                return true;
            }
        }

        if (name.startsWith("flag:")) {
            String flag = name.split(":")[1];
            boolean bool = Settings.getDefaultFlag(flag);

            Settings.setDefaultFlag(flag, !bool);
            updateSlot(slot);
            player.sendMessage(Locale.getMessage("default-flag-set-success").replaceAll("%flag%", flag).replaceAll("%boolean%", String.valueOf(!bool)));
            return true;

        }

        return false;
    }

    @Override
    public boolean check(Player player, String name, ClickType clickType) {
        return false;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
