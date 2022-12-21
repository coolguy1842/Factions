package coolguy1842.factions.Classes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    public Faction(UUID id, String displayName, UUID leader, Long money) {
        this.id = id;
        
        this.displayName = displayName;
        this.leader = leader;

        this.money = money;
        this.players = new HashMap<>();

        this.options = FactionsManager.getInstance().factionManager.getFactionOptions(this.id);
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
        this.displayName = displayName;
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
}
