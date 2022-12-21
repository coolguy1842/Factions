package coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionPlayer;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class SetFactionPlayerMoney {
    public static void set(FactionsManager manager, UUID id, Long money) {
        if(!manager.playerManager.hasPlayer(id)) return;

        manager.database.execute("UPDATE players SET money = ? WHERE id = ?", money, id.toString());
    }
}
