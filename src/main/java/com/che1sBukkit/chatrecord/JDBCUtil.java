package com.che1sBukkit.chatrecord;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;

public final class JDBCUtil {
    private static Connection conn = null;
    private static String host;
    private static int port;
    private static String database;
    private static String table;
    private static String user;
    private static String password;
    private static boolean useSSL;
    private static final boolean useUnicode = true;
    private static final String characterEncoding = "utf8";
    private static final String serverTimezone = "Asia/Shanghai";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private JDBCUtil() {}

    public static void init() {
        ConfigurationSection config = Main.getInstance().getConfig().getConfigurationSection("mysql");
        host = config.getString("host");
        port = config.getInt("port");
        database = config.getString("database");
        table = config.getString("table");
        user = config.getString("user");
        password = config.getString("password");
        useSSL = config.getBoolean("useSSL");
        connect();
    }

    public static void connect() {
        if (!isConnected()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://"
                        + host + ":" + port + "/"
                        + database + "?"
                        + "useSSL=" + useSSL
                        + "&useUnicode=" + useUnicode
                        + "&characterEncoding=" + characterEncoding
                        + "&serverTimezone=" + serverTimezone;
                Enumeration<Driver> drivers = DriverManager.getDrivers();
                while (drivers.hasMoreElements()) {
                    Driver driver = drivers.nextElement();
                    if (driver instanceof com.mysql.cj.jdbc.Driver) {
                        Properties properties = new Properties();
                        properties.put("user", user);
                        properties.put("password", password);
                        conn = driver.connect(url, properties);
                        break;
                    }
                }
                if (conn == null) {
                    throw new SQLException("[com.mysql.cj.jdbc.Driver] not found.");
                }
                Main.getInstance().getLogger().info("MySQL connected successfully!");
                String sql = "CREATE TABLE IF NOT EXISTS `" + table + "` ("
                        + "`id` INT AUTO_INCREMENT PRIMARY KEY,"
                        + "`name` varchar(20) NOT NULL,"
                        + "`time` varchar(20) NOT NULL,"
                        + "`message` varchar(255) NOT NULL"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
                Statement statement = conn.createStatement();
                statement.execute(sql);
                statement.close();
            } catch (SQLException | ClassNotFoundException e) {
                Main.getInstance().getLogger().log(Level.SEVERE, e, e::getMessage);
                Bukkit.getPluginManager().disablePlugin(Main.getInstance());
            }
        }
    }

    public static void disconnect() {
        try {
            if (isConnected()) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn = null;
        }
    }

    public static boolean isConnected() {
        try {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertChat(String name, String message) {
        if (!isConnected()) {
            connect();
        }
        try {
            String sql = "INSERT INTO `" + table + "` VALUES (NULL, ?, ?, ?);";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1, name);
            pStatement.setString(2, formatter.format(LocalDateTime.now()));
            pStatement.setString(3, message);
            pStatement.executeUpdate();
            pStatement.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
