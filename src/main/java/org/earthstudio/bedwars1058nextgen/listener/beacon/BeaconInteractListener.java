package org.earthstudio.bedwars1058nextgen.listener.beacon;

import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import org.earthstudio.bedwars1058nextgen.arena.ArenaInstance;
import org.earthstudio.bedwars1058nextgen.arena.ArenaManager;

import static org.earthstudio.bedwars1058nextgen.NextGen.bwAPI;

public class BeaconInteractListener implements Listener {

    @EventHandler
    public void onBeaconPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();

        if (block == null)
            return;

        if (block.getType() != Material.BEACON)
            return;

        Player player = event.getPlayer();
        IArena arena = bwAPI.getArenaUtil().getArenaByPlayer(player);

        if (arena == null)
            return;

        ArenaInstance arenaInstance = ArenaManager.getArena(arena);

        arenaInstance.getBeaconManager().create(block.getLocation(), arena.getTeam(player));
        // call visual effect here
    }

    @EventHandler
    public void onBeaconUse(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) // if not use action
            return;

        if (event.getClickedBlock().getType() != Material.BEACON)
            return;

        Player player = event.getPlayer();
        IArena arena = bwAPI.getArenaUtil().getArenaByPlayer(player);

        if (arena == null || arena.isSpectator(player)) // do not response action if player is not in game
            return;

        if (player.getGameMode() == GameMode.SPECTATOR) // link to teleport function, DO NOT combine it with up one
            return;

        if (player.isSneaking() && player.getItemInHand().getType() != Material.AIR) // for proper action to place block on beacon side
            return;

        event.setCancelled(true);
        // call beacon gui here
    }

    @EventHandler
    public void onBeaconDestroy(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.BEACON)
            return;

        Player player = event.getPlayer();
        IArena arena = bwAPI.getArenaUtil().getArenaByPlayer(player);

        if (arena == null)
            return;

        ArenaInstance arenaInstance = ArenaManager.getArena(arena);

        if (arenaInstance == null)
            return;

        arenaInstance.getBeaconManager().remove(event.getBlock().getLocation());

        // spawn item logic here
    }

}
