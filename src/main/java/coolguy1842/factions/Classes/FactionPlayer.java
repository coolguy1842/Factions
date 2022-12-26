package coolguy1842.factions.Classes;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.PlayerUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

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

    public String getUsername() { return PlayerUtil.getPlayerUsername(this.id); }
    public Boolean isOnline() { return this.getPlayer() != null; }

    public Component getDisplayName() { return PlayerUtil.getPlayerDisplayName(this.id); }
    public String getDisplayNameStr() { return ((TextComponent)PlayerUtil.getPlayerDisplayName(this.id)).content(); }
    
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


    public Player getPlayer() { return Bukkit.getPlayer(this.id); }


    public void setMoney(Long money) { 
        this.money = money;

        FactionsManager.getInstance().playerManager.setPlayerMoney(this.id, money);
    }

    public void setRank(UUID rankID) { 
        if(this.hasRank()) this.rank.players.remove(this.id);

        this.rank = FactionsManager.getInstance().rankManager.getRank(rankID);
        if(this.hasRank()) this.rank.players.put(this.id, this);

        FactionsManager.getInstance().playerManager.setPlayerRank(this.id, rankID);
    }
    
    public void setFaction(UUID factionID) { 
        if(this.inFaction()) this.faction.players.remove(this.id);

        this.faction = FactionsManager.getInstance().factionManager.getFaction(factionID);
        if(this.inFaction()) this.faction.players.put(this.id, this);

        FactionsManager.getInstance().playerManager.setPlayerFaction(this.id, factionID);
    }

    
    public Boolean hasInvite(UUID faction) {
        return FactionsManager.getInstance().inviteManager.playerHasInvite(this.id, faction);
    }


    public void addInvite(UUID faction) {
        FactionsManager.getInstance().inviteManager.createFactionInvite(this.id, faction);
    }
    
    public void removeInvite(UUID faction) {
        FactionsManager.getInstance().inviteManager.deletePlayerInvite(this.id, faction);
    }
    

    public void removeAllInvites() {
        FactionsManager.getInstance().inviteManager.deleteAllPlayerInvites(this.id);
    }
    
}
