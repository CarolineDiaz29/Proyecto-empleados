package com.profamilia.dao;

import com.profamilia.dto.Empleado;
import com.profamilia.utilidades.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmpleadoDao {
    /**
     * Metodo para crear una empleado
     * @param empleado
     */
    public void crearEmpleado(Empleado empleado) {
        Transaction transaction = null;
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(empleado);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error al crear empleado: " + e.getMessage(), e);
        }
    }

    /**
     * Metodo para consultar los empleado por su id
     * @param idEmpleado
     * @return
     */
    public Empleado obtenerEmpleadoPorId(long idEmpleado) {
        Transaction transaction = null;
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Empleado empleado = session.get(Empleado.class, idEmpleado);
            transaction.commit();
            return empleado;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error al obtener empleado: " + e.getMessage(), e);
        }
    }

    /**
     * Metodo para consultar los empleados por la sede a la que pertenecen
     * @param idSede
     * @return
     */
    public List<Empleado> obtenerEmpleadosPorSede(int idSede) {
        Transaction transaction = null;
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<Empleado> empleados = session.createQuery("FROM Empleado WHERE sede.idsede = :idSede", Empleado.class)
                    .setParameter("idSede", idSede)
                    .list();
            transaction.commit();
            return empleados;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error al obtener empleados por sede: " + e.getMessage(), e);
        }
    }

    /**
     * Metodo para actualizar o modificar loos datos de los empleados
     * @param empleado
     */
    public void editarEmpleado(Empleado empleado) {
        Transaction transaction = null;
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(empleado);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error al editar empleado: " + e.getMessage(), e);
        }
    }

    /**
     * Metodo para eliminar un empleado
     * @param idEmpleado
     */
    public void eliminarEmpleado(long idEmpleado) {
        Transaction transaction = null;
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Empleado empleado = session.get(Empleado.class, idEmpleado);
            if (empleado != null) {
                session.delete(empleado);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error al eliminar empleado: " + e.getMessage(), e);
        }
    }
}