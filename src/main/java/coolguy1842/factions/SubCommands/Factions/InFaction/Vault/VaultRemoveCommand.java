package coolguy1842.factions.SubCommands.Factions.InFaction.Vault;

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
        Component.text(" removed the vault named \"")
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

        FactionVault vault = player.getFaction().getVault(args[2]);
        vault.dropAllContents(p.getLocation());

        FactionsManager.getInstance().vaultManager.deleteVault(vault.getID());
        Long money = player.getFaction().vaults.size() * 30000L;

        player.getFaction().setMoney(player.getFaction().getMoney() + money);
        player.getFaction().broadcastMessage(Globals.factionsPrefix, p.name(), commandMessages[VaultRemoveCommandMessages.SUCCESS.ordinal()], Component.text(args[2] + "\"."));
    }
}
