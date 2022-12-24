package coolguy1842.factions.Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import coolguy1842.factions.Managers.FactionsManager;

public class FactionRank {
    private UUID id;
    private Faction faction;

    private String displayName;

    private Boolean isDefault;
    private ArrayList<String> permissions;

    public HashMap<UUID, FactionPlayer> players;

    public FactionRank(UUID id, Faction faction, String displayName, Boolean isDefault, String... permissions) {
        this.id = id;
        this.faction = faction;
        
        this.displayName = displayName;
        this.isDefault = isDefault;

        this.permissions = new ArrayList<String>(List.of(permissions));

        this.players = new HashMap<>();
    }


    public UUID getID() { return this.id; }
    public Faction getFaction() { return this.faction; }
    public String getDisplayName() { return this.displayName; }
    public Boolean getIsDefault() { return this.isDefault; }
    
    private String getPermissionsString() { return String.join(",", permissions);  }
    public ArrayList<String> getPermissions() { return this.permissions; }


    public void setFaction(Faction faction) { 
        if(faction == null) return;

        this.faction.ranks.remove(this.id);
        this.faction = faction;
        
        this.faction.ranks.put(this.id, this);
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


    public Boolean hasPlayer(UUID id) { return this.players.containsKey(id); }
    public Boolean hasPlayer(Player player) { return this.players.containsKey(player.getUniqueId()); }
}
