package com.profamilia.Dao;

import com.profamilia.Dto.Sede;
import com.profamilia.Utilidades.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SedeDao {
    //Metodo para agregar sede
    public void crearSedes(Sede sede) {
        String sql = "INSERT INTO sede (nombre, direccion, ciudad, telefono, capacidad) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sede.getNombre());
            stmt.setString(2, sede.getDireccion());
            stmt.setString(3, sede.getCiudad());
            stmt.setLong(4, sede.getTelefono());
            stmt.setInt(5, sede.getCapacidad());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al insertar la sede en la base de datos", e);
        }
    }

    //Metodo para consultar sede con sus Empleados
    public Sede obtenerSedePorId(int idsede) {
        Sede sede = null;
        String sql = "SELECT * FROM sede WHERE idsede = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idsede);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sede = new Sede();
                sede.setIdsede(rs.getInt("idsede"));
                sede.setNombre(rs.getString("nombre"));
                sede.setCiudad(rs.getString("ciudad"));
                sede.setDireccion(rs.getString("direccion"));
                sede.setTelefono(rs.getLong("telefono"));
                sede.setCapacidad(rs.getInt("capacidad"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Added proper error handling
        }
        return sede;
    }

    //Metodo para consltar todas las sedes
    public List<Sede> obtenerTodasLasSedes() {
        List<Sede> listaSedes = new ArrayList<>();
        String sql = "SELECT idsede, nombre, ciudad, direccion, telefono, capacidad FROM sede";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (!rs.isBeforeFirst()) {
                System.out.println("No se encontraron registros en la tabla 'sede'.");
                return listaSedes;
            }

            while (rs.next()) {
                Sede s = new Sede();
                s.setIdsede(rs.getInt("idsede"));
                s.setNombre(rs.getString("nombre"));
                s.setCiudad(rs.getString("ciudad"));
                s.setDireccion(rs.getString("direccion"));
                s.setTelefono(rs.getLong("telefono"));
                s.setCapacidad(rs.getInt("capacidad"));
                listaSedes.add(s);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar las sedes: " + e.getMessage(), e);
        }

        return listaSedes;
    }

    //Metodo para editar la sede
    public void editarSede(Sede sede) {
        String sql = "UPDATE sede SET nombre = ?, ciudad = ?, direccion = ?, telefono = ?, capacidad = ? WHERE idsede = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sede.getNombre());
            stmt.setString(2, sede.getCiudad());
            stmt.setString(3, sede.getDireccion());
            stmt.setLong(4, sede.getTelefono());
            stmt.setInt(5, sede.getCapacidad());
            stmt.setInt(6, sede.getIdsede());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("No se encontró la sede con ID " + sede.getIdsede());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la sede: " + e.getMessage(), e);
        }
    }
}