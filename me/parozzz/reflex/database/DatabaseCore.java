/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.parozzz.reflex.database.json.JSONSQLTable;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Paros
 */
public class DatabaseCore implements Connectionable
{
    private final JSONThread jsonThread;
    private final Queue<JSONSQLTable<?>> jsonTableQueue;
    private volatile HikariDataSource source;

    public DatabaseCore(final JavaPlugin javaPlugin)
    {
        HikariConfig config = new HikariConfig();
        config.setPoolName("CoreSQLitePool");
        config.setDriverClassName("org.sqlite.JDBC");

        /*
         * try { new File("jdbc:sqlite:" + core.getDataFolder().getAbsolutePath() + File.separator + "database.db").createNewFile(); } catch (IOException ex) {
         * Logger.getLogger(DatabaseCore.class.getName()).log(Level.SEVERE, null, ex); }
         */
        config.setJdbcUrl("jdbc:sqlite:" + javaPlugin.getDataFolder().getAbsolutePath() + File.separator + "database.db");

        source = new HikariDataSource(config);

        this.jsonTableQueue = new ConcurrentLinkedQueue<>();
        this.jsonThread = new JSONThread();
    }

    public void startThreads()
    {
        jsonThread.start();
    }

    @Override
    public Connection getConnection() throws SQLException
    {
        return source.getConnection();
    }

    public void addJsonTable(final JSONSQLTable<?> table)
    {
        jsonTableQueue.add(table);
    }

    /**
     * Don't call this method unless the server is being stop by the command. It will stop saving data to the database.
     */
    public void setServerStop()
    {
        jsonThread.stoppingServer = true;
    }

    /**
     * Check if the data has been all updated before being able to stop the server
     *
     * @return {@code True} if everything has been correctly saved, {@code False} otherwise.
     */
    public boolean isAllDataUpdatedOnStop()
    {
        return jsonThread.stopSavingComplete;
    }

    private final class JSONThread extends Thread
    {
        private boolean stoppingServer = false;
        private boolean stopSavingComplete = false;

        @Override
        public void run()
        {
            while(!stoppingServer)
            {
                try
                {
                    for(JSONSQLTable<?> table : jsonTableQueue)
                    {
                        table.refreshData();

                        sleep(500);
                    }

                    sleep(1500);
                }
                catch(IllegalArgumentException | InterruptedException ex)
                {
                    ex.printStackTrace();
                }
            }

            stopSavingComplete = true;
        }
    }
}
