package org.earthstudio.bedwars1058nextgen;

import com.andrei1058.bedwars.api.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.earthstudio.bedwars1058nextgen.configuration.Config;
import org.earthstudio.bedwars1058nextgen.configuration.Message;

public final class NextGen extends JavaPlugin {

    public static BedWars bwAPI;
    public static JavaPlugin plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Disable if BedWars1058 not found
        if (Bukkit.getPluginManager().getPlugin("BedWars1058") == null) {
            getLogger().severe("BedWars1058 was not found. Disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        bwAPI = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
        plugin = this;

        Config.addDefaultConfig();
        Message.setupMessages();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        bwAPI = null;
        plugin = null;
    }
}
