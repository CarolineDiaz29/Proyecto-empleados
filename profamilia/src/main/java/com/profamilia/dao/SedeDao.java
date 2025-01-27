package com.profamilia.dao;

import com.profamilia.dto.Sede;
import com.profamilia.utilidades.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SedeDao {
    /**
     * El metodo sirve para crear las sedes
     * @param sede
     */
    public void crearSede(Sede sede) {
        Transaction transaction = null;
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(sede);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error al crear sede: " + e.getMessage(), e);
        }
    }

    /**
     * Metodo para hacer consultar las sedes por su id
     * @param idSede
     * @return
     */
    public Sede obtenerSedePorId(int idSede) {
        Transaction transaction = null;
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Sede sede = session.get(Sede.class, idSede);
            transaction.commit();
            return sede;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error al obtener sede: " + e.getMessage(), e);
        }
    }

    /**
     * Metodo para hacer consultar las sedes
     * @return
     */
    public List<Sede> obtenerTodasLasSedes() {
        Transaction transaction = null;
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<Sede> sedes = session.createQuery("FROM Sede", Sede.class).list();
            transaction.commit();
            return sedes;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error al obtener todas las sedes: " + e.getMessage(), e);
        }
    }

    /**
     * Metodo para editar  las sedes
     * @param sede
     */
    public void editarSede(Sede sede) {
        Transaction transaction = null;
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(sede);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error al editar sede: " + e.getMessage(), e);
        }
    }
}