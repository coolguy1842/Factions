package coolguy1842.factions.Commands.Misc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import coolguy1842.factions.Managers.FactionsManager;
import net.kyori.adventure.text.Component;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

public class QueryCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player)sender;

            if(!p.isOp()) return false;
            if(args.length <= 0) return false;

            String sql = String.join(" ", args);

            CachedRowSet rows = FactionsManager.getInstance().database.query(sql);
            if(rows == null) return true;
            else if(rows.size() <= 0) return true;

            try {
                ResultSetMetaData meta = rows.getMetaData();

                int row = 0;
                while(rows.next()) {
                    p.sendMessage(Component.text("Row #" + row++ + ":"));
                    for(int i = 1; i <= meta.getColumnCount(); i++) {
                        Component message = Component.text(meta.getColumnName(i) + ": ");
                        Object col = rows.getObject(i);
                        
                        if(col == null) {
                            message = message.append(Component.text("null"));
                        }
                        else {
                            message = message.append(Component.text(col.toString()));
                        }

                        p.sendMessage(message);            
                    }

                    p.sendMessage(Component.text(""));
                }

                rows.close();
            } 
            catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }
        
        return false;
    }
}
