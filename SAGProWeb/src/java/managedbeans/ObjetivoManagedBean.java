/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Material;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import sessionbeans.MaterialFacadeLocal;
import sessionbeans.ObjetivoFacadeLocal;

/**
 *
 * @author Jose
 */
@Named(value = "objetivoManagedBean")
@RequestScoped
public class ObjetivoManagedBean {
    @EJB
    private ObjetivoFacadeLocal objetivoFacade;
    @EJB
    private MaterialFacadeLocal materialFacade;

    /**
     * Creates a new instance of ObjetivoManagedBean
     */
    
    private String nombreObjetivo;
    private List<Material> listaMateriales;
    private List<Material> selectedMateriales;
    

    public String getNombreObjetivo() {
        return nombreObjetivo;
    }

    public void setNombreObjetivo(String nombreObjetivo) {
        this.nombreObjetivo = nombreObjetivo;
    }

    public List<Material> getListaMateriales() {
        return listaMateriales;
    }

    public void setListaMateriales(List<Material> listaMateriales) {
        this.listaMateriales = listaMateriales;
    }

    public List<Material> getSelectedMateriales() {
        return selectedMateriales;
    }

    public void setSelectedMateriales(List<Material> selectedMateriales) {
        this.selectedMateriales = selectedMateriales;
    }
    
    
    
    public ObjetivoManagedBean() {
    }
}
