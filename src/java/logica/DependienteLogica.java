/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.Dependiente;
import persistencia.DependienteFacadeLocal;

/**
 *
 * @author jsnar
 */
@Stateless
public class DependienteLogica implements DependienteLogicaLocal {

    @EJB
    DependienteFacadeLocal dependienteDAO;
    
    @Override
    public void registrarDependiente(Dependiente dependiente) throws Exception {
        if(dependiente.getDependientePK().getNombreDependiente().equals("")){
            throw new Exception("Campo nombre es obligatorio.");
        }
        if(dependiente.getSexo().equals("0")){
            throw new Exception("Campo sexo es obligatorio.");
        }
        if(dependiente.getFechan() == null){
            throw new Exception("Campo fecha de nacimiento es obligatorio.");
        }
        if(dependiente.getParentesco().equals("")){
            throw new Exception("Campo parentesco es obligatorio.");
        }
        
        Dependiente objDependiente = dependienteDAO.find(dependiente.getDependientePK());
        if(objDependiente != null) {
            throw new Exception("Dependiente ya existe.");
        } else {
            dependienteDAO.create(dependiente);
        }
    }

    @Override
    public void modificarDependiente(Dependiente dependiente) throws Exception {
        if(dependiente.getDependientePK().getNombreDependiente() == null){
            throw new Exception("Campo nombre es obligatorio.");
        }
        if(dependiente.getSexo().equals("")){
            throw new Exception("Campo sexo es obligatorio.");
        }
        if(dependiente.getFechan() == null){
            throw new Exception("Campo fecha de nacimiento es obligatorio.");
        }
        if(dependiente.getParentesco().equals("")){
            throw new Exception("Campo parentesco es obligatorio.");
        }
        
        Dependiente objDependiente = dependienteDAO.find(dependiente.getDependientePK());
        if(objDependiente == null) {
            throw new Exception("Dependiente a modificar no existe.");
        } else {
            objDependiente.setDependientePK(dependiente.getDependientePK());
            objDependiente.setSexo(dependiente.getSexo());
            objDependiente.setFechan(dependiente.getFechan());
            objDependiente.setParentesco(dependiente.getParentesco());
            dependienteDAO.create(objDependiente);
        }
    }

    @Override
    public void eliminarDependiente(Dependiente dependiente) throws Exception {
        if(dependiente.getDependientePK().getNombreDependiente() == null){
            throw new Exception("Campo nombre es obligatorio.");
        }
        
        Dependiente objDependiente = dependienteDAO.find(dependiente.getDependientePK());
        if(objDependiente == null) {
            throw new Exception("Dependiente a eliminar no existe.");
        } else {
            dependienteDAO.remove(objDependiente);
        }
    }
    
    @Override
    public List<Dependiente> consultarxEmpleado(Long nssEmpleado) {
        return dependienteDAO.consultarxEmpleado(nssEmpleado);
    }
}