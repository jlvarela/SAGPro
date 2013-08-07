/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import java.io.Serializable;
import pojoclass.Material;
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
@ManagedBean (name = "ingresarMaterialManagedBean")
@ViewScoped
public class IngresarMaterialManagedBean implements Serializable{

    /**
     * Creates a new instance of IngresarMaterialManagedBean
     */
    public IngresarMaterialManagedBean() {
    }
    
    @EJB
    private MaterialFacadeLocal materialFacade;
    
    private String[] medidasVentaMaterial;
    private String[] medidasProduccionMaterial;
    private Material newMaterial;

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
        
        newMaterial = new Material();
        
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

    public Material getNewMaterial() {
        return newMaterial;
    }

    public void setNewMaterial(Material newMaterial) {
        this.newMaterial = newMaterial;
    }
    
    public void ingresarMaterial() {
        System.out.println(newMaterial != null);
        int resp = materialFacade.agregarMaterial(newMaterial.getNombreMaterial()
                , newMaterial.getMedidaProduccionMaterial()
                , newMaterial.getMedidaVentaMaterial());
        FacesContext fcontext = FacesContext.getCurrentInstance();
        if (resp == 0) {
            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Material agregado con éxito"));
        } else if (resp == -1) {
            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Material ya ingresado"));
        }
    }
        
    /**
     * Mapping entre clase Entities.Material y pojoclass.Material
     * @param materialEntity    Entities.Material a Mappear
     * @return pojoclass.material   Entidad POJO mapeada
     */
    private pojoclass.Material fromEntityToPojo(entities.Material materialEntity){
        pojoclass.Material materialPojo = new pojoclass.Material();
        
        materialPojo.setCodMaterial(materialEntity.getCodMaterial());
        materialPojo.setNombreMaterial(materialEntity.getNombreMaterial());
        materialPojo.setMedidaProduccionMaterial(materialEntity.getMedidaProduccionMaterial());
        materialPojo.setMedidaVentaMaterial(materialEntity.getMedidaVentaMaterial());
        
        return materialPojo;
    }
}
