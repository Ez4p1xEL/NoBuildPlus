package p1xel.nobuildplus.listener.gui;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
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
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.gamerule.GameRuleRegistry;
import p1xel.nobuildplus.storage.*;
import p1xel.nobuildplus.world.NBPWorld;
import p1xel.nobuildplus.world.WorldManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GUIWorld extends GUIAbstract implements InventoryHolder {

    private Inventory inventory;
    private String worldName;
    private int page;
    private final GUIType type;
    private NBPWorld world;
    private boolean VIEW_FOR_GAMERULE_ONLY = false;

    public GUIWorld(String worldName, int page, GUIType type) {
        this.worldName = worldName;
        this.page = page;
        this.type = type;
        init();
    }

    @Override
    public void init() {
        this.world = (NBPWorld) WorldManager.getWorld(worldName);
        if (world == null && type == GUIType.GAMERULE) {
            VIEW_FOR_GAMERULE_ONLY = true;
        }

        if (this.inventory == null) {
            this.inventory = Bukkit.createInventory(this, 54, Locale.getMessage("gui.world.title").replaceAll("%world%", worldName));
        }
        this.inventory.clear();

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

        if (hasNextPage) {
            inventory = setNextPage(inventory, "world", 4);
        }

        if (hasPreviousPage) {
            inventory = setPreviousPage(inventory, "world", 4);
        }

        inventory = setBackTo(inventory, "world", 4);
        inventory = setTypeButton(inventory, "world", type);
        if (VIEW_FOR_GAMERULE_ONLY) {
            ItemStack type = inventory.getItem(4);
            ItemMeta typeMeta = type.getItemMeta();
            List<String> lore = typeMeta.getLore();
            lore.add(Locale.getMessage("gui.messages.not-enabled"));
            typeMeta.setLore(lore);
            type.setItemMeta(typeMeta);
        }

        inventory.setItem(0, getWikiButton());

        if (!VIEW_FOR_GAMERULE_ONLY) {

            Material edit_permission = Material.matchMaterial(MenuConfig.WORLD_SETTING_EDIT_PERMISSION);
            Material edit_deny_message = Material.matchMaterial(MenuConfig.WORLD_SETTING_EDIT_DENY_MESSAGE);
            setItem(edit_permission != null ? edit_permission : Material.PAPER, "edit-permission", 2);
            setItem(edit_deny_message != null ? edit_deny_message : Material.PAPER, "edit-deny-message", 6);
        }

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
                        .map(line -> line.replaceAll("%permission%", world.getPermission())).collect(Collectors.toList());
                break;
            }

            case "edit-deny-message": {
                String message = world.getDenyMessage() != null ? world.getDenyMessage() : "";
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
                boolean bool = world.getFlag(flag);
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

                String materialString = RuleSetting.getMaterialString(element);
                if (materialString == null) { materialString = "PAPER"; }
                Material material = Material.matchMaterial(materialString);

                String value = GameRuleRegistry.getWorldGameRule(worldName, element);
                Logger.debug("GameRule " + element + " value: " + value);
                Logger.debug("GameRule " + element + " class type: " + value.getClass().getName());
                ItemStack item = new ItemStack(material);
                ItemMeta meta = item.getItemMeta();
                String gameruleBoolean;
                String gameruleInteger = "";
                if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                    gameruleBoolean = Locale.getMessage("gamerule_" + value.toLowerCase());
                } else {
                    gameruleBoolean = "";
                    try {
                        int number = Integer.parseInt(value);
                        gameruleInteger = Locale.getMessage("value").replace("%value%", String.valueOf(number));
                    } catch (NumberFormatException exception) {
                        gameruleInteger = value;
                    }
                }

                String description = Locale.yaml.isSet("gamerule.description." + element) ? Locale.getMessage("gamerule.description." + element) : "";
                meta.setDisplayName(Locale.getMessage("gui.world.items.gamerule.display_name").replaceAll("%gamerule%", element).replace("%bool%", gameruleBoolean).replace("%int%", gameruleInteger));
                String finalGameruleInteger = gameruleInteger;
                List<String> lore_list = Locale.yaml.getStringList("gui.world.items.gamerule.lore").stream()
                        .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                        .map(line -> line.replace("%gamerule%", element)
                                .replace("%bool%", gameruleBoolean)
                                .replace("%int%", finalGameruleInteger)
                                .replace("%description%", description))
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
                Material empty_slot = Material.matchMaterial(MenuConfig.GLOBAL_EMPTY_SLOT);
                ItemStack item = new ItemStack(empty_slot != null ? empty_slot : Material.GRAY_STAINED_GLASS_PANE);
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
            boolean bool = world.getFlag(flag);
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
        String value = GameRuleRegistry.getWorldGameRule(worldName, gameruleName);
        String gameruleBoolean;
        String gameruleInteger = "";
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            gameruleBoolean = Locale.getMessage("gamerule_" + value.toLowerCase());
        } else {
            gameruleBoolean = "";
            try {
                int number = Integer.parseInt(value);
                gameruleInteger = Locale.getMessage("value").replace("%value%", String.valueOf(number));
            } catch (NumberFormatException exception) {
                gameruleInteger = value;
            }
        }
        String description = Locale.yaml.isSet("gamerule.description." + gameruleName) ? Locale.getMessage("gamerule.description." + gameruleName) : "";
        meta.setDisplayName(Locale.getMessage("gui.world.items.gamerule.display_name").replaceAll("%gamerule%", gameruleName).replace("%bool%", gameruleBoolean).replace("%int%", gameruleInteger));
        String finalGameruleInteger = gameruleInteger;
        List<String> lore_list = Locale.yaml.getStringList("gui.world.items.gamerule.lore").stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                .map(line -> line.replace("%gamerule%", gameruleName)
                        .replace("%bool%", gameruleBoolean)
                        .replace("%int%", finalGameruleInteger)
                        .replace("%description%", description))
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
                if (VIEW_FOR_GAMERULE_ONLY) {
                    player.openInventory(new GUIWorldList(1).getInventory());
                } else {
                    player.openInventory(new GUIMain(1).getInventory());
                }
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
                NoBuildPlus.getTextEditMode().setPlayerAction(player, "edit-permission:" + worldName, this);
                player.closeInventory();
                player.sendMessage(Locale.getMessage("join-mode").replaceAll("%cancel%", NoBuildPlus.getTextEditMode().cancelWord));
                player.playSound(player, Sound.BLOCK_ANVIL_PLACE, 0.5f, 0.5f);
                return true;
            }

            case "edit-deny-message": {
                NoBuildPlus.getTextEditMode().setPlayerAction(player, "edit-deny-message:" + worldName, this);
                player.closeInventory();
                player.sendMessage(Locale.getMessage("join-mode").replaceAll("%cancel%", NoBuildPlus.getTextEditMode().cancelWord));
                player.playSound(player, Sound.BLOCK_ANVIL_PLACE, 0.5f, 0.5f);
                return true;
            }

            case "type": {
                if (VIEW_FOR_GAMERULE_ONLY) {
                    return true;
                }
                GUIType new_type = type == GUIType.FLAG ? GUIType.GAMERULE : GUIType.FLAG;
                player.openInventory(new GUIWorld(worldName, 1, new_type).getInventory());
                player.playSound(player, Sound.UI_BUTTON_CLICK, 0.5f, 0.5f);
                return true;
            }

            case "wiki": {
                TextComponent text = new TextComponent(Locale.getMessage("documentation"));
                        text.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://docs.p1mc.top/"));
                player.spigot().sendMessage(text);
                player.playSound(player, Sound.BLOCK_PISTON_EXTEND, 0.5f, 0.5f);
                player.closeInventory();
                return true;
            }
        }

        if (name.startsWith("flag:")) {
            String flagName = name.split(":")[1];
            Flag flag = FlagRegistry.matchFlag(flagName);
            boolean bool = world.getFlag(flag);

            WorldManager.setFlag(world, flag, !bool);
            updateSlot(slot);
            player.sendMessage(Locale.getMessage("flag-set-success").replace("%world%", worldName).replace("%flag%", flagName).replace("%boolean%", String.valueOf(!bool)));
            player.playSound(player, Sound.ENTITY_VILLAGER_YES, 0.5f, 0.5f);
            return true;

        }

        if (name.startsWith("gamerule:")) {
            String gameruleName = name.split(":")[1];
            String value = GameRuleRegistry.getWorldGameRule(worldName, gameruleName);

            try {
                int number = Integer.parseInt(value);
                NoBuildPlus.getTextEditMode().setPlayerAction(player, "edit-gamerule:" + gameruleName + ":" + worldName, this);
                player.closeInventory();
                player.sendMessage(Locale.getMessage("join-mode").replaceAll("%cancel%", NoBuildPlus.getTextEditMode().cancelWord));
                player.playSound(player, Sound.BLOCK_ANVIL_PLACE, 0.5f, 0.5f);
                return true;
            } catch (NumberFormatException ignored) {}

            boolean bool = Boolean.parseBoolean(value);
            GameRuleRegistry.setWorldGameRule(worldName, gameruleName, !bool);
            if (NoBuildPlus.getFoliaLib().isFolia()) {
                NoBuildPlus.getFoliaLib().getScheduler().runNextTick(task -> updateSlot(slot));
            } else {
                updateSlot(slot);
            }
            player.sendMessage(Locale.getMessage("gamerule-set-success").replace("%world%", worldName).replace("%gamerule%", gameruleName).replace("%value%", String.valueOf(!bool)));
            player.playSound(player, Sound.ENTITY_VILLAGER_YES, 0.5f, 0.5f);
            Logger.debug("Setting world "+worldName+" gamerule: " + gameruleName);
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

    @Override
    public int getPage() {
        return page;
    }

    public GUIType guiType() { return type; }
}
