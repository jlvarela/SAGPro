/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import pojoclass.ProduccionDiaria;
import entities.Material;
import java.io.Serializable;
//import entities.ProduccionDiaria;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import sessionbeans.MaterialFacadeLocal;
import sessionbeans.ProduccionDiariaFacadeLocal;

/**
 *
 * @author Gary
 */
@ManagedBean(name = "consultarProduccionManagedBean")
@ViewScoped
public class ConsultarProduccionManagedBean implements Serializable {

    @EJB
    private ProduccionDiariaFacadeLocal produccionDiariaFacade;
    @EJB
    private MaterialFacadeLocal materialFacade;
    private List<Material> listaMateriales;
    private List<ProduccionDiaria> listaProduccionDiaria;
    private ProduccionDiaria selectedProduccionDiaria = new ProduccionDiaria();;
    private String fecha;
    Date f;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Creates a new instance of ProduccionManagedBean
     */
    public ConsultarProduccionManagedBean() {
    }

    @PostConstruct
    public void init() {
        f = new Date();
        fecha = df.format(f);
        /**
         * Llenando lista de produccion para ser mostrada en tabla*
         */
        List<entities.ProduccionDiaria> listaProduccionDiariaEntities = produccionDiariaFacade.buscarPorFecha(f);

        ArrayList<ProduccionDiaria> produccionDiariaList = new ArrayList();

        for (entities.ProduccionDiaria produccionDiariaEntity : listaProduccionDiariaEntities) {
            produccionDiariaList.add(util.MappingFromEntitieToPojo.produccionFromEntityToPojo(produccionDiariaEntity));
        }

        listaProduccionDiaria = produccionDiariaList;
        selectedProduccionDiaria = new ProduccionDiaria();
        
    }

    public ProduccionDiaria getSelectedProduccionDiaria() {
        return selectedProduccionDiaria;
    }

    public void setSelectedProduccionDiaria(ProduccionDiaria selectedProduccionDiaria) {
        this.selectedProduccionDiaria = selectedProduccionDiaria;
    }

    public List<Material> getListaMateriales() {
        return listaMateriales;
    }

    public void setListaMateriales(List<Material> listaMateriales) {
        this.listaMateriales = listaMateriales;
    }

    public List<ProduccionDiaria> getListaProduccionDiaria() {
        return listaProduccionDiaria;
    }

    public void setListaProduccionDiaria(List<ProduccionDiaria> listaProduccionDiaria) {
        this.listaProduccionDiaria = listaProduccionDiaria;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


    /**
     * Modifica la cantidad 
     *
     */
    public void modificarProduccion() {
        int resp;

        resp = produccionDiariaFacade.editarProduccion(selectedProduccionDiaria.getCodMaterial(), selectedProduccionDiaria.getFecha(), selectedProduccionDiaria.getCantidad());
        FacesContext fcontext = FacesContext.getCurrentInstance();
        if (resp == 0) {
            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Material editado con éxito"));
        } else if (resp == -1) {
            selectedProduccionDiaria = null;
            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error en la edición del material"));
        }
    }

    /**
     * Genera una lista de producciones ingresadas para una fecha dada.
     *
     */
    public void cambiarFecha() {

        System.out.println("y la fecha es " + fecha);
        try {
            f = new Date(df.parse(fecha).getTime());
            List<entities.ProduccionDiaria> listaProduccionDiariaEntities = produccionDiariaFacade.buscarPorFecha(f);

            ArrayList<ProduccionDiaria> produccionDiariaList = new ArrayList();

            for (entities.ProduccionDiaria produccionDiariaEntity : listaProduccionDiariaEntities) {
                System.out.println("cant"+produccionDiariaEntity.getProduccionMaterial());
                produccionDiariaList.add(util.MappingFromEntitieToPojo.produccionFromEntityToPojo(produccionDiariaEntity));
            }

            listaProduccionDiaria = produccionDiariaList;

        } catch (ParseException ex) {
            Logger.getLogger(ConsultarProduccionManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        selectedProduccionDiaria = new ProduccionDiaria();;
        
    }
}
