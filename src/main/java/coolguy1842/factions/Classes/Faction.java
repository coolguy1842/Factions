package coolguy1842.factions.Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import coolguy1842.factions.Managers.FactionsManager;
import net.kyori.adventure.text.Component;

public class Faction {
    private UUID id;
    private String displayName;

    private UUID leader;
    private FactionRank defaultRank;
    
    private Long money;

    private HashMap<String, Object> options;

    public HashMap<UUID, FactionPlayer> players;
    public HashMap<UUID, FactionRank> ranks;
    public ArrayList<UUID> invites;

    public Faction(UUID id, String displayName, UUID leader, Long money) {
        this.id = id;
        
        this.displayName = displayName;
        this.leader = leader;

        this.money = money;

        this.options = FactionsManager.getInstance().factionManager.getFactionOptions(this.id);
        
        this.players = new HashMap<>();
        this.ranks = new HashMap<>();
        this.invites = new ArrayList<>();
    }

    
    public UUID getID() { return this.id; }
    public Long getMoney() { return this.money; }
    public UUID getLeader() { return this.leader; }
    public String getDisplayName() { return this.displayName; }
    public FactionRank getDefaultRank() { return this.defaultRank; }
    
    public Component getFormattedDisplayName() {
        return Component.text(this.displayName); 
    }

    public void setMoney(Long money) {
        this.money = money;
        FactionsManager.getInstance().factionManager.setFactionMoney(this.id, money);
    }
    
    public void setLeader(UUID leader) {
        this.leader = leader;
        FactionsManager.getInstance().factionManager.setFactionLeader(this.id, leader);
    }
    
    public void setDefaultRank(UUID rank) {
        if(this.defaultRank != null) this.defaultRank.setIsDefault(false);

        this.defaultRank = FactionsManager.getInstance().rankManager.getRank(rank);
        if(this.defaultRank != null) this.defaultRank.setIsDefault(true);
    }
    
    public void setDisplayName(String displayName) {
        FactionsManager.getInstance().factionManager.factionsNameLookup.remove(this.displayName);

        this.displayName = displayName;
        FactionsManager.getInstance().factionManager.factionsNameLookup.put(this.displayName, this.id);
        FactionsManager.getInstance().factionManager.setFactionDisplayName(this.id, displayName);
    }


    public Object getOption(String option) {
        return this.options.get(option);
    }

    private String getOptionsString() {
        String out = "";

        for(Map.Entry<String, Object> entry : this.options.entrySet()) {
            out += entry.getKey() + ":" + entry.getValue().toString();
        }

        return out;
    }

    public void setOption(String option, Object value) {
        if(this.options.containsKey(option)) return;

        this.options.put(option, value);
        FactionsManager.getInstance().factionManager.setFactionOptions(this.id, this.getOptionsString());
    }

    public void removeOption(String option) {
        if(!this.options.containsKey(option)) return;
        
        this.options.remove(option);
        FactionsManager.getInstance().factionManager.setFactionOptions(this.id, this.getOptionsString());
    }


    public Boolean hasPlayer(UUID playerID) {
        return this.players.containsKey(playerID);
    }

    public Boolean hasDefaultRank() {
        return this.defaultRank != null;
    }

    
    public Boolean hasInvite(UUID player) {
        return this.invites.contains(player);
    }
    
    public Boolean hasInvite(Player player) {
        return this.invites.contains(player.getUniqueId());
    }


    public void addInvite(Player player) {
        if(this.hasInvite(player)) return;

        this.invites.add(player.getUniqueId());
        FactionsManager.getInstance().inviteManager.createFactionInvite(player, this.id);
    }
    
    public void addInvite(UUID player) {
        if(this.hasInvite(player)) return;
        
        this.invites.add(player);
        FactionsManager.getInstance().inviteManager.createFactionInvite(player, this.id);
    }
    

    public void removeInvite(Player player) {
        if(!this.hasInvite(player)) return;

        this.invites.remove(player.getUniqueId());
        FactionsManager.getInstance().inviteManager.deletePlayerInvite(player, this.id);
    }
    
    public void removeInvite(UUID player) {
        if(!this.hasInvite(player)) return;
        
        this.invites.remove(player);
        FactionsManager.getInstance().inviteManager.deletePlayerInvite(player, this.id);
    }

    
    public void removeAllInvites() {
        for(UUID invite : this.invites) {
            FactionsManager.getInstance().inviteManager.deletePlayerInvite(invite, this.id);
        }

        this.invites.clear();
    }
}
