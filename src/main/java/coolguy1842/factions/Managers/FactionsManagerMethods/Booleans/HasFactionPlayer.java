package coolguy1842.factions.Managers.FactionsManagerMethods.Booleans;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class HasFactionPlayer {
    public static Boolean has(FactionsManager manager, UUID id) {
        return manager.playerManager.players.containsKey(id);
    }
}
