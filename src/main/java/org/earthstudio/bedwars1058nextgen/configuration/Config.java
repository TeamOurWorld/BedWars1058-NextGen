package org.earthstudio.bedwars1058nextgen.configuration;

import com.andrei1058.bedwars.api.configuration.ConfigManager;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.earthstudio.bedwars1058nextgen.NextGen;

import java.io.File;
import java.util.logging.Level;

public class Config {

    private Config() {}

    public static final String EXAMPLE = "example";

    public static ConfigManager config;

    public static void addDefaultConfig() {
        new File("plugins/BedWars1058/Addons/NextGen").mkdirs();

        config = new ConfigManager(NextGen.plugin, "config", "plugins/BedWars1058/Addons/NextGen");
        YamlConfiguration yml = config.getYml();
        yml.options().header("example header");

        yml.addDefault(EXAMPLE, "example");
    }

    public static void playSound(Player player, String path) {
        if (player == null) return;
        String sound = Config.config.getString(path);
        try {
            player.playSound(player.getLocation(), Sound.valueOf(sound), 1f, 1f);
        } catch (Exception e) {
            sendSoundLog(sound);
        }
    }

    private static void sendSoundLog(String sound) {
        NextGen.plugin.getLogger().log(Level.SEVERE, sound + " 在你的服务器版本中不是有效的音效！");
    }

}
