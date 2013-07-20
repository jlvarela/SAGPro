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
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import sessionbeans.MaterialFacadeLocal;

/**
 *
 * @author Jose
 */
@Named(value = "crearObjetivoManagedBean")
@ViewScoped
public class CrearObjetivoManagedBean {
    @EJB
    private MaterialFacadeLocal materialFacade;
    
    private class SelectedMaterial{
        private String idMaterial;
        private int cantidad;
        public SelectedMaterial () {
            
        }

        public String getIdMaterial() {
            return idMaterial;
        }

        public void setIdMaterial(String idMaterial) {
            this.idMaterial = idMaterial;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }
        
    }
    
    private List<SelectedMaterial> selectedMateriales;
    private List<Material> listaMateriales;
    private String nombreObjetivo;
    private String initialDate;
    private String finishDate;
    private String codMaterialSelected;
    private String cantMaterialSelected;

    public String getCodMaterialSelected() {
        return codMaterialSelected;
    }

    public void setCodMaterialSelected(String codMaterialSelected) {
        this.codMaterialSelected = codMaterialSelected;
    }

    public String getCantMaterialSelected() {
        return cantMaterialSelected;
    }

    public void setCantMaterialSelected(String cantMaterialSelected) {
        this.cantMaterialSelected = cantMaterialSelected;
    }
    
    public String getNombreObjetivo() {
        return nombreObjetivo;
    }

    public void setNombreObjetivo(String nombreObjetivo) {
        this.nombreObjetivo = nombreObjetivo;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }
       
    public List<SelectedMaterial> getSelectedMateriales() {
        return selectedMateriales;
    }

    public void setSelectedMateriales(List<SelectedMaterial> selectedMateriales) {
        this.selectedMateriales = selectedMateriales;
    }

    public List<Material> getListaMateriales() {
        return listaMateriales;
    }

    public void setListaMateriales(List<Material> listaMateriales) {
        this.listaMateriales = listaMateriales;
    }
    
    
    
    /**
     * Creates a new instance of CrearObjetivoManagedBean
     */
    public CrearObjetivoManagedBean() {
    }
    
    @PostConstruct
    public void init (){
        listaMateriales = materialFacade.findAll();
    }
    
}
