/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Material;
import entities.ProduccionDiaria;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.faces.event.ValueChangeEvent;
import sessionbeans.MaterialFacadeLocal;
import sessionbeans.ProduccionDiariaFacadeLocal;

/**
 *
 * @author Gary
 */
@ManagedBean(name = "consultarProduccionManagedBean")
@ViewScoped
public class ConsultarProduccionManagedBean {

     public String getCodMaterial() {
        return codMaterial;
    }

    public void setCodMaterial(String codMaterial) {
        this.codMaterial = codMaterial;
    }
    @EJB
    private ProduccionDiariaFacadeLocal produccionDiariaFacade;
    @EJB
    private MaterialFacadeLocal materialFacade;
    
    private List<Material> listaMateriales;
    private String cantidad;
    private Material materialSelected;
    private String codMaterial;
    private List<ProduccionDiaria> listaProduccionDiaria;
    private ProduccionDiaria selectedProduccionDiaria;
    private List<ProduccionDiaria> selectedProduccionesDiarias;
    private String fecha = "07/25/2013";

    /**
     * Creates a new instance of ProduccionManagedBean
     */
    public ConsultarProduccionManagedBean() {
    }
    
    @PostConstruct
    public void init()
    {
        Date f = new Date();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
         try {
             f = new Date(df.parse(fecha).getTime());
         } catch (ParseException ex) {
             Logger.getLogger(ConsultarProduccionManagedBean.class.getName()).log(Level.SEVERE, null, ex);
         }
        //listaMateriales = materialFacade.findAll();
        listaProduccionDiaria = produccionDiariaFacade.buscarPorFecha(f);
    }

    public ProduccionDiaria getSelectedProduccionDiaria() {
        return selectedProduccionDiaria;
    }

    public void setSelectedProduccionDiaria(ProduccionDiaria selectedProduccionDiaria) {
        this.selectedProduccionDiaria = selectedProduccionDiaria;
    }

    public List<ProduccionDiaria> getSelectedProduccionesDiarias() {
        return selectedProduccionesDiarias;
    }

    public void setSelectedProduccionesDiarias(List<ProduccionDiaria> selectedProduccionesDiarias) {
        this.selectedProduccionesDiarias = selectedProduccionesDiarias;
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

    
    
    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
    
    public void materialSelectCantidadListener(ValueChangeEvent event){
        String idMatSelect = (String) event.getNewValue();
        for(Material material: listaMateriales){
            if(material.getCodMaterial().equals(Integer.parseInt(idMatSelect))){
                this.materialSelected = material;
                System.out.println("prueba: " + material.getCodMaterial());
                codMaterial = idMatSelect;
                break;
            }
        }
    }

    public Material getMaterialSelected() {
        return materialSelected;
    }

    public void setMaterialSelected(Material materialSelected) {
        this.materialSelected = materialSelected;
    }



    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public void modificarProduccion(){
        int resp;
        
        resp = produccionDiariaFacade.editarProduccion(selectedProduccionDiaria.getProduccionDiariaPK().getCodMaterial(), selectedProduccionDiaria.getProduccionDiariaPK().getFechaDiariaEstadistica(), selectedProduccionDiaria.getProduccionMaterial());
        FacesContext fcontext = FacesContext.getCurrentInstance();
        if (resp == 0) {
            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Material editado con éxito"));
        } else if (resp == -1) {
            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error en la edición del material"));
        }
    }
   
}
