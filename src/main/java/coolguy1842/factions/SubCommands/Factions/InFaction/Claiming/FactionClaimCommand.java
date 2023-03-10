package coolguy1842.factions.SubCommands.Factions.InFaction.Claiming;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum ClaimCommandMessages {
    NOTINFACTION,
    NOPERMISSIONS,
    CLAIMED,
    SUCCESS
}

public class FactionClaimCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("You do not have the permissions to claim."),
        Component.text("This chunk is already claimed."),
        Component.text("Claimed chunk successfully.")
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[ClaimCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }   
        else if(!player.hasPermission("claim")) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[ClaimCommandMessages.NOPERMISSIONS.ordinal()]);
            return;
        }
        else if(FactionsManager.getInstance().claimManager.hasClaim(p.getChunk())) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[ClaimCommandMessages.CLAIMED.ordinal()]);
            return;
        }

        FactionsManager.getInstance().claimManager.createClaim(p.getChunk(), player.getFaction().getID());
        FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[ClaimCommandMessages.SUCCESS.ordinal()]);
    }
}
