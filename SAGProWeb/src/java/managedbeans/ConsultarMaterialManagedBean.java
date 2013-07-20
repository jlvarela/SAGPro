/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Material;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sessionbeans.MaterialFacadeLocal;

/**
 *
 * @author Gary
 */
@ManagedBean (name = "consultarMaterialManagedBean")
@ViewScoped
public class ConsultarMaterialManagedBean {

    /**
     * Creates a new instance of ConsultarMaterialManagedBean
     */
    public ConsultarMaterialManagedBean() {
    }
    
     @EJB
    private MaterialFacadeLocal materialFacade;

    /**
     * Creates a new instance of MaterialManagedBean
     */

    @PostConstruct
    public void init() {
        String[] valuesVentas = materialFacade.getValuesVentaMaterial();
        String[] valuesProduc = materialFacade.getValuesProducMaterial();
        if (valuesVentas != null && valuesProduc != null){
            medidasVentaMaterial = valuesVentas;
            medidasProduccionMaterial = valuesProduc;
        }
        
        listaMateriales = materialFacade.findAll();
    }

    public List<Material> getListaMateriales() {
        return listaMateriales;
    }

    public void setListaMateriales(List<Material> listaMateriales) {
        this.listaMateriales = listaMateriales;
    }
    private String nombreMaterial;
    private String medidaProduccionMaterial;
    private String medidaVentaMaterial;
    private String[] medidasVentaMaterial;
    private String[] medidasProduccionMaterial;
    private List<Material> listaMateriales;
    
    private Material selectedMaterial;
    private Material[] selectedMaterials;

    public Material getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(Material selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }

    public Material[] getSelectedMaterials() {
        return selectedMaterials;
    }

    public void setSelectedMaterials(Material[] selectedMaterials) {
        this.selectedMaterials = selectedMaterials;
    }
    
    

    public String[] getMedidasVentaMaterial() {
        return medidasVentaMaterial;
    }

    public void setMedidasVentaMaterial(String[] medidasVentaMaterial) {
        this.medidasVentaMaterial = medidasVentaMaterial;
    }

    public String[] getMedidasProduccionMaterial() {
        return medidasProduccionMaterial;
    }

    public void setMedidasProduccionMaterial(String[] medidasProduccionMaterial) {
        this.medidasProduccionMaterial = medidasProduccionMaterial;
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }

    public String getMedidaProduccionMaterial() {
        return medidaProduccionMaterial;
    }

    public void setMedidaProduccionMaterial(String medidaProduccionMaterial) {
        this.medidaProduccionMaterial = medidaProduccionMaterial;
    }

    public String getMedidaVentaMaterial() {
        return medidaVentaMaterial;
    }

    public void setMedidaVentaMaterial(String medidaVentaMaterial) {
        this.medidaVentaMaterial = medidaVentaMaterial;
    }
}
