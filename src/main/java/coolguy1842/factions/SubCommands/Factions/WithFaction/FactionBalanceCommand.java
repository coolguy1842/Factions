package coolguy1842.factions.SubCommands.Factions.WithFaction;

import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.FactionPlayer;
import net.kyori.adventure.text.Component;

enum BalanceCommandMessages {
    NOTINFACTION,
    SUCCESS
}

public class FactionBalanceCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("Faction balance - $"),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            p.sendMessage(commandMessages[BalanceCommandMessages.NOTINFACTION.ordinal()]);

            return;
        }   

        p.sendMessage(commandMessages[BalanceCommandMessages.SUCCESS.ordinal()].append(Component.text(player.getFaction().getMoney())));
    }
}
