/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vista;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import logica.SesionLogicaLocal;
import modelo.Empleado;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.password.Password;

/**
 *
 * @author jsnar
 */
@ManagedBean (name = "sesionVista")
@RequestScoped
public class SesionVista {
    
    @EJB
    private SesionLogicaLocal sesionLogica;
    
    private InputText txtUsuario;
    private Password txtClave;
    private CommandButton btnIngresar;

    /**
     * Creates a new instance of SesionVista
     */
    public SesionVista() {
    }

    public InputText getTxtUsuario() {
        return txtUsuario;
    }

    public void setTxtUsuario(InputText txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    public Password getTxtClave() {
        return txtClave;
    }

    public void setTxtClave(Password txtClave) {
        this.txtClave = txtClave;
    }

    public CommandButton getBtnIngresar() {
        return btnIngresar;
    }

    public void setBtnIngresar(CommandButton btnIngresar) {
        this.btnIngresar = btnIngresar;
    }
    
    public void funcion_ingresar(){
        try {
            String urlP, urlC;
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext extContext = context.getExternalContext();
            
            urlP = extContext.encodeActionURL(context.getApplication().
                    getViewHandler().getActionURL(context, "/planta/gestionProyectos.xhtml"));
            
            urlC = extContext.encodeActionURL(context.getApplication().
                    getViewHandler().getActionURL(context, "/contratista/gestionDependientes.xhtml"));
            
            Long documento = null;
            try {
                documento = Long.parseLong(txtUsuario.getValue().toString());
            } catch (Exception ex) {}
            String clave = txtClave.getValue().toString();
            
            sesionLogica.buscarCamposInvalidosOVacios(documento, clave);
            Empleado e = sesionLogica.iniciarSesionEmpleado(documento, clave);
            if(e.getTipo().equals("PLANTA")){
                extContext.getSessionMap().put("tipo", "planta");
                extContext.getSessionMap().put("usuario", e);
                extContext.redirect(urlP);
            } else {
                extContext.getSessionMap().put("tipo", "contratista");
                extContext.getSessionMap().put("usuario", e);
                extContext.redirect(urlC);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage()));
        }
    }
    
    public void cerrarSesion_action()
    {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext extContext= context.getExternalContext();
            extContext.getSessionMap().remove("tipo");
            extContext.getSessionMap().remove("usuario");
            String url=extContext.encodeActionURL(context.getApplication().getViewHandler().getActionURL(context,"/index.xhtml"));
            extContext.redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(SesionVista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void modificarClave_action() {
        Empleado empleado = (Empleado) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        
        try {
            sesionLogica.modificarClave(empleado, this.txtClave.getValue().toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información de modificación de clave:", "La clave fue modificada con éxito."));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", ex.getMessage()));
        }
    }
}
