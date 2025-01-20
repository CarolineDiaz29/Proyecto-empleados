package com.profamilia.Utilidades;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection conn = Conexion.getConnection()) {
            if (conn != null) {
                System.out.println("Conexión exitosa a la base de datos.");
            } else {
                System.out.println("Error al conectar a la base de datos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

