package coolguy1842.factions.Managers.FactionsManagerMethods.Getters;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import javax.sql.rowset.CachedRowSet;

import coolguy1842.factions.Managers.FactionsManager;

public class GetFactionOptions {
    public GetFactionOptions() {}

    public static HashMap<String, String> get(FactionsManager manager, UUID factionID) {
        HashMap<String, String> options = new HashMap<>();

        CachedRowSet rows = manager.database.query("SELECT * FROM factionOptions WHERE faction = ?", factionID.toString());
        if(rows == null) return options;
        else if(rows.size() <= 0) return options;

        try {
            while(rows.next()) {
                options.put(rows.getString("option"), rows.getString("value"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return options;
    }
}
