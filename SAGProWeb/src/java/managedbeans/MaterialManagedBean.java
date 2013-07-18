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

/**
 *
 * @author Jose
 */
@Named(value = "materialManagedBean")
@RequestScoped
public class MaterialManagedBean {
       
    @EJB
    private MaterialFacadeLocal materialFacade;

    /**
     * Creates a new instance of MaterialManagedBean
     */
    public MaterialManagedBean() {
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

    public void ingresarMaterial() {
        System.out.println(nombreMaterial+medidaProduccionMaterial+medidaVentaMaterial);
        int resp = materialFacade.agregarMaterial(nombreMaterial, medidaProduccionMaterial, medidaVentaMaterial);
        FacesContext fcontext = FacesContext.getCurrentInstance();
        if (resp == 0) {
            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Material agregado con éxito"));
        } else if (resp == -1) {
            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Material ya ingresado"));
        }
    }
    
    public void materialSelectCantidadListener(ValueChangeEvent event){
        String idMatSelect = (String) event.getNewValue();
        for(Material material: listaMateriales){
            if(material.getCodMaterial().equals(Integer.parseInt(idMatSelect))){
                medidaProduccionMaterial = material.getMedidaProduccionMaterial();
                System.out.println("new value " + medidaProduccionMaterial);
                break;
            }
        }
    }
}
