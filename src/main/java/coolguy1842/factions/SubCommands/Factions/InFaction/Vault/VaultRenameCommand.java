package coolguy1842.factions.SubCommands.Factions.InFaction.Vault;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum VaultRenameCommandMessages {
    NOPERMISSIONS,
    NOARGS,
    NONEWNAME,
    NOTEXISTS,
    NEWEXISTS,
    SUCCESS
}

public class VaultRenameCommand {
    private static Component[] commandMessages = {
        Component.text("You do not have the permissions to rename vaults."),
        Component.text("You must specify the current name."),
        Component.text("You must specify the new name."),
        Component.text("No vault exists with this name."),
        Component.text("A vault with the new name already exists."),
        Component.text(" has renamed the vault from ")
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.hasPermission("removevault")) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[VaultRenameCommandMessages.NOPERMISSIONS.ordinal()]);
            return;
        }
        if(args.length <= 2) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[VaultRenameCommandMessages.NOARGS.ordinal()]);
            return;
        }
        else if(!player.getFaction().hasVault(args[2])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[VaultRenameCommandMessages.NOTEXISTS.ordinal()]);
            return;
        }
        if(args.length <= 3) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[VaultRenameCommandMessages.NONEWNAME.ordinal()]);
            return;
        }
        else if(player.getFaction().hasVault(args[3])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[VaultRenameCommandMessages.NEWEXISTS.ordinal()]);
            return;
        }

        player.getFaction().getVault(args[2]).setDisplayName(args[3]);
        
        for(FactionPlayer factionPlayer : player.getFaction().players.values()) {
            Player fPlayer = factionPlayer.getPlayer();

            FactionsMessaging.sendMessage(fPlayer, Globals.factionsPrefix, p.name(), commandMessages[VaultRenameCommandMessages.SUCCESS.ordinal()], 
            Component.text(args[2] + " to " + args[3]));
        }
    }
}
