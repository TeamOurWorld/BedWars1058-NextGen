package org.earthstudio.bedwars1058nextgen.gameplay.beacon;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.Location;

import java.util.UUID;

public class BeaconStatement {

    public enum Status{
        INACTIVE,
        ACTIVE,
        CAPTURING,
        INTERRUPTING,
        BROKEN
    }

    private Location location;
    private Status status;
    private ITeam ownerTeam;
    private ITeam attackerTeam;
    private UUID uuid;

    public BeaconStatement(Location location, Status status, ITeam ownerTeam) {
        this(location, status, ownerTeam, null);
    }

    public BeaconStatement(Location location, Status status, ITeam ownerTeam, ITeam attackerTeam) {
        this.location = location;
        this.status = status;
        this.ownerTeam = ownerTeam;
        this.attackerTeam = attackerTeam;
        this.uuid = UUID.randomUUID();
    }

    public Location getLocation() {
        return location;
    }

    public Status getStatus() {
        return status;
    }

    public ITeam getOwnerTeam() {
        return ownerTeam;
    }

    public ITeam getAttackerTeam() {
        return attackerTeam;
    }

    public UUID getUuid() {
        return uuid;
    }

}
