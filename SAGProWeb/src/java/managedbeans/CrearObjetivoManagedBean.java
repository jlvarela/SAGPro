/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Material;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sessionbeans.MaterialFacadeLocal;

/**
 *
 * @author Jose
 */
@ManagedBean(name = "crearObjetivoManagedBean")
@ViewScoped
public class CrearObjetivoManagedBean implements Serializable{
    @EJB
    private MaterialFacadeLocal materialFacade;
    
    public class SelectedMaterial{
        private String idMaterial;
        public int cantidad;
        public String nombreMaterial;
        
        public SelectedMaterial () {
            
        }

        public String getNombreMaterial() {
            return nombreMaterial;
        }

        public void setNombreMaterial(String nombreMaterial) {
            this.nombreMaterial = nombreMaterial;
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
    private Integer cantMaterialSelected;

    public String getCodMaterialSelected() {
        return codMaterialSelected;
    }

    public void setCodMaterialSelected(String codMaterialSelected) {
        this.codMaterialSelected = codMaterialSelected;
    }

    public Integer getCantMaterialSelected() {
        return cantMaterialSelected;
    }

    public void setCantMaterialSelected(Integer cantMaterialSelected) {
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
        selectedMateriales = new ArrayList<>();
        cantMaterialSelected = 0;
    }
    
    public void addMaterialObjetivo(){
        SelectedMaterial newmaterial = new SelectedMaterial();
        System.out.println(codMaterialSelected + cantMaterialSelected);
        newmaterial.setIdMaterial(codMaterialSelected);
        newmaterial.setNombreMaterial(codMaterialSelected);
        newmaterial.setCantidad(cantMaterialSelected);
        selectedMateriales.add(newmaterial);
    }
    
}
