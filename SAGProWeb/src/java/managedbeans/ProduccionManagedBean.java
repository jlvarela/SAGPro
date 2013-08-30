/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Material;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import sessionbeans.MaterialFacadeLocal;
import sessionbeans.ProduccionDiariaFacadeLocal;

/**
 *
 * @author Jose
 */
@Named(value = "produccionManagedBean")
@RequestScoped
public class ProduccionManagedBean {

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
   

    /**
     * Creates a new instance of ProduccionManagedBean
     */
    public ProduccionManagedBean() {
    }
    
    @PostConstruct
    public void init(){
        listaMateriales = materialFacade.findAll();
    }

    public List<Material> getListaMateriales() {
        return listaMateriales;
    }

    public void setListaMateriales(List<Material> listaMateriales) {
        this.listaMateriales = listaMateriales;
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
    
    public void ingresarProduccion(){
        // Validar entradas
        // Fin Validar entradas
        int resp = produccionDiariaFacade.agregarProduccionDiaria(Integer.parseInt(this.codMaterial), Integer.parseInt(this.cantidad));
        FacesContext fcontext = FacesContext.getCurrentInstance();
        if (resp == 0) {
            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Producción agregado con éxito"));
        } else if (resp == -1) {
            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ha ocurrido un error"));
        }        
    }
}
