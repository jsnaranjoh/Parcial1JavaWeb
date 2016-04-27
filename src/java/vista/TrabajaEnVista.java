/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import logica.EmpleadoLogicaLocal;
import logica.ProyectoLogicaLocal;
import logica.TrabajaEnLogicaLocal;
import modelo.Empleado;
import modelo.Proyecto;
import modelo.TrabajaEn;
import modelo.TrabajaEnPK;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;

/**
 *
 * @author jsnar
 */
@Named(value = "trabajaEnVista")
@RequestScoped
public class TrabajaEnVista {

    @EJB
    private EmpleadoLogicaLocal empleadoLogica;
    
    @EJB
    private ProyectoLogicaLocal proyectoLogica;
    
    @EJB
    private TrabajaEnLogicaLocal trabajaEnLogica;
    
    private SelectOneMenu cmbEmpleados;
    private ArrayList<SelectItem> itemsEmpleados;
   
    private SelectOneMenu cmbProyectos;
    private ArrayList<SelectItem> itemsProyectos;
    
    private InputText txtHorasTrabajadas;
    
    private CommandButton btnRegistrar;
    
    private List<TrabajaEn> listaTrabajaEn;
    private TrabajaEn selectedTrabajaEn;
    
    /**
     * Creates a new instance of TrabajaEnVista
     */
    public TrabajaEnVista() {
    }

    public SelectOneMenu getCmbEmpleados() {
        return cmbEmpleados;
    }

    public void setCmbEmpleados(SelectOneMenu cmbEmpleados) {
        this.cmbEmpleados = cmbEmpleados;
    }

    public ArrayList<SelectItem> getItemsEmpleados() {
        try {
            List<Empleado> listaE = empleadoLogica.consultarxTipo("CONTRATISTA");
            itemsEmpleados = new ArrayList<>();
            
            for(Empleado e: listaE){
                itemsEmpleados.add(new SelectItem(e.getNss(), e.getNombrep() + " " + e.getApellido()));
            }
        } catch (Exception ex) {
            Logger.getLogger(TrabajaEnVista.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return itemsEmpleados;
    }

    public void setItemsEmpleados(ArrayList<SelectItem> itemsEmpleados) {
        this.itemsEmpleados = itemsEmpleados;
    }

    public SelectOneMenu getCmbProyectos() {
        return cmbProyectos;
    }

    public void setCmbProyectos(SelectOneMenu cmbProyectos) {
        this.cmbProyectos = cmbProyectos;
    }

    public ArrayList<SelectItem> getItemsProyectos() {
        try {
            List<Proyecto> listaP = proyectoLogica.consultarTodos();
            itemsProyectos = new ArrayList<>();
            
            for(Proyecto p: listaP){
                itemsProyectos.add(new SelectItem(p.getNumerop(), p.getNombrep()));
            }
        } catch (Exception ex) {
            Logger.getLogger(TrabajaEnVista.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return itemsProyectos;
    }

    public void setItemsProyectos(ArrayList<SelectItem> itemsProyectos) {
        this.itemsProyectos = itemsProyectos;
    }

    public InputText getTxtHorasTrabajadas() {
        return txtHorasTrabajadas;
    }

    public void setTxtHorasTrabajadas(InputText txtHorasTrabajadas) {
        this.txtHorasTrabajadas = txtHorasTrabajadas;
    }

    public CommandButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public void setBtnRegistrar(CommandButton btnRegistrar) {
        this.btnRegistrar = btnRegistrar;
    }

    public List<TrabajaEn> getListaTrabajaEn() {
        if(listaTrabajaEn == null) {
            try {
                listaTrabajaEn = trabajaEnLogica.consultarxTipoEmpleado("CONTRATISTA");
            } catch (Exception ex) {
                Logger.getLogger(TrabajaEnVista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaTrabajaEn;
    }

    public void setListaTrabajaEn(List<TrabajaEn> listaTrabajaEn) {
        this.listaTrabajaEn = listaTrabajaEn;
    }

    public TrabajaEn getSelectedTrabajaEn() {
        return selectedTrabajaEn;
    }

    public void setSelectedTrabajaEn(TrabajaEn selectedTrabajaEn) {
        this.selectedTrabajaEn = selectedTrabajaEn;
    }
    
    // Limpia los campos
    public void limpiar(){
        this.cmbEmpleados.setValue("");
        this.cmbProyectos.setValue("");
        this.txtHorasTrabajadas.setValue("");
    }
    
    //Método registrar
    public void action_registrar(){
        try {
            Empleado empleado = new Empleado();
            empleado.setNss(Long.parseLong(this.cmbEmpleados.getValue().toString()));
            
            Proyecto proyecto = new Proyecto();
            proyecto.setNumerop(Long.parseLong(this.cmbProyectos.getValue().toString()));
            
            TrabajaEnPK trabajaEnPK = new TrabajaEnPK(empleado.getNss(), proyecto.getNumerop());
            
            TrabajaEn trabajaEn = new TrabajaEn();
            trabajaEn.setTrabajaEnPK(trabajaEnPK);
            trabajaEn.setEmpleado(empleado);
            trabajaEn.setProyecto(proyecto);
            trabajaEn.setHoras(Integer.parseInt(this.txtHorasTrabajadas.getValue().toString()));
            
            trabajaEnLogica.registrarTrabajaEn(trabajaEn);
            listaTrabajaEn = null;
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información de creación de trabajaEn", "El TrabajaEn fue registrado con éxito."));
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error.", ex.getMessage()));
        }
    }
}
