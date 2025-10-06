package com.crud.veiculos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO implements AutoCloseable {
    private final Connection conn;

    public VehicleDAO(String dbFile) throws SQLException {
        String url = "jdbc:sqlite:" + dbFile;
        this.conn = DriverManager.getConnection(url);
        initTable();
    }

    private void initTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS vehicles (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "marca TEXT NOT NULL, " +
                "modelo TEXT NOT NULL, " +
                "placa TEXT NOT NULL UNIQUE, " +
                "ano INTEGER NOT NULL" +
                ");";
        try (Statement st = conn.createStatement()) {
            st.execute(sql);
        }
    }

    public Vehicle insert(Vehicle v) throws SQLException {
        String sql = "INSERT INTO vehicles(marca, modelo, placa, ano) VALUES(?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, v.getMarca());
            ps.setString(2, v.getModelo());
            ps.setString(3, v.getPlaca());
            ps.setInt(4, v.getAno());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) v.setId(rs.getInt(1));
            }
        }
        return v;
    }

    public List<Vehicle> listAll() throws SQLException {
        String sql = "SELECT id, marca, modelo, placa, ano FROM vehicles";
        List<Vehicle> list = new ArrayList<>();
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Vehicle(rs.getInt("id"), rs.getString("marca"), rs.getString("modelo"), rs.getString("placa"), rs.getInt("ano")));
            }
        }
        return list;
    }

    public Vehicle findByPlaca(String placa) throws SQLException {
        String sql = "SELECT id, marca, modelo, placa, ano FROM vehicles WHERE placa = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, placa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Vehicle(rs.getInt("id"), rs.getString("marca"), rs.getString("modelo"), rs.getString("placa"), rs.getInt("ano"));
                }
            }
        }
        return null;
    }

    public boolean deleteById(int id) throws SQLException {
        String sql = "DELETE FROM vehicles WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public void close() throws Exception {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}
