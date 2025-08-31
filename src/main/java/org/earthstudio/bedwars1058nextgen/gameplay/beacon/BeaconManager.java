package org.earthstudio.bedwars1058nextgen.gameplay.beacon;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class BeaconManager {

    private final Map<Location, BeaconStatement> beaconMap = new HashMap<>();

    public void create(Location location, ITeam ownerTeam) {
        setStatement(new BeaconStatement(location, BeaconStatement.Status.ACTIVE, ownerTeam));
    }

    public void setStatement(BeaconStatement beaconStatement) {
        beaconMap.put(beaconStatement.getLocation(), beaconStatement);
    }

    public BeaconStatement getStatement(Location location) {
        return beaconMap.get(location);
    }

    public void remove(Location location) {
        beaconMap.remove(location);
    }

    public void shutdown() {
        beaconMap.clear();
    }

}
