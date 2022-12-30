package coolguy1842.factions.Managers.FactionsManagerMethods.Loaders;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.Database;
import coolguy1842.factions.Managers.FactionsManager;

public class LoadDatabase {
    public static void load(FactionsManager manager) {
        String factionsDatabasePath = Globals.plugin.getDataFolder().toPath().resolve("factions.db").toAbsolutePath().toString();
        manager.database = new Database(factionsDatabasePath);
        
        loadTables(manager);
    }

    private static void loadTables(FactionsManager manager) {
        manager.database.execute("""
            CREATE TABLE IF NOT EXISTS `players` (
                `id` TEXT NOT NULL PRIMARY KEY,
                `faction` TEXT,
                `rank` TEXT,
                `money` INTEGER NOT NULL
            );
        """);

        manager.database.execute("""
            CREATE TABLE IF NOT EXISTS `factions` (
                `id` TEXT NOT NULL PRIMARY KEY,
                `displayName` TEXT NOT NULL,
                `leader` TEXT NOT NULL,
                `money` INTEGER NOT NULL
            );
        """);
        
        manager.database.execute("""
            CREATE TABLE IF NOT EXISTS `factionRanks` (
                `id` TEXT NOT NULL PRIMARY KEY,
                `faction` TEXT NOT NULL,
                `displayName` TEXT NOT NULL,
                `isDefault` INTEGER NOT NULL,
                `permissions` TEXT
            );
        """);
        
        manager.database.execute("""
            CREATE TABLE IF NOT EXISTS `factionOptions` (
                `faction` TEXT NOT NULL,
                `option` TEXT NOT NULL,
                `value` TEXT NOT NULL
            );      
        """);

        manager.database.execute("""
            CREATE TABLE IF NOT EXISTS `factionInvites` (
                `invited` TEXT NOT NULL PRIMARY KEY,
                `faction` TEXT NOT NULL
            );
        """);
        
        manager.database.execute("""
            CREATE TABLE IF NOT EXISTS `factionAllyInvites` (
                `inviter` TEXT NOT NULL,
                `invited` TEXT NOT NULL
            );
        """);

        manager.database.execute("""
            CREATE TABLE IF NOT EXISTS `factionAllies` (
                `faction1` TEXT NOT NULL,
                `faction2` TEXT NOT NULL
            );
        """);

        manager.database.execute("""
            CREATE TABLE IF NOT EXISTS `factionVaults` (
                `id` TEXT NOT NULL PRIMARY KEY,
                `faction` TEXT NOT NULL,
                `displayName` TEXT NOT NULL,
                `contents` TEXT NOT NULL
            );
        """);

        manager.database.execute("""
            CREATE TABLE IF NOT EXISTS `factionClaims` (
                `chunk` TEXT NOT NULL PRIMARY KEY,
                `faction` TEXT NOT NULL
            );
        """);
     
        manager.database.execute("""
            CREATE TABLE IF NOT EXISTS `factionHomes` (
                `id` TEXT NOT NULL PRIMARY KEY,
                `displayName` TEXT NOT NULL,
                `location` TEXT NOT NULL,
                `faction` TEXT NOT NULL
            );            
        """);
    }
}
