package coolguy1842.factions.SubCommands.Factions.InFaction.Priveliged;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Classes.FactionVault;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum DisbandCommandMessages {
    NOTINFACTION,
    NOTLEADER
}

public class FactionDisbandCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("You are not the leader; you can leave instead.")
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[DisbandCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }
        else if(!player.isLeader()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[DisbandCommandMessages.NOTLEADER.ordinal()]);
            return;
        }

        for(FactionVault vault : player.getFaction().vaults.values()) {
            vault.dropAllContents(p.getLocation());
        }

        FactionsMessaging.broadcastMessage(Globals.factionsPrefix, player.getFaction().getFormattedDisplayName(), Component.text(" has been disbanded."));
        FactionsManager.getInstance().factionManager.deleteFaction(player.getFaction());
    }
}
