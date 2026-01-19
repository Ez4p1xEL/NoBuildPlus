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

public class GUIWorld extends GUIAbstract implements InventoryHolder {

    private Inventory inventory;
    private String worldName;
    private int page;
    private final GUIType type;

    public GUIWorld(String worldName, int page, GUIType type) {
        this.worldName = worldName;
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

        list = list.subList(28 * (page - 1), Math.min((page) * 28, list.size()));

        Inventory inventory = Bukkit.createInventory(this, 54, Locale.getMessage("gui.world.title").replaceAll("%world%", worldName));

        if (hasNextPage) {
            inventory = setNextPage(inventory, "world", 4);
        }

        if (hasPreviousPage) {
            inventory = setPreviousPage(inventory, "world", 4);
        }

        inventory = setBackTo(inventory, "world", 4);
        inventory = setTypeButton(inventory, "world", type);

        this.inventory = inventory;

        setItem(Material.IRON_CHESTPLATE, "edit-permission", 48);
        setItem(Material.FEATHER, "edit-deny-message", 50);

        update(list);

    }

    private void setItem(Material material, String menu_id, int slot) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        String display_name = null;
        List<String> lore = new ArrayList<>();

        switch (menu_id) {

            case "back_to_main": {

                display_name = Locale.getMessage("gui.world.items." + menu_id + ".display_name");
                lore = Locale.yaml.getStringList("gui.world.items." + menu_id + ".lore").stream()
                        .map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList());
                break;
            }

            case "edit-permission": {
                display_name = Locale.getMessage("gui.world.items." + menu_id + ".display_name");
                lore = Locale.yaml.getStringList("gui.world.items." + menu_id + ".lore").stream()
                        .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                        .map(line -> line.replaceAll("%permission%", Worlds.getPermission(worldName))).collect(Collectors.toList());
                break;
            }

            case "edit-deny-message": {
                String message = Worlds.isDenyMessageExist(worldName) ? Worlds.getDenyMessage(worldName) : "";
                display_name = Locale.getMessage("gui.world.items." + menu_id + ".display_name");
                lore = Locale.yaml.getStringList("gui.world.items." + menu_id + ".lore").stream()
                        .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                        .map(line -> line.replaceAll("%message%", message)).collect(Collectors.toList());
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
            for (String f : list) {
                if ((slot - base) % 9 == 0) {
                    slot += 2;
                }

                Flag flag = FlagRegistry.matchFlag(f);
                Material material = Material.matchMaterial(flag.getShowItem());
                if (material == null) {
                    material = Material.PAPER;
                }
                String flagName = flag.getName();
                boolean bool = Worlds.getFlag(worldName, flagName);
                ItemStack item = new ItemStack(material);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(Locale.getMessage("gui.world.items.flag.display_name").replaceAll("%flag%", flagName).replaceAll("%bool%", Locale.getMessage(String.valueOf(bool))));
                List<String> lore_list = Locale.yaml.getStringList("gui.world.items.flag.lore").stream()
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
                Object value = Worlds.getGameRule(worldName, element);
                ItemStack item = new ItemStack(material);
                ItemMeta meta = item.getItemMeta();
                String gameruleBoolean = value instanceof Boolean ? Locale.getMessage("gamerule_"+(boolean) value) : "";
                String gameruleInteger = value instanceof Integer ? Locale.getMessage("value").replace("%value%", String.valueOf((int) value)) : "";
                meta.setDisplayName(Locale.getMessage("gui.world.items.gamerule.display_name").replaceAll("%gamerule%", element).replace("%bool%", gameruleBoolean).replace("%int%", gameruleInteger));
                List<String> lore_list = Locale.yaml.getStringList("gui.world.items.gamerule.lore").stream()
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
            boolean bool = Worlds.getFlag(worldName, flagName);
            meta.setDisplayName(Locale.getMessage("gui.world.items.flag.display_name").replaceAll("%flag%", flagName).replaceAll("%bool%", Locale.getMessage(String.valueOf(bool))));
            List<String> lore_list = Locale.yaml.getStringList("gui.world.items.flag.lore").stream()
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
        Object value = Worlds.getGameRule(worldName, gameruleName);
        String gameruleBoolean = value instanceof Boolean ? Locale.getMessage("gamerule_"+(boolean) value) : "";
        String gameruleInteger = value instanceof Integer ? Locale.getMessage("value").replace("%value%", String.valueOf((int) value)) : "";
        meta.setDisplayName(Locale.getMessage("gui.world.items.gamerule.display_name").replaceAll("%gamerule%", gameruleName).replace("%bool%", gameruleBoolean).replace("%int%", gameruleInteger));
        List<String> lore_list = Locale.yaml.getStringList("gui.world.items.gamerule.lore").stream()
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
                player.openInventory(new GUIWorld(worldName, Math.max(1, page-1), type).getInventory());
                player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 0.5f, 0.5f);
                return true;
            }
            case "next_page": {
                player.openInventory(new GUIWorld(worldName, page+1, type).getInventory());
                player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 0.5f, 0.5f);
                return true;
            }

            case "edit-permission": {
                NoBuildPlus.getTextEditMode().setPlayerAction(player, "edit-permission:" + worldName, inventory);
                player.closeInventory();
                player.sendMessage(Locale.getMessage("join-mode").replaceAll("%cancel%", NoBuildPlus.getTextEditMode().cancelWord));
                player.playSound(player, Sound.BLOCK_ANVIL_PLACE, 0.5f, 0.5f);
                return true;
            }

            case "edit-deny-message": {
                NoBuildPlus.getTextEditMode().setPlayerAction(player, "edit-deny-message:" + worldName, inventory);
                player.closeInventory();
                player.sendMessage(Locale.getMessage("join-mode").replaceAll("%cancel%", NoBuildPlus.getTextEditMode().cancelWord));
                player.playSound(player, Sound.BLOCK_ANVIL_PLACE, 0.5f, 0.5f);
                return true;
            }

            case "type": {
                GUIType new_type = type == GUIType.FLAG ? GUIType.GAMERULE : GUIType.FLAG;
                player.openInventory(new GUIWorld(worldName, page, new_type).getInventory());
                player.playSound(player, Sound.UI_BUTTON_CLICK, 0.5f, 0.5f);
                return true;
            }
        }

        if (name.startsWith("flag:")) {
            String flag = name.split(":")[1];
            boolean bool = Worlds.getFlag(worldName, flag);

            Worlds.setFlag(worldName, flag, !bool);
            updateSlot(slot);
            player.sendMessage(Locale.getMessage("flag-set-success").replace("%world%", worldName).replace("%flag%", flag).replace("%boolean%", String.valueOf(!bool)));
            player.playSound(player, Sound.ENTITY_VILLAGER_YES, 0.5f, 0.5f);
            return true;

        }

        if (name.startsWith("gamerule:")) {
            String gameruleName = name.split(":")[1];
            Object value = Worlds.getGameRule(worldName, gameruleName);
            if (value instanceof Boolean) {
                boolean bool = (boolean) value;
                Worlds.setGameRule(worldName, gameruleName, !bool);
                updateSlot(slot);
                player.sendMessage(Locale.getMessage("gamerule-set-success").replace("%world%", worldName).replace("%gamerule%", gameruleName).replace("%value%", String.valueOf(!bool)));
                player.playSound(player, Sound.ENTITY_VILLAGER_YES, 0.5f, 0.5f);
                Logger.debug("Setting world "+worldName+" gamerule: " + gameruleName);
                return true;
            }

            NoBuildPlus.getTextEditMode().setPlayerAction(player, "edit-gamerule:" + gameruleName + ":" + worldName, inventory);
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
