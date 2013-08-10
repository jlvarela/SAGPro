/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import pojoclass.Objetivo;
import pojoclass.Material;
import pojoclass.SelectedMaterial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import sessionbeans.MaterialFacadeLocal;
import sessionbeans.ObjetivoFacadeLocal;
import sessionbeans.ObjetivoMaterialFacadeLocal;

/**
 *
 * @author Jose
 */
@ManagedBean(name = "consultarObjetivoManagedBean")
@ViewScoped
public class ConsultarObjetivoManagedBean implements Serializable {
    @EJB
    private ObjetivoMaterialFacadeLocal objetivoMaterialFacade;
    @EJB
    private ObjetivoFacadeLocal objetivoFacade;
    
    @EJB
    private MaterialFacadeLocal materialFacade;
    
    private List<Material> materialList;
    private List<Objetivo> listaObjetivos;
    private Objetivo selectedObjetivo;
    private SimpleDateFormat sdf;
    private Material tempSelectedMaterial;
    private Integer cantSelectedMaterial;
    
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
        rellenarListas();
        tempSelectedMaterial = new Material();
    }
    
    private void rellenarListas(){
        // Lista de Materiales para selección
        List<entities.Material> materialEntitiesList = materialFacade.findAll();
        ArrayList<Material> materialArrayList = new ArrayList();
        
        // Mapping de clases Entity a POJO
        for (entities.Material matEntitie : materialEntitiesList){
            materialArrayList.add(util.MappingFromEntitieToPojo.materialFromEntityToPojo(matEntitie));
        }
        
        // Actualizar lista de materiales
        this.materialList = materialArrayList;
        
        /*Llenando la lista de objetivos*/
        List<entities.Objetivo> listaObjetivosEntities= objetivoFacade.findAll();
        ArrayList<Objetivo> objetivoList = new ArrayList();
        
        for (entities.Objetivo objetivoEntity : listaObjetivosEntities )
            objetivoList.add(util.MappingFromEntitieToPojo.objetivoFromEntityToPojo(objetivoEntity));
        
        listaObjetivos = objetivoList;
                
    }

    public Integer getCantSelectedMaterial() {
        return cantSelectedMaterial;
    }

    public void setCantSelectedMaterial(Integer cantSelectedMaterial) {
        this.cantSelectedMaterial = cantSelectedMaterial;
    }

    public Material getTempSelectedMaterial() {
        return tempSelectedMaterial;
    }

    public void setTempSelectedMaterial(Material tempSelectedMaterial) {
        this.tempSelectedMaterial = tempSelectedMaterial;
    }
    
    public List<Material> getListaMaterial() {
        return materialList;
    }

    public void setListaMaterial(List<Material> listaMaterial) {
        this.materialList = listaMaterial;
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
    
    /**
     * Mostrar la prioridad de los objetivos como nombre.
     * @param prioridad Prioridad ingresada
     * @return  String  Nombre de la prioridad
     */
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
    
    public void handleRowSelect(SelectEvent event){
        Objetivo obj = (Objetivo) event.getObject();
        List<entities.ObjetivoMaterial> omList = objetivoMaterialFacade.buscarPorObjetivo(obj.getCodObjetivo());
        ArrayList<SelectedMaterial> smArray = new ArrayList();
        for(entities.ObjetivoMaterial om : omList){
            smArray.add(util.MappingFromEntitieToPojo.selectedMaterialFromMaterialObjetivoToPojo(om));
        }
        obj.setMaterialList(smArray);
        selectedObjetivo = obj;
    }
    
    public String verPrioridad(){
        if (selectedObjetivo == null)
            return null;
        return verPrioridadTabla(selectedObjetivo.getPrioridad());
    }
    
    public void removeMaterialObjetivo(SelectedMaterial item){
        selectedObjetivo.removeMaterial(item);
    }
    
    public void addMaterialObjetivo(){
        SelectedMaterial sm = new SelectedMaterial();
        sm.setCodMaterial(tempSelectedMaterial.getCodMaterial());
        if ( ! selectedObjetivo.isMaterialInObjetivo(sm) ){
            sm.setCantidad(cantSelectedMaterial);
            selectedObjetivo.addMaterial(sm);
        }
    }
}
