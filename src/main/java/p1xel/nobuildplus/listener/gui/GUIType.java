package p1xel.nobuildplus.listener.gui;

public enum GUIType {

    FLAG("flag"),
    GAMERULE("gamerule");

    private final String name;

    GUIType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
