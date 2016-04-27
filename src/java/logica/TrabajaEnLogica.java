/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.TrabajaEn;
import persistencia.TrabajaEnFacadeLocal;

/**
 *
 * @author jsnar
 */
@Stateless
public class TrabajaEnLogica implements TrabajaEnLogicaLocal {

    @EJB
    TrabajaEnFacadeLocal trabajaEnDAO;
    
    @Override
    public void registrarTrabajaEn(TrabajaEn trabajaEn) throws Exception {
        TrabajaEn objTrabajaEn = trabajaEnDAO.find(trabajaEn.getTrabajaEnPK());
        if(objTrabajaEn != null){
            throw new Exception("El trabajaEn ya existe.");
        }
        else{
            trabajaEnDAO.create(trabajaEn);
        }
    }

    @Override
    public List<TrabajaEn> consultarTodas() throws Exception {
        return trabajaEnDAO.findAll();
    }
    
    @Override
    public List<TrabajaEn> consultarxTipoEmpleado(String tipo) throws Exception {
        return trabajaEnDAO.consultarxTipoEmpleado(tipo);
    }
}
