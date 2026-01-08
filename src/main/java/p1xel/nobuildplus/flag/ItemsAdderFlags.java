package p1xel.nobuildplus.flag;

import p1xel.nobuildplus.Flag;
import p1xel.nobuildplus.storage.FlagsManager;
import p1xel.nobuildplus.storage.Locale;

import java.util.ArrayList;
import java.util.List;

public enum ItemsAdderFlags implements Flag {

    // Reference: WorldGuard Compatibility with ItemsAdder
    // 参考来自WorldGuard对ItemsAdder新增的可开关保护
    ia_furniture_sit("ia-furniture-sit", "OAK_STAIRS", null, null),
    ia_campfire_interact("ia-campfire-interact", "CAMPFIRE", null, null);
//    ia_vehicle_place("ia-vehicle-place", "minecart", null, null),
//    ia_vehicle_remove("ia-vehicle-remove", "minecart", null, null),
//    ia_vehicle_sit("ia-vehicle-sit", "minecart", null, null);

    private final String flag_name;
    private final String show_item;
    private final String type;
    private List<String> list;

    ItemsAdderFlags(String flag_name, String show_item, String type, List<String> list) {
        this.flag_name = flag_name;
        this.show_item = show_item;
        this.type = type;
        this.list = list;
    }

    @Override
    public String getName() {
        return flag_name;
    }

    @Override
    public String getType() {
        return FlagsManager.getFlagsType(getName());
    }

    @Override
    public List<String> getList() {
        return list;
    }

    @Override
    public String getDefaultType() {
        return type;
    }

    @Override
    public List<String> getDefaultList() {
        return list;
    }

    @Override
    public boolean inLocalFile() {
        return true;
    }

    @Override
    public void refreshMap() {
        for (ItemsAdderFlags flag : values()) {
            List<String> list = new ArrayList<>();
            if (FlagsManager.yaml.isSet("flags." + flag + ".list")) {
                list = FlagsManager.getFlagsList(flag.flag_name);
            }
            flag.list = list;
        }
    }

    @Override
    public String getShowItem() {
        return show_item;
    }

    @Override
    public String getDescription(String language) {
        return Locale.getMessage("flag.description." + flag_name);
    }
}
