package p1xel.nobuildplus.gamerule;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface InterfaceGameRuleRegistry {

    List<String> getGameRules();

    void setWorldGameRule(String worldName, String rule, Object value);

    @Nullable
    String getWorldGameRule(String worldName, String rule);

}
