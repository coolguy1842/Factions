package coolguy1842.factions.SubCommands.Factions.NoFaction;

import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import net.kyori.adventure.text.Component;

enum AcceptCommandMessages {
    INFACTION,
    NOARGS,
    NOINVITE,
    SUCCESS
}
public class FactionAcceptCommand {
    
    private static Component[] commandMessages = {
        Component.text("You are already in a faction."),
        Component.text("You must specify the name of the faction."),
        Component.text("You do not have an invite to this faction."),
        Component.text("Joined faction successfully!"),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(player.getFaction() != null) {
            p.sendMessage(commandMessages[AcceptCommandMessages.INFACTION.ordinal()]);
            return;
        }
        else if(args.length <= 1) {
            p.sendMessage(commandMessages[AcceptCommandMessages.NOARGS.ordinal()]);
            return;
        }
        else if(!FactionsManager.getInstance().factionManager.hasFaction(args[1])) {
            p.sendMessage(commandMessages[AcceptCommandMessages.NOINVITE.ordinal()]);
            return;
        }
        
        Faction faction = FactionsManager.getInstance().factionManager.getFaction(args[1]);
        if(!FactionsManager.getInstance().inviteManager.playerHasInvite(p, faction.getID())) {
            p.sendMessage(commandMessages[AcceptCommandMessages.NOINVITE.ordinal()]);
            return;
        }

        FactionsManager.getInstance().inviteManager.deleteAllPlayerInvites(p);
        
        player.setFaction(faction.getID());
        if(faction.getDefaultRank() != null) player.setRank(faction.getDefaultRank().getID());
        
        p.sendMessage(commandMessages[AcceptCommandMessages.SUCCESS.ordinal()]);
    }
}
