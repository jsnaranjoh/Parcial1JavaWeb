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
import modelo.Proyecto;

/**
 *
 * @author jsnar
 */
@Stateless
public class ProyectoFacade extends AbstractFacade<Proyecto> implements ProyectoFacadeLocal {

    @PersistenceContext(unitName = "Parcial1JavaWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProyectoFacade() {
        super(Proyecto.class);
    }

    @Override
    public Proyecto consultarxNombre(String nombre) {
        String consulta = "SELECT p FROM Proyecto p WHERE p.nombrep='"+nombre+"'";
        try{
            Query query = em.createQuery(consulta);
            return (Proyecto) query.getSingleResult();
        }catch(NoResultException nre){
            return null;
        }
    }
    
}
