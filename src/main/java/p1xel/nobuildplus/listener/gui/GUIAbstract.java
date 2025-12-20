package p1xel.nobuildplus.listener.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

public abstract class GUIAbstract {

    public abstract void init();
    public abstract boolean check(Player player, String name);
    public abstract boolean check(Player player, String name, ClickType clickType);
    public abstract Inventory getInventory();

}
