package coolguy1842.factions.SubCommands.Factions.InFaction.Vault;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum VaultOpenCommandMessages {
    NOPERMISSIONS,
    NOARGS,
    NOTEXISTS
}

public class VaultOpenCommand {
    private static Component[] commandMessages = {
        Component.text("You do not have the permissions to open vaults."),
        Component.text("You must specify the name."),
        Component.text("No vault exists with this name.")
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.hasPermission("viewvault")) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[VaultOpenCommandMessages.NOPERMISSIONS.ordinal()]);
            return;
        }
        if(args.length <= 2) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[VaultOpenCommandMessages.NOARGS.ordinal()]);
            return;
        }
        else if(!player.getFaction().hasVault(args[2])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[VaultOpenCommandMessages.NOTEXISTS.ordinal()]);
            return;
        }


        p.openInventory(player.getFaction().getVault(args[2]).getInventory());
    }
}
