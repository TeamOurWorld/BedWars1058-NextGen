package org.earthstudio.bedwars1058nextgen.arena;

import org.earthstudio.bedwars1058nextgen.gameplay.beacon.BeaconManager;

public class ArenaInstance {

    private BeaconManager beaconManager;

    public ArenaInstance(BeaconManager beaconManager) {
        this.beaconManager = beaconManager;
    }

    public BeaconManager getBeaconManager() {
        return beaconManager;
    }

    public void shutdown() {
        beaconManager.shutdown();
        beaconManager = null;
    }

}
