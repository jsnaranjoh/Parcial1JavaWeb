/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Departamento;

/**
 *
 * @author jsnar
 */
@Stateless
public class DepartamentoFacade extends AbstractFacade<Departamento> implements DepartamentoFacadeLocal {

    @PersistenceContext(unitName = "Parcial1JavaWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DepartamentoFacade() {
        super(Departamento.class);
    }

    @Override
    public Departamento consultarxNombre(String nombre) {
        String consulta = "SELECT d FROM Departamento d WHERE d.nombred='"+nombre+"'";
        try{
            Query query = em.createQuery(consulta);
            return (Departamento) query.getSingleResult();
        }catch(NoResultException nre){
            return null;
        }
    }
    
}
