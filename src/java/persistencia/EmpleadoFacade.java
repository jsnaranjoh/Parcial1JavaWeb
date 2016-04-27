/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Empleado;

/**
 *
 * @author jsnar
 */
@Stateless
public class EmpleadoFacade extends AbstractFacade<Empleado> implements EmpleadoFacadeLocal {

    @PersistenceContext(unitName = "Parcial1JavaWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpleadoFacade() {
        super(Empleado.class);
    }
    
    @Override
    public List<Empleado> consultarxTipo(String tipo) {
        String consulta = "SELECT e FROM Empleado e WHERE e.tipo='"+tipo+"'";
        Query query = em.createQuery(consulta);
        return query.getResultList();
    }
    
}
