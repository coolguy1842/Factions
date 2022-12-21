package coolguy1842.factions.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class FactionRank {
    public UUID id;
    public Faction faction;

    public String displayName;

    public Boolean isDefault;
    ArrayList<String> permissions;

    public FactionRank(UUID id, Faction faction, String displayName, Boolean isDefault, String... permissions) {
        this.id = id;
        this.faction = faction;
        
        this.displayName = displayName;
        this.isDefault = isDefault;

        this.permissions = new ArrayList<String>(List.of(permissions));
    }


    public UUID getID() { return this.id; }
    public Faction getFaction() { return this.faction; }
    public String getDisplayName() { return this.displayName; }
    public Boolean getIsDefault() { return this.isDefault; }
    
    private String getPermissionsString() { return String.join(",", permissions);  }


    public void setFaction(Faction faction) { 
        if(faction == null) return;

        this.faction = faction;
        FactionsManager.getInstance().rankManager.setRankFaction(this.id, faction.getID());
    }

    public void setFaction(UUID factionID) {
        this.setFaction(FactionsManager.getInstance().factionManager.getFaction(factionID));
    } 

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        FactionsManager.getInstance().rankManager.setRankDisplayName(this.id, displayName);
    } 

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
        FactionsManager.getInstance().rankManager.setRankIsDefault(this.id, isDefault);
    } 


    public Boolean hasPermission(String permission) { return this.permissions.contains(permission); }

    public void addPermission(String permission) {
        if(this.permissions.contains(permission)) return;
        this.permissions.add(permission);

        FactionsManager.getInstance().rankManager.setRankPermissions(this.id, this.getPermissionsString());
    }
    
    public void removePermission(String permission) {
        if(!this.permissions.contains(permission)) return;
        this.permissions.remove(permission);

        FactionsManager.getInstance().rankManager.setRankPermissions(this.id, this.getPermissionsString());
    }
}
