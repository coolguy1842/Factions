package coolguy1842.factions.SubCommands.Vault;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.Base64Util;
import coolguy1842.factions.Util.FactionsMessaging;
import coolguy1842.factions.Util.InventoryUtil;
import net.kyori.adventure.text.Component;

enum VaultCreateCommandMessages {
    NOPERMISSIONS,
    NOARGS,
    EXISTS,
    SUCCESS
}

public class VaultCreateCommand {
    private static Component[] commandMessages = {
        Component.text("You do not have the permissions to create a vault."),
        Component.text("You must specify the name."),
        Component.text("A vault already exists with this name."),
        Component.text(" has created a vault named ")
    }; 
    
    private static String emptyVaultStr = Base64Util.strToBase64(InventoryUtil.serializeInventory(Bukkit.createInventory(null, 54)));

    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.hasPermission("createvault")) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[VaultCreateCommandMessages.NOPERMISSIONS.ordinal()]);
            return;
        }
        if(args.length <= 2) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[VaultCreateCommandMessages.NOARGS.ordinal()]);
            return;
        }
        else if(player.getFaction().hasVault(args[2])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[VaultCreateCommandMessages.EXISTS.ordinal()]);
            return;
        }

        FactionsManager.getInstance().vaultManager.createVault(UUID.randomUUID(), player.getFaction().getID(), args[2], emptyVaultStr);

        for(FactionPlayer factionPlayer : player.getFaction().players.values()) {
            Player fPlayer = factionPlayer.getPlayer();

            FactionsMessaging.sendMessage(fPlayer, Globals.factionsPrefix, p.displayName(), commandMessages[VaultCreateCommandMessages.SUCCESS.ordinal()], Component.text(args[2] + "."));
        }
    }
}
