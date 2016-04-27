/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import javax.ejb.Local;
import modelo.Empleado;

/**
 *
 * @author jsnar
 */
@Local
public interface SesionLogicaLocal {
    public void buscarCamposInvalidosOVacios(Long documento, String clave) throws Exception;
    public Empleado iniciarSesionEmpleado(Long documento, String clave) throws Exception;
    public void modificarClave(Empleado empleado, String clave) throws Exception;
}
