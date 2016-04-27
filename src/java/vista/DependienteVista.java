/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import logica.DependienteLogicaLocal;
import modelo.Dependiente;
import modelo.DependientePK;
import modelo.Empleado;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author jsnar
 */
@Named(value = "dependienteVista")
@RequestScoped
public class DependienteVista {

    private InputText txtNombre;
    private SelectOneMenu cmbSexo;
    private Date fechaNacimineto;
    private InputText txtParentesco;
    
    private List<Dependiente> listaDependientes;
    private Dependiente selectedDependiente;
    
    private CommandButton btnRegistrar;
    private CommandButton btnModificar;
    private CommandButton btnEliminar;
    private CommandButton btnLimpiar;
    
    @EJB
    private DependienteLogicaLocal dependienteLogica;
    
    /**
     * Creates a new instance of DependienteVista
     */
    public DependienteVista() {
    }

    public InputText getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(InputText txtNombre) {
        this.txtNombre = txtNombre;
    }

    public SelectOneMenu getCmbSexo() {
        return cmbSexo;
    }

    public void setCmbSexo(SelectOneMenu cmbSexo) {
        this.cmbSexo = cmbSexo;
    }

    public Date getFechaNacimineto() {
        return fechaNacimineto;
    }

    public void setFechaNacimineto(Date FechaNacimineto) {
        this.fechaNacimineto = FechaNacimineto;
    }

    public InputText getTxtParentesco() {
        return txtParentesco;
    }

    public void setTxtParentesco(InputText txtParentesco) {
        this.txtParentesco = txtParentesco;
    }

    public List<Dependiente> getListaDependientes() {
        if(listaDependientes == null) {
            try {
                Empleado empleado = (Empleado) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
                listaDependientes = dependienteLogica.consultarxEmpleado(empleado.getNss());
            } catch (Exception ex) {
                Logger.getLogger(DependienteVista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaDependientes;
    }

    public void setListaDependientes(List<Dependiente> listaDependientes) {
        this.listaDependientes = listaDependientes;
    }

    public Dependiente getSelectedDependiente() {
        return selectedDependiente;
    }

    public void setSelectedDependiente(Dependiente selectedDependiente) {
        this.selectedDependiente = selectedDependiente;
    }

    public CommandButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public void setBtnRegistrar(CommandButton btnRegistrar) {
        this.btnRegistrar = btnRegistrar;
    }

    public CommandButton getBtnModificar() {
        return btnModificar;
    }

    public void setBtnModificar(CommandButton btnModificar) {
        this.btnModificar = btnModificar;
    }

    public CommandButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setBtnEliminar(CommandButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    public CommandButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public void setBtnLimpiar(CommandButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }
    
    public void onRowSelect(SelectEvent event) {
        this.selectedDependiente = (Dependiente) event.getObject();
        
        this.txtNombre.setValue(selectedDependiente.getDependientePK().getNombreDependiente());
        this.cmbSexo.setValue(selectedDependiente.getSexo());
        this.fechaNacimineto = selectedDependiente.getFechan();
        this.txtParentesco.setValue(selectedDependiente.getParentesco());
        
        this.btnRegistrar.setDisabled(true);
        this.btnModificar.setDisabled(false);
        this.btnEliminar.setDisabled(false);
        this.txtNombre.setDisabled(true);
    }
    
    public void limpiar() {
        this.txtNombre.setValue("");
        this.cmbSexo.setValue("");
        this.txtParentesco.setValue("");
        
        this.txtNombre.setDisabled(false);
        this.btnRegistrar.setDisabled(false);
        this.btnModificar.setDisabled(true);
        this.btnEliminar.setDisabled(true);
    }
    
    public void action_registrar(){
        try {
            Empleado empleado = (Empleado) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            
            DependientePK dependientePK = new DependientePK(empleado.getNss(), this.txtNombre.getValue().toString());
            
            Dependiente dependiente = new Dependiente();
            dependiente.setDependientePK(dependientePK);
            dependiente.setSexo(this.cmbSexo.getValue().toString());
            dependiente.setFechan(fechaNacimineto);
            dependiente.setParentesco(this.txtParentesco.getValue().toString());
            
            dependienteLogica.registrarDependiente(dependiente);
            listaDependientes = null;
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información de creación de dependiente:", "El dependiente fue registrado con éxito."));
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", ex.getMessage()));
        }
    }
    
    public void action_modificar(){
        try {
            Empleado empleado = (Empleado) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            
            DependientePK dependientePK = new DependientePK(empleado.getNss(), this.txtNombre.getValue().toString());
            
            Dependiente dependiente = new Dependiente();
            dependiente.setDependientePK(dependientePK);
            dependiente.setSexo(this.cmbSexo.getValue().toString());
            dependiente.setFechan(fechaNacimineto);
            dependiente.setParentesco(this.txtParentesco.getValue().toString());
            
            dependienteLogica.modificarDependiente(dependiente);
            listaDependientes = null;
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información de modificación de dependiente:", "El dependiente fue modificado con éxito."));
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", ex.getMessage()));
        }
    }
    
    public void action_eliminar(){
        try {
            Empleado empleado = (Empleado) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            
            DependientePK dependientePK = new DependientePK(empleado.getNss(), this.txtNombre.getValue().toString());
            
            Dependiente dependiente = new Dependiente();
            dependiente.setDependientePK(dependientePK);
            
            dependienteLogica.eliminarDependiente(dependiente);
            listaDependientes = null;
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información de eliminación de dependiente:", "El dependiente fue eliminado con éxito."));
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", ex.getMessage()));
        }
    }
}
