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
import modelo.TrabajaEn;

/**
 *
 * @author jsnar
 */
@Stateless
public class TrabajaEnFacade extends AbstractFacade<TrabajaEn> implements TrabajaEnFacadeLocal {

    @PersistenceContext(unitName = "Parcial1JavaWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TrabajaEnFacade() {
        super(TrabajaEn.class);
    }

    @Override
    public List<TrabajaEn> consultarxTipoEmpleado(String tipo) {
        String consulta = "SELECT t FROM TrabajaEn t WHERE t.empleado.tipo='"+tipo+"'";
        Query query = em.createQuery(consulta);
        return query.getResultList();
    }
    
}
