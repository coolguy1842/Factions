package coolguy1842.factions.Commands.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import coolguy1842.discordrelay.Globals;

public class HubCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player)sender;            
            
            p.getServer().getMessenger().registerOutgoingPluginChannel(Globals.plugin, "BungeeCord");

            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("hub");
            p.sendPluginMessage(Globals.plugin, "BungeeCord", out.toByteArray());
            
            return true;
        }
        
        return false;
    }
}