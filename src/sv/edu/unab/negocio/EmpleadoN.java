package sv.edu.unab.negocio;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.unab.dominio.Empleado;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EmpleadoN {
    private static final Logger LOG=Logger.getLogger("sv.edu.unab.agenciavuelo");
    EntityManagerFactory emf= Persistence.createEntityManagerFactory("AgenciaVueloPU");
    EntityManager em=emf.createEntityManager();

    public Supplier<List<Empleado>> listadoEmpleados=()->{
        LOG.log(Level.INFO,"[EmpleadoN][INIT]->Listado de Empleados");
        Query query=em.createNamedQuery("Empleado.findAll");
        List<Empleado> listado=query.getResultList();
        return listado;
       };
    public Consumer<Empleado> insertarEmpleado=e->{
        LOG.log(Level.INFO,"[EmpleadoN][INIT]->Insertar Empleado");
        try {
            em.getTransaction().begin();
            e.setDatosPersonales(e.getDatosPersonales());
            em.persist(e);
            em.getTransaction().commit();
        }finally {
            em.close();
        }
    };
    public Consumer<Empleado> editarEmpleado=e->{
        LOG.log(Level.INFO,"[EmpleadoN][INIT]->Editar Empleado");
        try {
            em.getTransaction().begin();
            Empleado emp=em.find(Empleado.class,e.getId());
            emp.setDatosPersonales(e.getDatosPersonales());
            emp.setIsss(e.getIsss());
            emp.setAfp(e.getAfp());
            em.getTransaction().commit();
        }finally {
            em.close();
        }
    };
    public Consumer<Empleado> eliminarEmpleado=e->{
        LOG.log(Level.INFO,"[EmpleadoN][INIT]->Remover Empleado");
        try{
            em.getTransaction().begin();
            Empleado emp = em.find(Empleado.class, e.getId());
            em.remove(emp);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
    };
}