package coolguy1842.factions.SubCommands.Factions.NoFaction;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum DenyCommandMessages {
    INFACTION,
    NOARGS,
    NOINVITE,
    SUCCESS
}
public class FactionDenyCommand {
    private static Component[] commandMessages = {
        Component.text("You are already in a faction."),
        Component.text("You must specify the name of the faction."),
        Component.text("You do not have an invite to this faction."),
        Component.text("Denied invite from "),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[DenyCommandMessages.INFACTION.ordinal()]);
            return;
        }
        else if(args.length <= 1) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[DenyCommandMessages.NOARGS.ordinal()]);
            return;
        }

        Faction faction = FactionsManager.getInstance().factionManager.getFaction(args[1]);
        if(faction == null || !player.hasInvite(faction.getID())) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[DenyCommandMessages.NOINVITE.ordinal()]);
            return;
        }

        FactionsManager.getInstance().inviteManager.deletePlayerInvite(player.getID(), faction.getID());
        FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[DenyCommandMessages.SUCCESS.ordinal()], faction.getFormattedDisplayName(), Component.text("."));
    }
}
