package coolguy1842.factions.SubCommands.Factions.InFaction.Priveliged.Ally;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;

enum AllyCommandMessages {
    NOTINFACTION,
    NOTLEADER,
    NOARGS,
    NOTEXISTS,
    OWNFACTION,
    ALREADYALLIED,
    ALREADYINVITED,
    ACCEPTEDINVITE,
    SUCCESS
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
        Component.text("\" is now allied with \""),
        Component.text(" has invited \"")
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
            Component[] message = { Globals.factionsPrefix, 
                Component.text("\""),
                player.getFaction().getFormattedDisplayName(), 
                commandMessages[AllyCommandMessages.ACCEPTEDINVITE.ordinal()], 
                mentionedFaction.getFormattedDisplayName(),
                Component.text("\".")};
            
            
            FactionsMessaging.broadcastMessage(null, message);
            FactionsMessaging.sendToDiscord("[Factions]", null, message);

            FactionsManager.getInstance().factionManager.deleteFactionAllyInvite(mentionedFaction, player.getFaction());
            FactionsManager.getInstance().factionManager.createFactionAlly(mentionedFaction, player.getFaction());

            return;
        }
        
        Component[] message = { Globals.factionsPrefix, 
                                p.name(), 
                                commandMessages[AllyCommandMessages.SUCCESS.ordinal()], 
                                mentionedFaction.getFormattedDisplayName(),
                                Component.text("\" to be allies.")};
                                
        Component[] commandMessage = {
            Component.text("[Accept] ")
                                            .color(TextColor.color(0, 255, 0))
                                            .clickEvent(ClickEvent.runCommand("/f ally " + player.getFaction().getDisplayName()))
                                            .hoverEvent(
                                                HoverEvent.showText(Component.text("Accept \"")
                                                                    .append(player.getFaction().getFormattedDisplayName())
                                                                    .append(Component.text("\" to be your allies?")))
                                            ),
            Component.text("[Decline]")
                                            .color(TextColor.color(255, 0, 0))
                                            .clickEvent(ClickEvent.runCommand("/f declineally " + player.getFaction().getDisplayName()))
                                            .hoverEvent(
                                                HoverEvent.showText(Component.text("Decline \"")
                                                                    .append(player.getFaction().getFormattedDisplayName())
                                                                    .append(Component.text("\" from being your allies?"))
                                            ))
        };

        FactionsManager.getInstance().factionManager.createFactionAllyInvite(player.getFaction(), mentionedFaction);

        player.getFaction().broadcastMessage(message);
        mentionedFaction.broadcastMessage(message);

        FactionsMessaging.sendMessage(mentionedFaction.getLeader(), commandMessage);
    }
}
