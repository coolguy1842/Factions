package coolguy1842.factions.SubCommands.Factions.InFaction;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum AutoClaimCommandMessages {
    NOTINFACTION,
    NOPERMISSIONS,
    SUCCESS
}

public class FactionAutoClaimCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("You do not have the permissions to claim."),
        Component.text("Toggled autoclaim ")
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[AutoClaimCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }   
        else if(!player.hasPermission("claim")) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[AutoClaimCommandMessages.NOPERMISSIONS.ordinal()]);
            return;
        }

        FactionsManager.getInstance().claimManager.setPlayerHasAutoClaim(p, !FactionsManager.getInstance().claimManager.playerHasAutoClaim(p));

        FactionsManager.getInstance().claimManager.createClaim(p.getChunk(), player.getFaction().getID());
        FactionsMessaging.sendMessage(p, Globals.factionsPrefix, 
                                            commandMessages[AutoClaimCommandMessages.SUCCESS.ordinal()], 
                                            Component.text(FactionsManager.getInstance().claimManager.playerHasAutoClaim(p) ? "on." : "off."));
    }
}
