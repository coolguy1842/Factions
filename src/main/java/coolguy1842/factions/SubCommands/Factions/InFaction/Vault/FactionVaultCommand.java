package coolguy1842.factions.SubCommands.Factions.InFaction.Vault;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum VaultCommandMessages {
    NOTINFACTION,
    NODEFAULT,
    WRONGARG
}

public class FactionVaultCommand {    
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("No vault named \"vault\"(default) exists."),
        Component.text("Invalid option.")
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[VaultCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }
        else if(args.length <= 1) {
            if(player.getFaction().hasVault("vault")) {        
                p.openInventory(player.getFaction().getVault("vault").getInventory());
                return;
            }

            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[VaultCommandMessages.NODEFAULT.ordinal()]);
            return;
        }
        
        switch(args[1]) {
        case "create":
            VaultCreateCommand.execute(p, player, args);
            break;
        case "open":
            VaultOpenCommand.execute(p, player, args);
            break;
        case "remove":
            VaultRemoveCommand.execute(p, player, args);
            break;
        case "rename":
            VaultRenameCommand.execute(p, player, args);
            break;
        default:
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[VaultCommandMessages.WRONGARG.ordinal()]);
            return;
        }

    }
}
