package coolguy1842.factions.SubCommands.Factions.InFaction.Priveliged.Set;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

enum SetColorCommandMessages {
    NOPERMISSIONS,
    INVALIDVALUE,

    SUCCESS
}

public class SetColorCommand {
    private static Component[] commandMessages = {
        Component.text("You do not have the permissions to set the color."),
        Component.text("Invalid value. (valid is \"0-255,0-255,0-255\")"),

        Component.text(" has set the color to "),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.hasPermission("setcolor")) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[SetColorCommandMessages.NOPERMISSIONS.ordinal()]);
            return;
        }

        String[] splitStr = args[2].split(",");
        if(splitStr.length != 3) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[SetColorCommandMessages.INVALIDVALUE.ordinal()]);
            return;
        }

        int red = Integer.parseInt(splitStr[0]);
        int green = Integer.parseInt(splitStr[1]);
        int blue = Integer.parseInt(splitStr[2]);
        if((red < 0 || red > 255) || (green < 0 || green > 255) || (blue < 0 || blue > 255)) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[SetColorCommandMessages.INVALIDVALUE.ordinal()]);
            return;
        }

        player.getFaction().setOption("color", args[2]);
        
        FactionsMessaging.sendMessage(p, Globals.factionsPrefix, 
                                        p.name(),
                                        commandMessages[SetColorCommandMessages.SUCCESS.ordinal()],
                                        Component.text(args[2]).color(TextColor.color(red, green, blue)));

        player.getFaction().loadColor();
    }
}
