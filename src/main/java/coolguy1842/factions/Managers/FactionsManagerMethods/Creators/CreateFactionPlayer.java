package coolguy1842.factions.Managers.FactionsManagerMethods.Creators;

import java.util.UUID;

import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;

public class CreateFactionPlayer {
    public static FactionPlayer create(FactionsManager manager, UUID id, Long money) {
        if(manager.playerManager.hasPlayer(id)) return manager.playerManager.getPlayer(id);

        manager.database.execute("INSERT INTO players(id, money) VALUES(?, ?)", id.toString(), money);
        return manager.playerManager.loadPlayer(id);
    }

    public static FactionPlayer create(FactionsManager manager, Player player, Long money) {
        return create(manager, player.getUniqueId(), money);
    }
}
