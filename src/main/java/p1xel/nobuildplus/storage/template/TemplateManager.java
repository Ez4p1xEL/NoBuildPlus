package p1xel.nobuildplus.storage.template;

import org.bukkit.plugin.java.JavaPlugin;
import p1xel.nobuildplus.Flag;
import p1xel.nobuildplus.storage.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TemplateManager {

    private final JavaPlugin plugin;
    private final HashMap<String, Template> templates = new HashMap<>();
    private final List<String> savedTemplateNames = Arrays.asList(
            "default",
            "lobby",
            "arena",
            "instance"
    );

    public TemplateManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerTemplate(Template template) {
        templates.put(template.getName(), template);
    }

    public void unregisterTemplate(String name) {
        templates.remove(name);
    }

    public void initializeTemplates() {
        templates.clear();
        File templateFolder = new File(plugin.getDataFolder(), "templates");

        boolean firstTimeLoaded = false;
        if (!templateFolder.exists()) {
            firstTimeLoaded = true;
            templateFolder.mkdirs();
        }

        // 加载 default.yml 模板
        if (firstTimeLoaded) {
            for (String templateName : savedTemplateNames) {
                File templateFile = new File(templateFolder, templateName + ".yml");
                if (!templateFile.exists()) {
                    plugin.saveResource("templates/" + templateName + ".yml", false);
                }
            }
        }

        File[] files = templateFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files != null) {
            for (File file : files) {
                String templateName = file.getName().replace(".yml", "");
                Template template = new Template(templateName);
                registerTemplate(template);
                Logger.debug("Template " + templateName + " has been loaded.");
            }
        }
    }

    public void setFlag(Template template, Flag flag, boolean value) {
        template.setFlag(flag, value);
    }

    public void setGameRule(Template template, String gameRule, Object value) {
        template.setGameRule(gameRule, value);
    }

    public void setDenyMessage(Template template, String message) {
        template.setDenyMessage(message);
    }

    public void setPermission(Template template, String permission) {
        template.setPermission(permission);
    }

    public void setLabel(Template template, String label) {
        template.setLabel(label);
    }

    public void setDescription(Template template, String description) {
        template.setDescription(description);
    }

    public Template getTemplate(String name) {
        return templates.get(name);
    }

    public List<Template> getTemplates() {
        return new ArrayList<>(templates.values());
    }

}
