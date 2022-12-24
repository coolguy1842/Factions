package coolguy1842.factions.Managers.FactionsManagerMethods.Loaders;

import java.sql.SQLException;
import java.util.UUID;

import javax.sql.rowset.CachedRowSet;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionVault;
import coolguy1842.factions.Managers.FactionsManager;

public class LoadFactionVaults {
    public static void load(FactionsManager manager) {
        CachedRowSet rows = manager.database.query("SELECT id FROM factionVaults");
        if(rows == null) return;
        else if(rows.size() <= 0) return;

        try {
            while(rows.next()) {
                loadVault(manager, UUID.fromString(rows.getString("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static FactionVault loadVault(FactionsManager manager, UUID id) {
        CachedRowSet rows = manager.database.query("SELECT * FROM factionVaults WHERE id = ?", id.toString());
        if(rows == null) return null;
        else if(rows.size() <= 0) return null;

        try {
            rows.next();

            Faction faction = manager.factionManager.getFaction(UUID.fromString(rows.getString("faction")));

            String displayName = rows.getString("displayName");
            String contents = rows.getString("contents");

            FactionVault vault = new FactionVault(id, faction, displayName, contents);
            manager.vaultManager.vaults.put(id, vault);

            faction.vaults.put(id, vault);
            faction.vaultsByName.put(displayName, vault);

            return vault;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
