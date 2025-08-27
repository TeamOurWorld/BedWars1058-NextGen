package org.earthstudio.bedwars1058nextgen;

import com.andrei1058.bedwars.api.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class NextGen extends JavaPlugin {

    BedWars bwAPI;

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
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        bwAPI = null;
    }
}
