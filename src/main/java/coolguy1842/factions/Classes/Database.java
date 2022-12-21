package coolguy1842.factions.Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import coolguy1842.factions.Util.FactionsLogger;

public class Database {
    String path;
    Connection con;

    private void connect() {
        try {
            // db parameters
            String url = "jdbc:sqlite:" + this.path;
            // create a connection to the database
            con = DriverManager.getConnection(url);
            
            FactionsLogger.info("Connection to SQLite has been established.");
        } 
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if(con != null) {
            try {
                con.close();
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Database(String path) {
        this.path = path;
        this.con = null;
        
        this.connect();
    }

    public void execute(String sql, Object... args) {
        try {
            PreparedStatement stmt = con.prepareStatement(sql);

            for(int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }

            stmt.executeUpdate();
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public CachedRowSet query(String sql, Object... args) {
        try {
            PreparedStatement stmt = con.prepareStatement(sql);

            for(int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }

            ResultSet rs = stmt.executeQuery();
            CachedRowSet cRS = RowSetProvider.newFactory().createCachedRowSet();
            
            if(rs != null) {
                cRS.populate(rs);
                rs.close();
            }
        
            return cRS;
        } 
        catch (SQLException e) {
            e.printStackTrace();
        
            return null;
        }
    }
}
