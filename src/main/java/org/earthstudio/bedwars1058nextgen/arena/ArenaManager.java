package org.earthstudio.bedwars1058nextgen.arena;

import com.andrei1058.bedwars.api.arena.IArena;
import org.earthstudio.bedwars1058nextgen.gameplay.beacon.BeaconManager;

import java.util.HashMap;
import java.util.Map;

public class ArenaManager {

    private ArenaManager() {}

    private static Map<IArena, ArenaInstance> arenaMap = new HashMap<>();

    public static void createArena(IArena arena) {
        arenaMap.put(arena, new ArenaInstance(
                new BeaconManager()
        ));
    }

    public static void removeArena(IArena arena) {
        arenaMap.remove(arena).shutdown();
    }

    public static ArenaInstance getArena(IArena arena) {
        return arenaMap.get(arena);
    }

}
