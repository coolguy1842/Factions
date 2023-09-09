package coolguy1842.factions.Classes;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.PlayerUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class FactionPlayer {
    private UUID id;

    private Faction faction;
    private FactionRank rank;
    private Long money;
    
    private FactionMenu menu;
    
    public FactionPlayer(UUID id, Faction faction, FactionRank rank, Long money) {
        this.id = id;
        
        this.faction = faction;
        this.rank = rank;

        this.money = money;


        this.menu = new FactionMenu(this);
        this.menu.loadMenus();
    }

    public UUID getID() { return this.id; }

    public String getUsername() { return PlayerUtil.getPlayerUsername(this.id); }
    public Boolean isOnline() { return this.getPlayer() != null; }

    public Component getDisplayName() { return PlayerUtil.getPlayerDisplayName(this.id); }
    public String getDisplayNameStr() { return PlainTextComponentSerializer.plainText().serialize(this.getDisplayName()); }
    
    public Faction getFaction() { return this.faction; }
    public FactionRank getRank() { return this.rank; }

    public Long getMoney() { return this.money; }

    public void formatName() {
        if(this.inFaction()) {
            this.faction.formatPlayerName(this);
            return;
        }

        Player p = this.getPlayer();
        if(p == null) return;
        
        p.displayName(p.name());
        p.playerListName(p.displayName());


        for(Player pl : Bukkit.getOnlinePlayers()) {
            if(pl.getUniqueId().equals(p.getUniqueId())) continue;
            pl.unlistPlayer(p);
            pl.listPlayer(p);
        }
    }


    public Boolean isLeader() { 
        if(!inFaction()) return false;
        
        return this.faction.getLeader().equals(this.id);
    }

    public Boolean hasPermission(String permission) {
        if(this.isLeader()) return true;
        if(!this.hasRank()) return false;
        else if(this.rank.hasPermission("all")) return true;

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

        this.formatName();
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
    


    public FactionMenu getMenu() { return this.menu; }
}
