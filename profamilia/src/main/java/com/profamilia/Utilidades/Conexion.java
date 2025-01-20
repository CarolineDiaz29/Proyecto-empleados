package com.profamilia.Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

        private static final String url = "jdbc:postgresql://localhost:5432/profamilia";
        private static final String user = "postgres";
        private static final String pass = "123456789";

        static {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Error al cargar el driver de PostgreSQL", e);
            }
        }

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(url, user, pass);
        }
}

