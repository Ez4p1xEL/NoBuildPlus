package p1xel.nobuildplus;

import p1xel.nobuildplus.storage.Settings;
import p1xel.nobuildplus.storage.Worlds;

import java.util.List;

public interface Flag {

    /**
     * @return name of the flag
     */
    String getName();

    /**
     * This can refer to your own flag setting file
     * @return type of the flag
     */

    String getType();
    /**
     * This can refer to your own flag setting file
     * @return list of the flag
     */
    List<String> getList();

    /**
     * This is recommended to initialize in the constructor
     * @return type of the flag
     */
    String getDefaultType();

    /**
     * This is recommended to initialize in the constructor
     * @return list of the flag
     */
    List<String> getDefaultList();

    /**
     * True means the plugin is protecting
     * True代表插件允许进行保护
     * @param world name of the world
     * @return boolean
     */
    default boolean isEnabled(String world) {
        if (!Settings.canExecute(world, getName())) {
            return false;
        }

        return !Worlds.getFlag(world, getName());
    }

    /**
     * Should the flag stay at flags.yml
     * Otherwise, you can let the flags settings staying in your own file.
     * 是否让规则的设置留在flags.yml, 否则你可以设置它到你的插件的文件中
     * @return boolean
     */
    boolean inLocalFile();

    /**
     * List Map
     */
    void refreshMap();


    // For Menu

    /**
     * Item on menu
     * 菜单显示的物品
     * @return material.toString()
     */
    String getShowItem();

    /**
     * To get language of NoBuildPlus, see NBPAPI.getLang()
     * @param language language of config
     * @return type of the flag
     */
    String getDescription(String language);

}
