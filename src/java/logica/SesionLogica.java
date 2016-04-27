/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logica;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.Empleado;
import org.apache.commons.codec.digest.DigestUtils;
import persistencia.EmpleadoFacadeLocal;

/**
 *
 * @author jsnar
 */
@Stateless
public class SesionLogica implements SesionLogicaLocal {
    @EJB
    private EmpleadoFacadeLocal empleadoDAO;
    
    @Override
    public void buscarCamposInvalidosOVacios(Long documento, String clave) throws Exception {
        if(documento==null) {
            throw new Exception("Ingrese un usuario válido.");
        }
        if(clave.equals("")) {
            throw new Exception("La clave es obligatoria.");
        }
    }
    
    @Override
    public Empleado iniciarSesionEmpleado(Long documento, String clave) throws Exception {
        Empleado e = empleadoDAO.find(documento);
        if(e!=null) {
            String claveEncriptada = DigestUtils.md5Hex(clave);
            if(!e.getClave().equals(claveEncriptada)) {
                throw new Exception("La clave es incorrecta.");
            }
        } else {
            throw new Exception("El usuario no existe.");
        }
        return e;
    }

    @Override
    public void modificarClave(Empleado empleado, String clave) throws Exception {
        if(clave.equals("")) {
            throw new Exception("La clave es obligatoria.");
        } else {
            Empleado objEmpleado = empleadoDAO.find(empleado.getNss());
            String claveEncriptada = DigestUtils.md5Hex(clave);
            objEmpleado.setClave(claveEncriptada);
            empleadoDAO.edit(objEmpleado);
        }
    }
}