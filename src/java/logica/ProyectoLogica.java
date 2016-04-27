/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.File;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jxl.Sheet;
import jxl.Workbook;
import modelo.Departamento;
import modelo.Proyecto;
import persistencia.DepartamentoFacadeLocal;
import persistencia.ProyectoFacadeLocal;

/**
 *
 * @author jsnar
 */
@Stateless
public class ProyectoLogica implements ProyectoLogicaLocal {

    @EJB
    ProyectoFacadeLocal proyectoDAO;
    
    @EJB
    DepartamentoFacadeLocal departamentoDAO;
    
    @Override
    public void registrarProyecto(Proyecto proyecto) throws Exception {
        Proyecto objProyecto = proyectoDAO.find(proyecto.getNumerop());
        if(objProyecto != null){
            throw new Exception("El proyecto ya existe.");
        }
        else{
            proyectoDAO.create(proyecto);
        }
    }
    
    @Override
    public String importarProyectos(String archivo) throws Exception {
        Workbook archivoExcel = Workbook.getWorkbook(new File(archivo));
        Sheet hoja = archivoExcel.getSheet(0);
        int numFilas = hoja.getRows();
        
        Integer proyectosRegistrados = 0;
        Integer proyectosExistentes = 0;
        Integer departamentoRegistrados = 0;
        
        for(int fila = 1; fila < numFilas; fila++){
            Proyecto proyecto = new Proyecto();
            
            String nombreProyecto = hoja.getCell(0, fila).getContents();
            proyecto.setNombrep(nombreProyecto);
            proyecto.setNumerop(new Long(0));
            String nombreDepartamento = hoja.getCell(1, fila).getContents();
            Departamento departamento = departamentoDAO.consultarxNombre(nombreDepartamento);
            if(departamento == null) {
                departamento = new Departamento();
                departamento.setNombred(nombreDepartamento);
                departamento.setNumerod(new Long(0));
                departamento.setNssgte(new BigInteger("12345"));
                departamento.setFechaninicgt(new Date());
                departamentoDAO.create(departamento);
                departamentoRegistrados++;
            }
            proyecto.setNumd(departamento);
            
            if(proyectoDAO.consultarxNombre(nombreProyecto) == null){
                proyectoDAO.create(proyecto);
                proyectosRegistrados++;
            }
            else{
                proyectosExistentes++;
            }
        }
        return "Se importaron " + proyectosRegistrados + " proyectos, ya existían " + proyectosExistentes + " proyectos y se registraron " + departamentoRegistrados + " departamentos.";
    }

    @Override
    public List<Proyecto> consultarTodos() throws Exception {
        return proyectoDAO.findAll();
    }
}
