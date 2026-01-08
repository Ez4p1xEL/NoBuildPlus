package p1xel.nobuildplus;

import java.util.*;

public class FlagRegistry {

    private static final List<Flag> externalFlags = new ArrayList<>();
    private static final HashMap<String, Flag> NAMEMAP = new HashMap<>();

    public static void registerFlag(Flag flag) {
        externalFlags.add(flag);
        NAMEMAP.put(flag.getName(), flag);
    }

    public static List<Flag> getAllFlags() {
        List<Flag> list = new ArrayList<>();
        list.addAll(Arrays.asList(Flags.values()));
        list.addAll(externalFlags);
        return list;
    }

    public static Flag matchFlag(final String name) {
        Flag result;

        String filtered = name.toLowerCase();

        //filtered = filtered.replaceAll("-", "_");
        result = NAMEMAP.get(filtered);

        return result;
    }

    static {
        for (Flags flag : Flags.values()) {

            NAMEMAP.put(flag.getName(), flag);
        }
    }

    public static void refreshMap() {
        for (Flag flag : getAllFlags()) {
            flag.refreshMap();
        }
    }

    public static HashMap<String, Flag> getMap() { return NAMEMAP; }

}
