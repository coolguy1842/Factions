package coolguy1842.factions.Classes;

import java.util.UUID;

import org.bukkit.Location;

import coolguy1842.factions.Managers.FactionsManager;

public class FactionHome {
    private UUID id;
    private String displayName;

    private Faction faction;
    private Location location;

    public FactionHome(UUID id, String displayName, Faction faction, Location location) {
        this.id = id;
        this.displayName = displayName;

        this.faction = faction;
        this.location = location;
    }


    public UUID getID() { return this.id; }
    public String getDisplayName() { return this.displayName; }
    public Faction getFaction() { return this.faction; }
    public Location getLocation() { return this.location; }


    public void setDisplayName(String displayName) {
        this.displayName = displayName;

        FactionsManager.getInstance().homeManager.setHomeDisplayName(this.id, this.displayName);
    }

    public void setLocation(Location location) {
        this.location = location;

        FactionsManager.getInstance().homeManager.setHomeLocation(this.id, this.location);
    }
}
