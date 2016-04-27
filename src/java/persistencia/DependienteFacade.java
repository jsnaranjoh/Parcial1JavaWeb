/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Dependiente;
import modelo.Empleado;

/**
 *
 * @author jsnar
 */
@Stateless
public class DependienteFacade extends AbstractFacade<Dependiente> implements DependienteFacadeLocal {

    @PersistenceContext(unitName = "Parcial1JavaWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DependienteFacade() {
        super(Dependiente.class);
    }

    @Override
    public List<Dependiente> consultarxEmpleado(Long nssEmpleado) {
        String consulta = "SELECT d FROM Dependiente d WHERE d.empleado.nss='"+nssEmpleado+"'";
        Query query = em.createQuery(consulta);
        return query.getResultList();
    }
    
}
