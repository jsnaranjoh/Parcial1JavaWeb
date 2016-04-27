/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.LugaresDptos;

/**
 *
 * @author jsnar
 */
@Stateless
public class LugaresDptosFacade extends AbstractFacade<LugaresDptos> implements LugaresDptosFacadeLocal {

    @PersistenceContext(unitName = "Parcial1JavaWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LugaresDptosFacade() {
        super(LugaresDptos.class);
    }
    
}
