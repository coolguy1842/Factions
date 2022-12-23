package coolguy1842.factions.SubCommands.Vault;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Classes.FactionVault;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum VaultRemoveCommandMessages {
    NOPERMISSIONS,
    NOARGS,
    NOTEXISTS,
    SUCCESS
}

public class VaultRemoveCommand {
    private static Component[] commandMessages = {
        Component.text("You do not have the permissions to remove vaults."),
        Component.text("You must specify the name."),
        Component.text("No vault exists with this name."),
        Component.text(" has removed the vault named ")
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.hasPermission("removevault")) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[VaultRemoveCommandMessages.NOPERMISSIONS.ordinal()]);
            return;
        }
        if(args.length <= 2) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[VaultRemoveCommandMessages.NOARGS.ordinal()]);
            return;
        }
        else if(!player.getFaction().hasVault(args[2])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[VaultRemoveCommandMessages.NOTEXISTS.ordinal()]);
            return;
        }

        FactionVault vault = FactionsManager.getInstance().vaultManager.getVault(args[2]);
        vault.dropAllContents(p.getLocation());

        FactionsManager.getInstance().vaultManager.deleteVault(args[2]);

        
        for(FactionPlayer factionPlayer : player.getFaction().players.values()) {
            Player fPlayer = factionPlayer.getPlayer();

            FactionsMessaging.sendMessage(fPlayer, Globals.factionsPrefix, p.displayName(), commandMessages[VaultRemoveCommandMessages.SUCCESS.ordinal()], Component.text(args[2] + "."));
        }
    }
}
