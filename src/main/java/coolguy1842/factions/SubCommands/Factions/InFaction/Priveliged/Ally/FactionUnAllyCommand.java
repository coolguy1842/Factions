package coolguy1842.factions.SubCommands.Factions.InFaction.Priveliged.Ally;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum UnAllyCommandMessages {
    NOTINFACTION,
    NOTLEADER,
    NOARGS,
    NOTEXISTS,
    OWNFACTION,
    NOTALLIED,
    SUCCESS
}

public class FactionUnAllyCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("You do not have the permissions to do this."),
        Component.text("You must specify the faction to ally with."),
        Component.text("That faction does not exist."),
        Component.text("You cannot unally your own faction."),
        Component.text("You are not allied with this faction."),
        Component.text("\" is no longer allied with \""),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[UnAllyCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }
        else if(!player.isLeader()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[UnAllyCommandMessages.NOTLEADER.ordinal()]);
            return;
        }
        else if(args.length <= 1) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[UnAllyCommandMessages.NOARGS.ordinal()]);
            return;
        }
        else if(!FactionsManager.getInstance().factionManager.hasFaction(args[1])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[UnAllyCommandMessages.NOTEXISTS.ordinal()]);
            return;
        }

        Faction mentionedFaction = FactionsManager.getInstance().factionManager.getFaction(args[1]);
        if(mentionedFaction.getID().equals(player.getFaction().getID())) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[UnAllyCommandMessages.OWNFACTION.ordinal()]);
            return;
        }
        else if(!mentionedFaction.isAllied(player.getFaction())) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[UnAllyCommandMessages.NOTALLIED.ordinal()]);
            return;
        }


        player.getFaction().unally(mentionedFaction);
    }
}
