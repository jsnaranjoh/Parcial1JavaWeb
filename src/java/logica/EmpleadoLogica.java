/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.Empleado;
import persistencia.EmpleadoFacadeLocal;

/**
 *
 * @author jsnar
 */
@Stateless
public class EmpleadoLogica implements EmpleadoLogicaLocal {

    @EJB
    EmpleadoFacadeLocal empleadoDAO;
    
    @Override
    public List<Empleado> consultarTodos() throws Exception {
        return empleadoDAO.findAll();
    }

    @Override
    public List<Empleado> consultarxTipo(String tipo) throws Exception {
        return empleadoDAO.consultarxTipo(tipo);
    }
}
