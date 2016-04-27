/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ejb.Local;
import modelo.Empleado;

/**
 *
 * @author jsnar
 */
@Local
public interface EmpleadoLogicaLocal {
    public List<Empleado> consultarTodos() throws Exception;
    public List<Empleado> consultarxTipo(String tipo) throws Exception;
}
