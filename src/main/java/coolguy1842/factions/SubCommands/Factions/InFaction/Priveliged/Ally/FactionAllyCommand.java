package coolguy1842.factions.SubCommands.Factions.InFaction.Priveliged.Ally;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum AllyCommandMessages {
    NOTINFACTION,
    NOTLEADER,
    NOARGS,
    NOTEXISTS,
    OWNFACTION,
    ALREADYALLIED,
    ALREADYINVITED
}

public class FactionAllyCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("You do not have the permissions to do this."),
        Component.text("You must specify the faction to ally with."),
        Component.text("That faction does not exist."),
        Component.text("You cannot ally your own faction."),
        Component.text("You have already are allied with this faction."),
        Component.text("You have already sent an ally invite to this faction."),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[AllyCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }
        else if(!player.isLeader()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[AllyCommandMessages.NOTLEADER.ordinal()]);
            return;
        }
        else if(args.length <= 1) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[AllyCommandMessages.NOARGS.ordinal()]);
            return;
        }
        else if(!FactionsManager.getInstance().factionManager.hasFaction(args[1])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[AllyCommandMessages.NOTEXISTS.ordinal()]);
            return;
        }

        Faction mentionedFaction = FactionsManager.getInstance().factionManager.getFaction(args[1]);
        if(mentionedFaction.getID().equals(player.getFaction().getID())) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[AllyCommandMessages.OWNFACTION.ordinal()]);
            return;
        }
        else if(mentionedFaction.isAllied(player.getFaction())) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[AllyCommandMessages.ALREADYALLIED.ordinal()]);
            return;
        }
        else if(mentionedFaction.hasAllyInvite(player.getFaction())) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[AllyCommandMessages.ALREADYINVITED.ordinal()]);
            return;
        }

        if(player.getFaction().hasAllyInvite(mentionedFaction)) {
            player.getFaction().acceptAllyInvite(mentionedFaction);

            return;
        }
        

        mentionedFaction.addAllyInvite(player.getFaction());
    }
}
