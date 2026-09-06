package gr1mly4memes.slime.config;


import gr1mly4memes.slime.SlimeConfig;
import gr1mly4memes.slime.commands.GlobalConfigManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class GlobalConfigCreator {

    public static void main(String[] args) {
        YamlConfiguration config = new YamlConfiguration();
        config.options().setHeader(SlimeConfig.CONFIG_HEADER);

        config.set("config-version", SlimeConfig.CURRENT_CONFIG_VERSION);

        Class<SlimeConfig> clazz = SlimeConfig.class;

        for (Field field : clazz.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);

                GlobalConfig globalConfig = field.getAnnotation(GlobalConfig.class);
                if (globalConfig != null) {
                    try {
                        GlobalConfigManager.VerifiedConfig verifiedConfig = GlobalConfigManager.VerifiedConfig.build(globalConfig, field);

                        ConfigVerify<? super Object> verify = verifiedConfig.verify();
                        boolean isEnumConfig = verify instanceof ConfigVerify.EnumConfigVerify;

                        Object defValue = isEnumConfig ? field.get(null).toString() : field.get(null);
                        config.set(verifiedConfig.path(), defValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        try {
            File file = new File("slime.yml");
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
