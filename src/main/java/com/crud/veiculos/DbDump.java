package com.crud.veiculos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbDump {
    public static void main(String[] args) throws Exception {
        String db = "veiculos.db";
        if (args.length > 0) db = args[0];
        String url = "jdbc:sqlite:" + db;
        try (Connection c = DriverManager.getConnection(url); Statement st = c.createStatement()) {
            System.out.println("Conectado ao: " + db);
            try (ResultSet rs = st.executeQuery("SELECT id, marca, modelo, placa, ano FROM vehicles")) {
                System.out.println("id | marca | modelo | placa | ano");
                while (rs.next()) {
                    System.out.printf("%d | %s | %s | %s | %d%n", rs.getInt("id"), rs.getString("marca"), rs.getString("modelo"), rs.getString("placa"), rs.getInt("ano"));
                }
            }
        }
    }
}
