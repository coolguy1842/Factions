package coolguy1842.factions.Commands.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

public class BalanceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player)sender;            
            FactionPlayer player = FactionsManager.getInstance().playerManager.getPlayer(p);

            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, p.displayName(), Component.text(" balance $" + player.getMoney()));
            return true;
        }
        
        return false;
    }
}