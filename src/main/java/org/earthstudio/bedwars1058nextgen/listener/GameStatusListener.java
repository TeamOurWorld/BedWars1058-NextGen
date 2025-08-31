package org.earthstudio.bedwars1058nextgen.listener;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.earthstudio.bedwars1058nextgen.arena.ArenaManager;

public class GameStatusListener implements Listener {

    @EventHandler
    public void gameStatusChange(GameStateChangeEvent event) {
        switch (event.getNewState()) {
            case playing -> ArenaManager.createArena(event.getArena());
            case restarting -> ArenaManager.removeArena(event.getArena());
        }
    }

}
