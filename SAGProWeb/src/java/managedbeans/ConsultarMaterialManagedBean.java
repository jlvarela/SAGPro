/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Material;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import sessionbeans.MaterialFacadeLocal;

/**
 *
 * @author Gary
 */
@ManagedBean(name = "consultarMaterialManagedBean")
@ViewScoped
public class ConsultarMaterialManagedBean implements Serializable {

    @EJB
    private MaterialFacadeLocal materialFacade;
    
    private String codMaterial;
    private String nombreMaterial;
    private String medidaProduccionMaterial;
    private String medidaVentaMaterial;
    private String[] medidasVentaMaterial;
    private String[] medidasProduccionMaterial;
    private List<Material> listaMateriales;
    private Material selectedMaterial;
    private List<Material> selectedMaterials;
    private String text; 

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCodMaterial() {
        return codMaterial;
    }

    public void setCodMaterial(String codMaterial) {
        this.codMaterial = codMaterial;
    }


    
    
    public List<Material> getListaMateriales() {
        return listaMateriales;
    }

    public void setListaMateriales(List<Material> listaMateriales) {
        this.listaMateriales = listaMateriales;
    }

    public Material getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(Material selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }

    public List<Material>  getSelectedMaterials() {
        return selectedMaterials;
    }

    public void setSelectedMaterials(List<Material>  selectedMaterials) {
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

    public ConsultarMaterialManagedBean() {
    }

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
    
     public void modificarMaterial(){
        int resp;
        resp = materialFacade.editarMaterial(selectedMaterial.getCodMaterial().toString(),selectedMaterial.getNombreMaterial(),selectedMaterial.getMedidaProduccionMaterial(),selectedMaterial.getMedidaVentaMaterial());
        FacesContext fcontext = FacesContext.getCurrentInstance();
        if (resp == 0) {
            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Material editado con éxito"));
        } else if (resp == -1) {
            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error en la edición del material"));
        }
    }
}
