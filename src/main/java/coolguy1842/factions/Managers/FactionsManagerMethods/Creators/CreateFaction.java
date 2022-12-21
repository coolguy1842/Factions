package coolguy1842.factions.Managers.FactionsManagerMethods.Creators;

import java.util.UUID;

import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;

public class CreateFaction {
    public static Faction create(FactionsManager manager, UUID id, String displayName, Player leader, Long money) {
        if(manager.factionManager.hasFaction(id)) return manager.factionManager.factions.get(id);

        manager.database.execute("INSERT INTO factions(id, displayName, leader, money) VALUES(?, ?, ?, ?)", 
            id.toString(), displayName, leader.getUniqueId().toString(), money);
       
        Faction faction = manager.factionManager.loadFaction(id);
        
        FactionPlayer player = manager.playerManager.getPlayer(leader.getUniqueId());
        player.setFaction(id);
        player.setRank(null);
        
        return faction;
    }
}
