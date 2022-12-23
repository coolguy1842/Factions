package coolguy1842.factions.Managers.FactionsManagerMethods.Getters;

import java.util.UUID;

import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;

public class GetFactionPlayer {
    public static FactionPlayer get(FactionsManager manager, UUID id) {
        if(manager.playerManager.hasPlayer(id)) return manager.playerManager.players.get(id);
        return null;
    }
}
