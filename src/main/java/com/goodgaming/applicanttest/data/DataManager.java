package com.goodgaming.applicanttest.data;

import com.goodgaming.applicanttest.ApplicantTest;
import com.patrickzhong.spark.file.Config;
import com.patrickzhong.spark.util.MapBuilder;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by patrickzhong on 8/5/18.
 */
public class DataManager {

    @Getter private static DataManager instance;
    @Getter private Config config;
    private HikariDataSource source;

    public DataManager(){

        instance = this;

        config = new Config(ApplicantTest.getInstance(), MapBuilder.get()
                .put("Logging", true)
                .put("Database.host", "host")
                .put("Database.user", "user")
                .put("Database.password", "pass")
                .put("Database.database", "Economy")
                .build(), "config");

        ApplicantTest.setLogging(config.getFile().getBoolean("Logging"));

        source = new HikariDataSource();
        source.setJdbcUrl("jdbc:mysql://" + config.getFile().getString("Database.host"));
        source.setUsername(config.getFile().getString("Database.user"));
        source.setPassword(config.getFile().getString("Database.password"));


        init();
    }

    private void init(){

        Bukkit.getScheduler().runTaskAsynchronously(ApplicantTest.getInstance(), () -> {
            try (Connection conn = source.getConnection()){
                String db = config.getFile().getString("Database.database");
                conn.prepareStatement("CREATE DATABASE IF NOT EXISTS " + db).execute();
                conn.prepareStatement("USE " + db).execute();
                conn.prepareStatement("CREATE TABLE IF NOT EXISTS Players(ID INT AUTO_INCREMENT, Uuid VARCHAR(36), Balance INT UNSIGNED, PRIMARY KEY (ID))").execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    public boolean exists(UUID uuid){

        try (Connection conn = source.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Players WHERE Uuid=?");
            stmt.setString(1, uuid.toString());
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    public void insert(UUID uuid, int amount){

        try (Connection conn = source.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Players(ID, Uuid, Balance) VALUES (?, ?, ?)");
            stmt.setInt(1, 0);
            stmt.setString(2, uuid.toString());
            stmt.setInt(3, amount);
            stmt.executeUpdate();

            ApplicantTest.log("Inserted " + uuid + " into database with value " + amount + ".");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setBal(UUID uuid, int amount){

        try (Connection conn = source.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("UPDATE Players SET Balance=? WHERE Uuid=?");
            stmt.setInt(1, amount);
            stmt.setString(2, uuid.toString());
            stmt.executeUpdate();

            ApplicantTest.log("Set value of " + uuid + " to " + amount + ".");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getBal(UUID uuid){

        try (Connection conn = source.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Players WHERE Uuid=?");
            stmt.setString(1, uuid.toString());

            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return rs.getInt("Balance");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;

    }

}
