package coolguy1842.factions.SubCommands.Factions.InFaction.Vault;

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
    EXISTS,
    NOTENOUGHMONEY,
    SUCCESS
}

public class VaultCreateCommand {
    private static Component[] commandMessages = {
        Component.text("You do not have the permissions to create a vault."),
        Component.text("A vault already exists with this name."),
        Component.text("Your faction does not have enough money - $"),
        Component.text(" created a vault named \"")
    }; 
    
    private static String emptyVaultStr = Base64Util.strToBase64(InventoryUtil.serializeInventory(Bukkit.createInventory(null, 54)));

    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.hasPermission("createvault")) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[VaultCreateCommandMessages.NOPERMISSIONS.ordinal()]);
            return;
        }

        String vaultName = "vault";
        if(args.length > 2) {
            vaultName = args[2];
        }
        
        if(player.getFaction().hasVault(vaultName)) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[VaultCreateCommandMessages.EXISTS.ordinal()]);
            return;
        }

        Long money = player.getFaction().vaults.size() * 30000L;
        if(money > player.getFaction().getMoney()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, 
                                            commandMessages[VaultCreateCommandMessages.NOTENOUGHMONEY.ordinal()], 
                                            Component.text(money - player.getFaction().getMoney() + "/" + money + " required."));
            return;
        }

        player.getFaction().setMoney(player.getFaction().getMoney() - money);
        FactionsManager.getInstance().vaultManager.createVault(UUID.randomUUID(), player.getFaction().getID(), vaultName, emptyVaultStr);

        player.getFaction().broadcastMessage(Globals.factionsPrefix, p.name(), commandMessages[VaultCreateCommandMessages.SUCCESS.ordinal()], Component.text(vaultName + "\"."));
    }
}
