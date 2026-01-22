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
import p1xel.nobuildplus.Flag;
import p1xel.nobuildplus.FlagRegistry;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.gamerule.GameRuleRegistry;
import p1xel.nobuildplus.storage.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GUIDefaultTemplate extends GUIAbstract implements InventoryHolder {

    private Inventory inventory;
    private int page;
    private GUIType type;

    public GUIDefaultTemplate(int page, GUIType type) {
        this.page = page;
        this.type = type;
        init();
    }

    @Override
    public void init() {

        boolean hasPreviousPage = page > 1;
        boolean hasNextPage;

        List<String> list;

        if (type == GUIType.GAMERULE) {
            list = GameRuleRegistry.getRegisteredGameRules();
            hasNextPage = list.size() > page * 28;

        } else {
            list = FlagsManager.getFlags();
            hasNextPage = list.size() > page * 28;
        }

        // 获取符合当前页面的数量
        list = list.subList(28 * (page - 1), Math.min((page) * 28, list.size()));

        Inventory inventory = Bukkit.createInventory(this, 54, Locale.getMessage("gui.edit-default.title"));

        if (hasNextPage) {
            inventory = setNextPage(inventory, "edit-default", 4);
        }

        if (hasPreviousPage) {
            inventory = setPreviousPage(inventory, "edit-default", 4);
        }

        inventory = setBackTo(inventory, "edit-default", 4);
        inventory = setTypeButton(inventory, "edit-default", type);

        this.inventory = inventory;

        setItem(Material.COMMAND_BLOCK, "edit-permission", 2);
        setItem(Material.FEATHER, "edit-deny-message", 6);

        update(list);

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

    public void update(List<String> list) {
        int slot = 10; // starting slot
        int base = 17;

        if (type == GUIType.FLAG) {

            for (String element : list) {
                if ((slot - base) % 9 == 0) {
                    slot += 2;
                }

                Flag flag = FlagRegistry.matchFlag(element);
                Material material = Material.matchMaterial(flag.getShowItem());
                if (material == null) {
                    material = Material.PAPER;
                }
                String flagName = flag.getName();
                boolean bool = Settings.getDefaultFlag(flagName);
                ItemStack item = new ItemStack(material);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(Locale.getMessage("gui.edit-default.items.flag.display_name").replaceAll("%flag%", flagName).replaceAll("%bool%", Locale.getMessage(String.valueOf(bool))));
                List<String> lore_list = Locale.yaml.getStringList("gui.edit-default.items.flag.lore").stream()
                        .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                        .map(line -> line.replaceAll("%flag%", flagName)
                                .replaceAll("%bool%", Locale.getMessage(String.valueOf(bool)))
                                .replaceAll("%description%", flag.getDescription(Config.getLanguage())))
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
        }

        if (type == GUIType.GAMERULE) {
            for (String element : list) {
                if ((slot - base) % 9 == 0) {
                    slot += 2;
                }

                Material material = Material.matchMaterial(RuleSetting.getMaterialString(element));
                if (material == null) {
                    material = Material.PAPER;
                }
                Object value = Settings.getDefaultGameRule(element);
                ItemStack item = new ItemStack(material);
                ItemMeta meta = item.getItemMeta();
                String gameruleBoolean = value instanceof Boolean ? Locale.getMessage("gamerule_"+(boolean) value) : "";
                String gameruleInteger = value instanceof Integer ? Locale.getMessage("value").replace("%value%", String.valueOf((int) value)) : "";
                meta.setDisplayName(Locale.getMessage("gui.edit-default.items.gamerule.display_name").replaceAll("%gamerule%", element).replace("%bool%", gameruleBoolean).replace("%int%", gameruleInteger));
                List<String> lore_list = Locale.yaml.getStringList("gui.edit-default.items.gamerule.lore").stream()
                        .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                        .map(line -> line.replace("%gamerule%", element)
                                .replace("%bool%", gameruleBoolean)
                                .replace("%int%", gameruleInteger)
                                .replace("%description%", Locale.getMessage("gamerule.description." + element)))
                        .collect(Collectors.toList());
                meta.setLore(lore_list);
                PersistentDataContainer container = meta.getPersistentDataContainer();
                container.set(menu_id_key, PersistentDataType.STRING, "gamerule:" + element);
                item.setItemMeta(meta);
                inventory.setItem(slot, item);
                slot++;
                // closing slot
                if (slot >= 44) {
                    break;
                }
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
        if (type == GUIType.FLAG) {
            ItemStack item = inventory.getItem(slot);
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();
            String flagName = container.get(menu_id_key, PersistentDataType.STRING).split(":")[1];
            Flag flag = FlagRegistry.matchFlag(flagName);
            boolean bool = Settings.getDefaultFlag(flagName);
            meta.setDisplayName(Locale.getMessage("gui.edit-default.items.flag.display_name").replaceAll("%flag%", flagName).replaceAll("%bool%", Locale.getMessage(String.valueOf(bool))));
            List<String> lore_list = Locale.yaml.getStringList("gui.edit-default.items.flag.lore").stream()
                    .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                    .map(line -> line.replaceAll("%flag%", flagName)
                            .replaceAll("%bool%", Locale.getMessage(String.valueOf(bool)))
                            .replaceAll("%description%", flag.getDescription(Config.getLanguage())))
                    .collect(Collectors.toList());
            meta.setLore(lore_list);
            item.setItemMeta(meta);
            inventory.setItem(slot, item);
            return;
        }

        ItemStack item = inventory.getItem(slot);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        String gameruleName = container.get(menu_id_key, PersistentDataType.STRING).split(":")[1];
        Object value = Settings.getDefaultGameRule(gameruleName);
        String gameruleBoolean = value instanceof Boolean ? Locale.getMessage("gamerule_"+(boolean) value) : "";
        String gameruleInteger = value instanceof Integer ? Locale.getMessage("value").replace("%value%", String.valueOf((int) value)) : "";
        meta.setDisplayName(Locale.getMessage("gui.edit-default.items.gamerule.display_name").replaceAll("%gamerule%", gameruleName).replace("%bool%", gameruleBoolean).replace("%int%", gameruleInteger));
        List<String> lore_list = Locale.yaml.getStringList("gui.edit-default.items.gamerule.lore").stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                .map(line -> line.replace("%gamerule%", gameruleName)
                        .replace("%bool%", gameruleBoolean)
                        .replace("%int%", gameruleInteger)
                        .replace("%description%", Locale.getMessage("gamerule.description." + gameruleName)))
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
                player.playSound(player, Sound.BLOCK_CHEST_CLOSE, 0.5f, 0.5f);
                return true;
            }
            case "previous_page": {
                player.openInventory(new GUIDefaultTemplate(Math.max(1, page-1), type).getInventory());
                player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 0.5f, 0.5f);
                return true;
            }
            case "next_page": {
                player.openInventory(new GUIDefaultTemplate(page+1, type).getInventory());
                player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 0.5f, 0.5f);
                return true;
            }

            case "edit-permission": {
                NoBuildPlus.getTextEditMode().setPlayerAction(player, "edit-default-permission", this);
                player.closeInventory();
                player.sendMessage(Locale.getMessage("join-mode").replaceAll("%cancel%", NoBuildPlus.getTextEditMode().cancelWord));
                player.playSound(player, Sound.BLOCK_ANVIL_PLACE, 0.5f, 0.5f);
                return true;
            }

            case "edit-deny-message": {
                NoBuildPlus.getTextEditMode().setPlayerAction(player, "edit-default-deny-message", this);
                player.closeInventory();
                player.sendMessage(Locale.getMessage("join-mode").replaceAll("%cancel%", NoBuildPlus.getTextEditMode().cancelWord));
                player.playSound(player, Sound.BLOCK_ANVIL_PLACE, 0.5f, 0.5f);
                return true;
            }

            case "type": {
                GUIType new_type = type == GUIType.FLAG ? GUIType.GAMERULE : GUIType.FLAG;
                player.openInventory(new GUIDefaultTemplate(1, new_type).getInventory());
                player.playSound(player, Sound.UI_BUTTON_CLICK, 0.5f, 0.5f);
                return true;
            }
        }

        if (name.startsWith("flag:")) {
            String flag = name.split(":")[1];
            boolean bool = Settings.getDefaultFlag(flag);

            Settings.setDefaultFlag(flag, !bool);
            updateSlot(slot);
            player.sendMessage(Locale.getMessage("default-flag-set-success").replaceAll("%flag%", flag).replaceAll("%boolean%", String.valueOf(!bool)));
            player.playSound(player, Sound.ENTITY_VILLAGER_YES, 0.5f, 0.5f);
            return true;

        }

        if (name.startsWith("gamerule:")) {
            String gameruleName = name.split(":")[1];
            Object value = Settings.getDefaultGameRule(gameruleName);
            if (value instanceof Boolean) {
                boolean bool = (boolean) value;
                Settings.setDefaultGameRule(gameruleName, !bool);
                updateSlot(slot);
                player.sendMessage(Locale.getMessage("default-gamerule-set-success").replaceAll("%gamerule%", gameruleName).replaceAll("%value%", String.valueOf(!bool)));
                player.playSound(player, Sound.ENTITY_VILLAGER_YES, 0.5f, 0.5f);
                Logger.debug("Setting default template gamerule: " + gameruleName);
                //GameRuleRegistry.setWorldGameRule("world", gameruleName, !bool); // for test
                return true;
            }

            NoBuildPlus.getTextEditMode().setPlayerAction(player, "edit-default-gamerule:" + gameruleName, this);
            player.closeInventory();
            player.sendMessage(Locale.getMessage("join-mode").replaceAll("%cancel%", NoBuildPlus.getTextEditMode().cancelWord));
            player.playSound(player, Sound.BLOCK_ANVIL_PLACE, 0.5f, 0.5f);
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
