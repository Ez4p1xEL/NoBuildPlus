package p1xel.nobuildplus.listener.gui;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import p1xel.nobuildplus.Flag;
import p1xel.nobuildplus.FlagRegistry;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.gamerule.GameRuleRegistry;
import p1xel.nobuildplus.storage.*;
import p1xel.nobuildplus.storage.template.Template;
import p1xel.nobuildplus.storage.template.TemplateManager;
import p1xel.nobuildplus.tool.ColorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class GUITemplate extends GUIAbstract implements InventoryHolder {

    private final String name;
    private Inventory inventory;
    private final int page;
    private final GUIType type;
    private final @Nullable GUIAbstract previousGUI;
    private @NotNull Template template;
    private final TemplateManager templateManager;
    private String permission = "";
    private String denyMessage = "";

    public GUITemplate(String name, int page, GUIType type, @Nullable GUIAbstract previousGUI) {
        this.name = name;
        this.page = page;
        this.type = type;
        this.previousGUI = previousGUI;
        this.templateManager = NoBuildPlus.getTemplateManager();
        init();
    }

    @Override
    public void init() {

        template = NoBuildPlus.getTemplateManager().getTemplate(name);
        if (template == null) {
            Logger.log(Level.WARNING, "Template " + name + " not found, please check the template name.");
            return;
        }

        boolean hasPreviousPage = page > 1;
        boolean hasNextPage;

        List<String> list = new ArrayList<>();

        if (type == GUIType.GAMERULE) {
            //list = GameRuleRegistry.getRegisteredGameRules();
            for (String gamerule : GameRuleRegistry.getRegisteredGameRules()) {
                if (template.getGameRule(gamerule) != null) {
                    list.add(gamerule);
                }
            }
            hasNextPage = list.size() > page * 28;

        } else {
            list = FlagsManager.getFlags();
            hasNextPage = list.size() > page * 28;
        }

        // 获取符合当前页面的数量
        list = list.subList(28 * (page - 1), Math.min((page) * 28, list.size()));

        if (template.getPermission() != null) {
            permission = template.getPermission();
        }
        if (template.getDenyMessage() != null) {
            denyMessage = ColorUtil.translateHexColorCodes(template.getDenyMessage());
        }

        Inventory inventory = Bukkit.createInventory(this, 54, Locale.getMessage("gui.edit-template.title").replace("%name%", name).replace("%label%", template.getLabel()));

        if (hasNextPage) {
            inventory = setNextPage(inventory, "edit-template", 4);
        }

        if (hasPreviousPage) {
            inventory = setPreviousPage(inventory, "edit-template", 4);
        }

        inventory = setBackTo(inventory, "edit-template", 4);
        inventory = setTypeButton(inventory, "edit-template", type);

        inventory.setItem(0, getWikiButton());

        this.inventory = inventory;

        Material edit_permission = Material.matchMaterial(MenuConfig.WORLD_SETTING_EDIT_PERMISSION);
        Material edit_deny_message = Material.matchMaterial(MenuConfig.WORLD_SETTING_EDIT_DENY_MESSAGE);
        setItem(edit_permission != null ? edit_permission : Material.PAPER, "edit-permission", 2);
        setItem(edit_deny_message != null ? edit_deny_message : Material.PAPER, "edit-deny-message", 6);

        update(list);

    }

    private void setItem(Material material, String menu_id, int slot) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        String display_name = null;
        List<String> lore = new ArrayList<>();

        switch (menu_id) {

            case "back_to_main": {

                display_name = Locale.getMessage("gui.edit-template.items." + menu_id + ".display_name");
                lore = Locale.yaml.getStringList("gui.edit-template.items." + menu_id + ".lore").stream()
                        .map(ColorUtil::translateHexColorCodes).collect(Collectors.toList());
                break;
            }

            case "edit-permission": {
                display_name = Locale.getMessage("gui.edit-template.items." + menu_id + ".display_name");
                lore = Locale.yaml.getStringList("gui.edit-template.items." + menu_id + ".lore").stream()
                        .map(ColorUtil::translateHexColorCodes)
                        .map(line -> line.replaceAll("%permission%", permission)).collect(Collectors.toList());
                break;
            }

            case "edit-deny-message": {
                display_name = Locale.getMessage("gui.edit-template.items." + menu_id + ".display_name");
                lore = Locale.yaml.getStringList("gui.edit-template.items." + menu_id + ".lore").stream()
                        .map(ColorUtil::translateHexColorCodes)
                        .map(line -> line.replaceAll("%message%", denyMessage)).collect(Collectors.toList());
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
                boolean bool = template.getFlag(flag);
                ItemStack item = new ItemStack(material);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(Locale.getMessage("gui.edit-template.items.flag.display_name").replaceAll("%flag%", flagName).replaceAll("%bool%", Locale.getMessage(String.valueOf(bool))));
                List<String> lore_list = Locale.yaml.getStringList("gui.edit-template.items.flag.lore").stream()
                        .map(ColorUtil::translateHexColorCodes)
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
                Object value = template.getGameRule(element);
                ItemStack item = new ItemStack(material);
                ItemMeta meta = item.getItemMeta();
                String gameruleBoolean = value instanceof Boolean ? Locale.getMessage("gamerule_"+(boolean) value) : "";
                String gameruleInteger = value instanceof Integer ? Locale.getMessage("value").replace("%value%", String.valueOf((int) value)) : "";
                meta.setDisplayName(Locale.getMessage("gui.edit-template.items.gamerule.display_name").replaceAll("%gamerule%", element).replace("%bool%", gameruleBoolean).replace("%int%", gameruleInteger));
                List<String> lore_list = Locale.yaml.getStringList("gui.edit-template.items.gamerule.lore").stream()
                        .map(ColorUtil::translateHexColorCodes)
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
            boolean bool = template.getFlag(flag);
            meta.setDisplayName(Locale.getMessage("gui.edit-template.items.flag.display_name").replaceAll("%flag%", flagName).replaceAll("%bool%", Locale.getMessage(String.valueOf(bool))));
            List<String> lore_list = Locale.yaml.getStringList("gui.edit-template.items.flag.lore").stream()
                    .map(ColorUtil::translateHexColorCodes)
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
        Object value = template.getGameRule(gameruleName);
        String gameruleBoolean = value instanceof Boolean ? Locale.getMessage("gamerule_"+(boolean) value) : "";
        String gameruleInteger = value instanceof Integer ? Locale.getMessage("value").replace("%value%", String.valueOf((int) value)) : "";
        meta.setDisplayName(Locale.getMessage("gui.edit-template.items.gamerule.display_name").replaceAll("%gamerule%", gameruleName).replace("%bool%", gameruleBoolean).replace("%int%", gameruleInteger));
        List<String> lore_list = Locale.yaml.getStringList("gui.edit-template.items.gamerule.lore").stream()
                .map(ColorUtil::translateHexColorCodes)
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
                player.openInventory(previousGUI.getInventory());
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0f, 1.0f);
                return true;
            }
            case "previous_page": {
                player.openInventory(new GUITemplate(this.name, Math.max(1, page-1), type, previousGUI).getInventory());
                player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1.0f, 1.0f);
                return true;
            }
            case "next_page": {
                player.openInventory(new GUITemplate(this.name, page+1, type, previousGUI).getInventory());
                player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1.0f, 1.0f);
                return true;
            }

            case "edit-permission": {
                NoBuildPlus.getTextEditMode().setPlayerAction(player, "edit-template-permission", this);
                player.closeInventory();
                player.sendMessage(Locale.getMessage("join-mode").replaceAll("%cancel%", NoBuildPlus.getTextEditMode().cancelWord));
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0f, 1.0f);
                return true;
            }

            case "edit-deny-message": {
                NoBuildPlus.getTextEditMode().setPlayerAction(player, "edit-template-deny-message", this);
                player.closeInventory();
                player.sendMessage(Locale.getMessage("join-mode").replaceAll("%cancel%", NoBuildPlus.getTextEditMode().cancelWord));
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0f, 1.0f);
                return true;
            }

            case "type": {
                GUIType new_type = type == GUIType.FLAG ? GUIType.GAMERULE : GUIType.FLAG;
                player.openInventory(new GUITemplate(this.name,1, new_type, previousGUI).getInventory());
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
                return true;
            }

            case "wiki": {
                TextComponent text = new TextComponent(Locale.getMessage("documentation"));
                text.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://docs.p1mc.top/"));
                player.spigot().sendMessage(text);
                player.playSound(player.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1.0f, 1.0f);
                player.closeInventory();
                return true;
            }
        }

        if (name.startsWith("flag:")) {
            String flagName = name.split(":")[1];
            Flag flag = FlagRegistry.matchFlag(flagName);
            boolean bool = template.getFlag(flag);

            //Settings.setDefaultFlag(flag, !bool);
            templateManager.setFlag(template, flag, !bool);
            updateSlot(slot);
            player.sendMessage(Locale.getMessage("template-flag-set-success").replace("%flag%", flagName).replace("%boolean%", String.valueOf(!bool)).replace("%template%", template.getName()));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
            return true;

        }

        if (name.startsWith("gamerule:")) {
            String gameruleName = name.split(":")[1];
            Object value = template.getGameRule(gameruleName);
            if (value instanceof Boolean) {
                boolean bool = (boolean) value;
                templateManager.setGameRule(template, gameruleName, !bool);
                updateSlot(slot);
                player.sendMessage(Locale.getMessage("template-gamerule-set-success").replace("%gamerule%", gameruleName).replace("%value%", String.valueOf(!bool)).replace("%template%", template.getName()));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
                Logger.debug("Setting template gamerule: " + gameruleName);
                //GameRuleRegistry.setWorldGameRule("world", gameruleName, !bool); // for test
                return true;
            }

            NoBuildPlus.getTextEditMode().setPlayerAction(player, "edit-template-gamerule:" + gameruleName, this);
            player.closeInventory();
            player.sendMessage(Locale.getMessage("join-mode").replaceAll("%cancel%", NoBuildPlus.getTextEditMode().cancelWord));
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0f, 1.0f);
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

    public Template getTemplate() { return template; }
}
