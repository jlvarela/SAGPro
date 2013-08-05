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
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sessionbeans.EstadisticaMensualFacadeLocal;
import sessionbeans.MaterialFacadeLocal;

/**
 *
 * @author Marco
 */

@ManagedBean(name = "consultarEstadisticasManagedBean")
@ViewScoped
public class ConsultarEstadisticasManagedBean implements Serializable {

     @EJB
     private EstadisticaMensualFacadeLocal estadisticaMensualFacade;
     
     @EJB
     private MaterialFacadeLocal materialFacade;
     
     private List<Material> listaMateriales;

    public List<Material> getListaMateriales() {
        return listaMateriales;
    }

    public void setListaMateriales(List<Material> listaMateriales) {
        this.listaMateriales = listaMateriales;
    }
     
     
     
    /**
     * Creates a new instance of ConsultarEstadisticasManagedBean
     */
    public ConsultarEstadisticasManagedBean() {
    }
    
    @PostConstruct
    public void init(){
        listaMateriales=materialFacade.findAll();
        
        
    }
}
