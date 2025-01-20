package com.profamilia.Dao;

import com.profamilia.Dto.Empleado;
import com.profamilia.Dto.Sede;
import com.profamilia.Utilidades.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDao {

    // Metodo para agregar empleados
    public void crearEmpleado(Empleado empleado, SedeDao sedeDAO) {
        String sql = "INSERT INTO empleado (idempleado, nombre, edad, telefono, cargo, salario, idsede) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, empleado.getIdempleado());
            stmt.setString(2, empleado.getNombre());
            stmt.setInt(3, empleado.getEdad());
            stmt.setLong(4, empleado.getTelefono());
            stmt.setString(5, empleado.getCargo());
            stmt.setDouble(6, empleado.getSalario());
            stmt.setInt(7, empleado.getIdsede());

            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al crear empleado: " + e.getMessage(), e);
        }
    }

    //Metodo para tener los Empleado por Sede
    public List<Empleado> obtenerEmpleadosPorSede(int idSede) {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleado WHERE idsede = ?" ;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSede);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Empleado emp = new Empleado();
                emp.setIdempleado(rs.getLong("idempleado"));
                emp.setNombre(rs.getString("nombre"));
                emp.setEdad(rs.getInt("edad"));
                emp.setTelefono(rs.getLong("telefono"));
                emp.setCargo(rs.getString("cargo"));
                emp.setSalario(rs.getDouble("salario"));
                empleados.add(emp);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar empleados por sede: " + e.getMessage(), e);
        }

        return empleados;
    }

    //Metodo para obtener el empleado por id
    public Empleado obtenerEmpleadoPorId(long idEmpleado) {
        String sql = "SELECT * FROM empleado WHERE idempleado = ?";
        Empleado empleado = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idEmpleado);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                empleado = new Empleado();
                empleado.setIdempleado(rs.getLong("idempleado"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setEdad(rs.getInt("edad"));
                empleado.setTelefono(rs.getLong("telefono"));
                empleado.setCargo(rs.getString("cargo"));
                empleado.setSalario(rs.getDouble("salario"));
                empleado.setIdsede(rs.getInt("idsede"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener empleado: " + e.getMessage(), e);
        }
        return empleado;
    }

    // Metodo para actualizar un empleado
    public void editarEmpleado(Empleado empleado, SedeDao sedeDAO) {
        String sql = "UPDATE empleado SET nombre = ?, edad = ?, telefono = ?, cargo = ?, salario = ?, idsede = ? WHERE idempleado = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, empleado.getNombre());
            stmt.setInt(2, empleado.getEdad());
            stmt.setLong(3, empleado.getTelefono());
            stmt.setString(4, empleado.getCargo());
            stmt.setDouble(5, empleado.getSalario());
            stmt.setInt(6, empleado.getIdsede());
            stmt.setLong(7, empleado.getIdempleado());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new IllegalArgumentException("No se encontró un empleado con ID " + empleado.getIdempleado() + " para actualizar.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar empleado: " + e.getMessage(), e);
        }
    }

    // Metodo para eliminar un empleado
    public void eliminarEmpleado(long idEmpleado) {
        String sql = "DELETE FROM empleado WHERE idempleado = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idEmpleado);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted == 0) {
                throw new IllegalArgumentException("No se encontró un empleado con ID " + idEmpleado);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar empleado: " + e.getMessage(), e);
        }
    }
}

