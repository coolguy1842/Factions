package coolguy1842.factions.Managers.FactionsManagers;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Managers.FactionsManagerMethods.Booleans.HasFactionPlayer;
import coolguy1842.factions.Managers.FactionsManagerMethods.Creators.CreateFactionPlayer;
import coolguy1842.factions.Managers.FactionsManagerMethods.Getters.GetFactionPlayer;
import coolguy1842.factions.Managers.FactionsManagerMethods.Loaders.LoadFactionPlayers;
import coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionPlayer.SetFactionPlayerFaction;
import coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionPlayer.SetFactionPlayerMoney;
import coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionPlayer.SetFactionPlayerRank;

public class PlayerManager {
    public HashMap<UUID, FactionPlayer> players;

    public PlayerManager() {
        this.players = new HashMap<>();
    }

    public void close() {
        players.clear();
        players = null;
    }
    

    public void loadPlayers() { LoadFactionPlayers.load(FactionsManager.getInstance()); }
    
    public FactionPlayer loadPlayer(Player player) { return LoadFactionPlayers.loadPlayer(FactionsManager.getInstance(), player.getUniqueId()); }
    public FactionPlayer loadPlayer(UUID id) { return LoadFactionPlayers.loadPlayer(FactionsManager.getInstance(), id); }

    
    public Boolean hasPlayer(UUID id) { return HasFactionPlayer.has(FactionsManager.getInstance(), id); }
    
    public FactionPlayer getPlayer(UUID id) { return GetFactionPlayer.get(FactionsManager.getInstance(), id); }
    public FactionPlayer getPlayer(Player player) { return GetFactionPlayer.get(FactionsManager.getInstance(), player.getUniqueId()); }
    

    public FactionPlayer createPlayer(Player player, Long money) { return CreateFactionPlayer.create(FactionsManager.getInstance(), player.getUniqueId(), money);  }
    public FactionPlayer createPlayer(UUID id, Long money) { return CreateFactionPlayer.create(FactionsManager.getInstance(), id, money);  }

    
    public void setPlayerMoney(Player player, Long money) { SetFactionPlayerMoney.set(FactionsManager.getInstance(), player.getUniqueId(), money); }
    public void setPlayerMoney(UUID id, Long money) { SetFactionPlayerMoney.set(FactionsManager.getInstance(), id, money); }
    
    public void setPlayerFaction(Player player, UUID factionID) { SetFactionPlayerFaction.set(FactionsManager.getInstance(), player.getUniqueId(), factionID); }
    public void setPlayerFaction(UUID id, UUID factionID) { SetFactionPlayerFaction.set(FactionsManager.getInstance(), id, factionID); }
    
    public void setPlayerRank(Player player, UUID rankID) { SetFactionPlayerRank.set(FactionsManager.getInstance(), player.getUniqueId(), rankID); }
    public void setPlayerRank(UUID id, UUID rankID) { SetFactionPlayerRank.set(FactionsManager.getInstance(), id, rankID); }

}
