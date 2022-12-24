package coolguy1842.factions.Managers.FactionsManagerMethods.Deleters;

import java.util.List;
import java.util.UUID;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionHome;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Classes.FactionRank;
import coolguy1842.factions.Classes.FactionVault;
import coolguy1842.factions.Managers.FactionsManager;

public class DeleteFaction {
    public static void delete(FactionsManager manager, UUID id) {
        if(!manager.factionManager.hasFaction(id)) return;

        Faction faction = manager.factionManager.getFaction(id);
        
        for(FactionRank rank : faction.ranks.values()) {
            manager.rankManager.deleteRank(rank);
        }
        
        for(FactionVault vault : faction.vaults.values().stream().toList()) {
            manager.vaultManager.deleteVault(vault.getID());
        }
        
        for(FactionHome home : faction.homes.values().stream().toList()) {
            manager.homeManager.deleteHome(home.getID());
        }
        

        List<FactionPlayer> players = faction.players.values().stream().toList();
        for(FactionPlayer player : players) {
            player.setFaction(null);
        }
        
        faction.removeAllInvites();

        manager.database.execute("DELETE FROM factions WHERE id = ?", id.toString());
        manager.database.execute("DELETE FROM factionOptions WHERE faction = ?", id.toString());
        manager.database.execute("DELETE FROM factionInvites WHERE faction = ?", id.toString());
        manager.database.execute("DELETE FROM factionVaults WHERE faction = ?", id.toString());
        manager.database.execute("DELETE FROM factionHomes WHERE faction = ?", id.toString());
        
        manager.factionManager.factionsNameLookup.remove(faction.getDisplayName());
        manager.factionManager.factions.remove(id);
    }
}
