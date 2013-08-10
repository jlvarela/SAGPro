/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import pojoclass.Objetivo;
import pojoclass.Material;
/*import entities.Material;*/
/*import entities.Objetivo;*/
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sessionbeans.MaterialFacadeLocal;
import sessionbeans.ObjetivoFacadeLocal;

/**
 *
 * @author Jose
 */
@ManagedBean(name = "consultarObjetivoManagedBean")
@ViewScoped
public class ConsultarObjetivoManagedBean implements Serializable {
    @EJB
    private ObjetivoFacadeLocal objetivoFacade;
    
    @EJB
    private MaterialFacadeLocal materialFacade;
    
    private List<Material> listaMaterialObjetivo;
    private List<Material> listaMaterial;
    private List<Objetivo> listaObjetivos;
    private Objetivo selectedObjetivo;
    private SimpleDateFormat sdf;
    
    
    

    /**
     * Creates a new instance of ConsultarObjetivoManagedBean
     */
    public ConsultarObjetivoManagedBean() {
        sdf = new SimpleDateFormat("dd/MM/YYYY");
        
    }
    
    /**
     * Obtener la lista de todos los objetivos del sistema.
     * 
     */
    @PostConstruct
    public void init(){
        
        /*Llenando la lista de objetivos*/
        List<entities.Objetivo> listaObjetivosEntities= objetivoFacade.findAll();
        ArrayList<Objetivo> objetivoList = new ArrayList();
        
        for (entities.Objetivo objetivoEntity : listaObjetivosEntities )
            objetivoList.add(util.MappingFromEntitieToPojo.objetivoFromEntityToPojo(objetivoEntity));
        
        listaObjetivos = objetivoList;
        
        /*Llenando la lista de materiales*/
        List <entities.Material> listaMaterialEntities= materialFacade.findAll();
        ArrayList<Material> materialList =new ArrayList();
        
        for(entities.Material materialEntity :listaMaterialEntities)
            materialList.add(util.MappingFromEntitieToPojo.materialFromEntityToPojo(materialEntity));
        
        listaMaterial = materialList;
        
        
        
        
        
    }

    public List<Material> getListaMaterialObjetivo() {
        return listaMaterialObjetivo;
    }

    public void setListaMaterialObjetivo(List<Material> listaMaterialObjetivo) {
        this.listaMaterialObjetivo = listaMaterialObjetivo;
    }

    
    
    public List<Material> getListaMaterial() {
        return listaMaterial;
    }

    public void setListaMaterial(List<Material> listaMaterial) {
        this.listaMaterial = listaMaterial;
    }

    public List<Objetivo> getListaObjetivos() {
        return listaObjetivos;
    }

    public void setListaObjetivos(List<Objetivo> listaObjetivos) {
        this.listaObjetivos = listaObjetivos;
    }

    public Objetivo getSelectedObjetivo() {
        return selectedObjetivo;
    }

    public void setSelectedObjetivo(Objetivo selectedObjetivo) {
        this.selectedObjetivo = selectedObjetivo;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }
    
   public String verPrioridadTabla(Short prioridad){
        if(prioridad==0){
            return "Baja";
        }else if(prioridad==1){
            return "Media";
        }else if(prioridad==2){
            return "Alta";
        }else if(prioridad==null) {
            return null;
        }
        return null;
        
    }
   
   public String verPrioridadDatos(Short prioridad){
        if(prioridad==0 && selectedObjetivo != null){
            System.out.println(selectedObjetivo.getPrioridad());
            System.out.println();
            return "Baja";
        }else if(prioridad==1){
            return "Media";
        }else if(prioridad==2){
            return "Alta";
        }else if(prioridad==null) {
            return null;
        }
        return null;
        
    }
   
   public void Imprimir(){
       System.out.println(selectedObjetivo.getNombre());
   }
   
   
}
