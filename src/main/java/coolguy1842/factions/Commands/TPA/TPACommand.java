package coolguy1842.factions.Commands.TPA;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.TPARequest;
import coolguy1842.factions.Enums.TPARequestType;
import coolguy1842.factions.Managers.TPAManager;
import net.kyori.adventure.text.Component;

public class TPACommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(args.length <= 0) return false;

            Player requester = (Player)sender;
            Player requested = Bukkit.getPlayerExact(args[0]);
            if(requested == null || requester == requested) return false;

            if(TPAManager.getInstance().getPlayerRequest(requested) != null) {
                requester.sendMessage(Component.text("This player already has a TPA request."));
                return true;
            }
            
            TPARequest request = TPAManager.getInstance().getPlayerRequest(requester);
            if(request != null) TPAManager.getInstance().removePlayerRequest(request);

            TPAManager.getInstance().createPlayerRequest(requester, requested, TPARequestType.TPA, 60L * 20L);

            return true;
        }
        
        return false;
    }
}