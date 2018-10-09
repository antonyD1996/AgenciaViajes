package sv.edu.unab.negocio;

import java.sql.*;
import java.util.List;
import java.util.function.*;
import java.util.function.Supplier;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import sv.edu.unab.dominio.Cliente;
import sv.edu.unab.dominio.Empleado;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class ClienteN{
    private static final Logger LOG=Logger.getLogger("sv.edu.unab.agenciavuelo");
    EntityManagerFactory emf= Persistence.createEntityManagerFactory("AgenciaVueloPU");
    EntityManager em=emf.createEntityManager();
    public Supplier<List<Cliente>> listadoClientes=()->{
        LOG.log(Level.INFO,"[ClienteN][INIT]->Listado de Clientes");
        Query query=em.createNamedQuery("Cliente.findAll");
        List<Cliente> listado=query.getResultList();
        return listado;
    };

    public Consumer<Cliente> insertarCliente=(c)->{
        LOG.log(Level.INFO,"[ClienteN][INIT]->Insertar Cliente");
        try {
            em.getTransaction().begin();
            c.setDatosPersonales(c.getDatosPersonales());
            em.persist(c);
            em.getTransaction().commit();
        }finally {
            em.close();
        }
    };

    public Consumer<Cliente> editarCliente=(c)->{
        LOG.log(Level.INFO,"[ClienteN][INIT]->Editar Cliente");
        try {
            em.getTransaction().begin();
            Cliente cl=em.find(Cliente.class,c.getId());
            cl.setDatosPersonales(c.getDatosPersonales());
            em.getTransaction().commit();
        }finally {
            em.close();
        }
    };
    public Consumer<Cliente> eliminarCliente=(c)->{
        LOG.log(Level.INFO,"[ClienteN][INIT]->Eliminar Cliente");
        try{
            em.getTransaction().begin();
            Cliente cl = em.find(Cliente.class, c.getId());
            em.remove(cl);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
    };
}