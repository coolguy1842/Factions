package coolguy1842.factions.SubCommands.Factions.WithFaction;

import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.FactionPlayer;
import net.kyori.adventure.text.Component;

enum RenameCommandMessages {
    NOTINFACTION,
    NOPERMISSIONS,
    NOARGS,
    SUCCESS
}

public class FactionRenameCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("You do not have the permissions to rename this faction."),
        Component.text("You must specify a new name."),
        Component.text("Faction renamed successfully!"),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(player.getFaction() == null) {
            p.sendMessage(commandMessages[RenameCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }
        else if(!player.hasPermission("rename")) {
            p.sendMessage(commandMessages[RenameCommandMessages.NOPERMISSIONS.ordinal()]);
            return;
        }
        else if(args.length <= 1) {
            p.sendMessage(commandMessages[RenameCommandMessages.NOARGS.ordinal()]);
            return;
        }

        player.getFaction().setDisplayName(args[1]);
        p.sendMessage(commandMessages[RenameCommandMessages.SUCCESS.ordinal()]);
    }
}
