package coolguy1842.factions.SubCommands.Factions.InFaction.Homes;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionHome;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

enum RankListCommandMessages {
    NOTINFACTION
}

public class FactionHomeListCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction.")
    };
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankListCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }

        Component out = Component.text("----- ").append(player.getFaction().getFormattedDisplayName()).append(Component.text(" Homes -----\n"));

        if(player.getFaction().homes.size() <= 0) {
            out = out.append(Component.text("None\n"));
            out = out.append(Component.text(StringUtils.repeat("-", "", player.getFaction().getDisplayName().length() + 16)));

            FactionsMessaging.sendMessage(p, out);    
            
            return;
        }

        for(FactionHome home : player.getFaction().homes.values()) {
            TextColor homeColor;
            
            switch(home.getLocation().getWorld().getEnvironment()) {
            case NORMAL:
                homeColor = TextColor.color(85, 255, 85);
                break;
            case NETHER:
                homeColor = TextColor.color(255, 87, 51);
                break;
            case THE_END:
                homeColor = TextColor.color(170, 0, 170);
                break;
            default:
                homeColor = TextColor.color(255, 255, 255);
                break;
            }
            
            out = out.append(Component.text(String.format("%s - %d, %d, %d\n", home.getDisplayName(), 
                (Long)Math.round(home.getLocation().getX()), (Long)Math.round(home.getLocation().getY()), (Long)Math.round(home.getLocation().getZ())))
                .color(homeColor));
        }

        out = out.append(Component.text(StringUtils.repeat("-", "", player.getFaction().getDisplayName().length() + 16)));
        FactionsMessaging.sendMessage(p, out);
    }
}
