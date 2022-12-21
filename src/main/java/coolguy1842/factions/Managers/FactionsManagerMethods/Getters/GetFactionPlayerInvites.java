package coolguy1842.factions.Managers.FactionsManagerMethods.Getters;

import java.util.ArrayList;
import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class GetFactionPlayerInvites {
    public static ArrayList<UUID> get(FactionsManager manager, UUID id) {
        if(manager.inviteManager.invites.containsKey(id)) return manager.inviteManager.invites.get(id);

        return new ArrayList<>();
    }
}
