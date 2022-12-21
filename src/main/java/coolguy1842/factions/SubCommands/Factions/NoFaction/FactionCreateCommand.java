package coolguy1842.factions.SubCommands.Factions.NoFaction;

import java.util.UUID;

import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import net.kyori.adventure.text.Component;

enum CreateCommandMessages {
    NOARGS,
    INFACTION,
    EXISTS,
    SUCCESS
}

public class FactionCreateCommand {
    private static Component[] commandMessages = {
        Component.text("You must specify the name of the faction."),
        Component.text("You are already in a faction."),
        Component.text("That name is already taken."),
        Component.text("Faction created successfully!"),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(args.length <= 1) {
            p.sendMessage(commandMessages[CreateCommandMessages.NOARGS.ordinal()]);
            
            return;
        }
        else if(player.getFaction() != null) {
            p.sendMessage(commandMessages[CreateCommandMessages.INFACTION.ordinal()]);
            
            return;
        }
        else if(FactionsManager.getInstance().factionManager.hasFaction(args[1])) {
            p.sendMessage(commandMessages[CreateCommandMessages.EXISTS.ordinal()]);
            
            return;
        }

        FactionsManager.getInstance().factionManager.createFaction(UUID.randomUUID(), args[1], p, 0L);
        p.sendMessage(commandMessages[CreateCommandMessages.SUCCESS.ordinal()]);
    }
}
