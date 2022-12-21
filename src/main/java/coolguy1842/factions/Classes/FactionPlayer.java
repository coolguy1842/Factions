package coolguy1842.factions.Classes;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class FactionPlayer {
    private UUID id;

    private Faction faction;
    private FactionRank rank;
    private Long money;
    
    public FactionPlayer(UUID id, Faction faction, FactionRank rank, Long money) {
        this.id = id;
        
        this.faction = faction;
        this.rank = rank;

        this.money = money;
    }

    public UUID getID() { return this.id; }
    
    public Faction getFaction() { return this.faction; }
    public FactionRank getRank() { return this.rank; }

    public Long getMoney() { return this.money; }


    public Boolean isLeader() { 
        if(!inFaction()) return false;
        
        return this.faction.getLeader().equals(this.id);
    }

    public Boolean hasPermission(String permission) {
        if(this.isLeader()) return true;
        if(!this.hasRank()) return false;

        return this.rank.hasPermission(permission);
    }

    public Boolean inFaction() { return this.faction != null; } 
    public Boolean hasRank() { return this.rank != null; } 


    public void setMoney(Long money) { 
        this.money = money;

        FactionsManager.getInstance().playerManager.setPlayerMoney(this.id, money);
    }

    public void setRank(UUID rankID) { 
        FactionRank rank = FactionsManager.getInstance().rankManager.getRank(rankID);

        this.rank = rank;
        FactionsManager.getInstance().playerManager.setPlayerRank(this.id, rankID);;
    }
    
    public void setFaction(UUID factionID) { 
        Faction faction = FactionsManager.getInstance().factionManager.getFaction(factionID);

        this.faction = faction;
        FactionsManager.getInstance().playerManager.setPlayerFaction(this.id, factionID);
    }

    
    public void addInvite(UUID faction) {

    }
    
    public void removeInvite(UUID faction) {

    }
    
}
