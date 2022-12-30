package coolguy1842.factions.SubCommands.Factions.InFaction.Priveliged.Ally;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum DeclineAllyCommandMessages {
    NOTINFACTION,
    NOTLEADER,
    NOARGS,
    NOTEXISTS,
    OWNFACTION,
    NOTINVITED,
    SUCCESSTOINVITER,
    SUCCESS
}

public class FactionDeclineAllyCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("You do not have the permissions to do this."),
        Component.text("You must specify the faction to decline the ally invite from."),
        Component.text("That faction does not exist."),
        Component.text("You cannot decline an ally invite from your own faction."),
        Component.text("You do not have an invite from this faction."),
        Component.text("\" declined your faction ally invite."),
        Component.text(" has declined the ally invite to \""),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[DeclineAllyCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }
        else if(!player.isLeader()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[DeclineAllyCommandMessages.NOTLEADER.ordinal()]);
            return;
        }
        else if(args.length <= 1) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[DeclineAllyCommandMessages.NOARGS.ordinal()]);
            return;
        }
        else if(!FactionsManager.getInstance().factionManager.hasFaction(args[1])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[DeclineAllyCommandMessages.NOTEXISTS.ordinal()]);
            return;
        }

        Faction mentionedFaction = FactionsManager.getInstance().factionManager.getFaction(args[1]);
        if(mentionedFaction.getID().equals(player.getFaction().getID())) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[DeclineAllyCommandMessages.OWNFACTION.ordinal()]);
            return;
        }
        else if(!player.getFaction().hasAllyInvite(mentionedFaction)) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[DeclineAllyCommandMessages.NOTINVITED.ordinal()]);
            return;
        }

        FactionsManager.getInstance().factionManager.deleteFactionAllyInvite(mentionedFaction, player.getFaction());

        player.getFaction().broadcastMessage(Globals.factionsPrefix, 
                                            p.name(), 
                                            commandMessages[DeclineAllyCommandMessages.SUCCESS.ordinal()], 
                                            mentionedFaction.getFormattedDisplayName(),
                                            Component.text("\"."));
                                            
        mentionedFaction.broadcastMessage(Globals.factionsPrefix, 
                                        Component.text("\""),
                                        player.getFaction().getFormattedDisplayName(),
                                        commandMessages[DeclineAllyCommandMessages.SUCCESSTOINVITER.ordinal()]);
    }
}
