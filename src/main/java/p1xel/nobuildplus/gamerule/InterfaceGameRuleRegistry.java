package p1xel.nobuildplus.gamerule;

import java.util.List;

public interface InterfaceGameRuleRegistry {

    List<String> getGameRules();

    void setWorldGameRule(String worldName, String rule, Object value);

}
