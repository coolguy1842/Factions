
package coolguy1842.factions.Commands.Misc;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player p = (Player)sender;

            if(args.length <= 0) {    
                p.sendMessage(String.format("Your ping is %dms", p.getPing()));

                return true;
            }

            
            Player p2 = Bukkit.getPlayerExact(args[0]);
            if(p2 == null || !p2.isOnline()) {
                p.sendMessage("Player not found.");
                return true;
            }

            p.sendMessage(String.format("%s's ping is %dms", p2.getName(), p2.getPing()));
        } 

        return true;
    }
}
