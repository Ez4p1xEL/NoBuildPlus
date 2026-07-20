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
import org.jetbrains.annotations.Nullable;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.storage.Locale;
import p1xel.nobuildplus.storage.template.Template;
import p1xel.nobuildplus.storage.template.TemplateManager;
import p1xel.nobuildplus.tool.ColorUtil;
import p1xel.nobuildplus.world.WorldManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GUITemplateList extends GUIAbstract implements InventoryHolder {

    private Inventory inventory;
    private final int page;
    private int size;
    private final String action;
    private final @Nullable GUIAbstract previousGUI;
    private final TemplateManager templateManager;

    public GUITemplateList(int page, String action, @Nullable GUIAbstract previousGUI) {
        this.page = page;
        this.action = action;
        this.previousGUI = previousGUI;
        this.templateManager = NoBuildPlus.getTemplateManager();
        init();
    }

    @Override
    public void init() {

        boolean hasPreviousPage = false;
        boolean hasNextPage = false;
        if (page > 1) {
            hasPreviousPage = true;
        }

        List<Template> registeredTemplates = templateManager.getTemplates();

        if (registeredTemplates.size() >= page*28) {
            hasNextPage = true;
        }

        registeredTemplates = registeredTemplates.subList(28 * (page-1), Math.min((page) * 28, registeredTemplates.size()));

        size = (int) Math.max(1,Math.ceil((double)registeredTemplates.size() / 7));
        Inventory inventory = Bukkit.createInventory(this, (size+2)*9, Locale.getMessage("gui.template-list.title"));

        if (hasNextPage) {
            inventory = setNextPage(inventory, "list", size);
        }

        if (hasPreviousPage) {
            inventory = setPreviousPage(inventory, "list", size);
        }

        inventory = setBackTo(inventory, "list", size);

        this.inventory = inventory;
        update(registeredTemplates);

    }

    public void update(List<Template> templates) {
        int slot = 10; // starting slot
        int base = 17;
        for (Template template : templates) {
            if ((slot-base)%9 == 0) {
                slot+=2;
            }

            String name = template.getName();
            String label = template.getLabel();
            String description = template.getDescription();
            String permission = template.getPermission();
            String denyMessage = ColorUtil.translateHexColorCodes(template.getDenyMessage());

            ItemStack item = new ItemStack(Material.BOOK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Locale.getMessage("gui.template-list.items.template.display_name").replace("%name%", name).replace("%label%", label));
            List<String> lore_list = Locale.yaml.getStringList("gui.template-list.items.template.lore").stream()
                    .map(ColorUtil::translateHexColorCodes)
                    .map(line -> line.replace("%name%",name)
                            .replace("%label%", label)
                            .replace("%description%", description)
                            .replace("%permission%", permission != null ? permission : "-")
                            .replace("%deny_message%", denyMessage != null ? denyMessage : "-"))
                    .collect(Collectors.toList());
            meta.setLore(lore_list);
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.set(menu_id_key, PersistentDataType.STRING, "template:" + name);
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
                ItemStack item = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
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
                GUIAbstract lastOpened = previousGUI != null ? previousGUI : new GUIMain(1);
                player.openInventory(lastOpened.getInventory());
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0f, 1.0f);
                return true;
            }
            case "previous_page": {
                player.openInventory(new GUITemplateList(Math.max(1, page-1), action, previousGUI).getInventory());
                player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1.0f, 1.0f);
                return true;
            }
            case "next_page": {
                player.openInventory(new GUITemplateList(page+1, action, previousGUI).getInventory());
                player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1.0f, 1.0f);
                return true;
            }
        }

        if (name.startsWith("template:")) {
            String templateName = name.split(":")[1];

            if (templateManager.getTemplate(templateName) == null) {
                player.sendMessage(Locale.getMessage("cant-find-template").replace("%template%", templateName));
                player.openInventory(new GUITemplateList(1, action, previousGUI).getInventory());
                return true;
            }

            if (action.equals("edit")) {
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0f, 1.0f);
                player.openInventory(new GUITemplate(templateName, 1, GUIType.FLAG, this).getInventory());
                return true;
            }

            if (action.equals("apply")) {
                GUIWorld worldGUI = (GUIWorld) previousGUI;
                String world = worldGUI.getWorldName();
                if (WorldManager.getWorld(world) == null) {
                    player.sendMessage(Locale.getMessage("cant-find-world"));
                    player.openInventory(new GUIMain(1).getInventory());
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                // Applying:
                WorldManager.applyTemplate(WorldManager.getWorld(world), templateManager.getTemplate(templateName));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
                player.sendMessage(Locale.getMessage("template-apply").replace("%template%", templateName).replace("%world%", world));
                player.openInventory(new GUIWorld(world, worldGUI.getPage(), worldGUI.getType()).getInventory());
                return true;

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
