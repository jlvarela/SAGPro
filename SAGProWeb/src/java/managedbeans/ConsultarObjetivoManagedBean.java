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
    private ObjetivoMaterialFacadeLocal objetivoMaterialFacade;         //  Para obtener los materiales de un objetivo.
    @EJB
    private ObjetivoFacadeLocal objetivoFacade;                         //  Para obtener la lista de objetivos.
    @EJB
    private MaterialFacadeLocal materialFacade;                         //  Para obtener la lista de materiales.
    
    private List<Material> materialList;                                //  Lista de materiales.
    private List<Objetivo> listaObjetivos;                              //  Lista de objetivos.
    private Objetivo selectedObjetivo;                                  //  Objetivo seleccionado.
    private SimpleDateFormat sdf;                                       //  Formateador de fechas.
    private Material tempSelectedMaterial;                              //  Material seleccionado.
    private Integer cantSelectedMaterial;                               // Cantidad del material a añadir.
    
    /**
     * Creates a new instance of ConsultarObjetivoManagedBean
     */
    public ConsultarObjetivoManagedBean() {
        sdf = new SimpleDateFormat("dd/MM/YYYY");
        
    }
    
    /**
     * Obtener la lista de todos los objetivos y materiales del sistema.
     * 
     */
    @PostConstruct
    public void init(){
        rellenarListas();
        tempSelectedMaterial = new Material();
    }
    
    /**
     * Rellenar las listas de materiales y objetivos
     */
    private void rellenarListas(){
        // Lista de Materiales para selección.
        List<entities.Material> materialEntitiesList = materialFacade.findAll();
        ArrayList<Material> materialArrayList = new ArrayList();
        
        // Mapping de clases Entity a POJO.
        for (entities.Material matEntitie : materialEntitiesList){
            materialArrayList.add(util.MappingFromEntitieToPojo.materialFromEntityToPojo(matEntitie));
        }
        
        // Actualizar lista de materiales.
        this.materialList = materialArrayList;
        
        //  Llenando la lista de objetivos.
        List<entities.Objetivo> listaObjetivosEntities= objetivoFacade.findAll();
        ArrayList<Objetivo> objetivoList = new ArrayList();
        
        // Mapping de clases Entity a POJO.
        for (entities.Objetivo objetivoEntity : listaObjetivosEntities )
            objetivoList.add(util.MappingFromEntitieToPojo.objetivoFromEntityToPojo(objetivoEntity));
        
        // Acualizar lista de objetivos.
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
    
    /**
     * Listener del evento Fila seleccionada de la tabla de objetivo.
     * En caso de seleccionar un objetivo, obtener todos los materiales
     * de dicho objetivo, y mapearlos a clases POJO para su posterior
     * procesamiento y posible edición.
     * 
     * @param event SelectEvent Evento de fila seleccionada
     */
    public void handleRowSelect(SelectEvent event){
        // Obtener el objeto seleccionado
        Objetivo obj = (Objetivo) event.getObject();
        
        //  Buscar todos los materiales asociados al objetivo seleccionado.
        //  Utilizar EJB objetivoMaterialFacade
        List<entities.ObjetivoMaterial> omList = objetivoMaterialFacade.buscarPorObjetivo(obj.getCodObjetivo());
        
        // Nuevo Array de Clases POJO SelectedMaterial 
        ArrayList<SelectedMaterial> smArray = new ArrayList();
        
        // Para cada material relacionado con el objetivo seleccionado.
        for(entities.ObjetivoMaterial om : omList){
            // Hacer mapping con clase POJO y añadirlo al array.
            smArray.add(util.MappingFromEntitieToPojo.selectedMaterialFromMaterialObjetivoToPojo(om));
        }
        
        //  Setear al objetivo la nueva lista de clases SelectedMaterial, las cuales son los materiales
        //  del objetivo seleccionado.
        obj.setMaterialList(smArray);
        
        // Setear objetivo como nuevo objetivo seleccionado
        selectedObjetivo = obj;
    }
    
    // Mapping de la prioridad del objetivo seleccionado a String.
    public String verPrioridad(){
        // Si el objetivo seleccionado es null, retornar nada.
        if (selectedObjetivo == null)
            return null;
        
        // Mapping de la prioridad del objetivo seleccionado.
        return verPrioridadTabla(selectedObjetivo.getPrioridad());
    }
    
    //  Eliminar un material de la lista de materiales del objetivo.
    public void removeMaterialObjetivo(SelectedMaterial item){
        selectedObjetivo.removeMaterial(item);
    }
    
    // Añadir un nuevo material al objetivo seleccionado.
    public void addMaterialObjetivo(){
        // Nuevo material
        SelectedMaterial sm = new SelectedMaterial();
        
        // Setear el codigo del nuevo material en base al material escogido.
        sm.setCodMaterial(tempSelectedMaterial.getCodMaterial());
        
        // Si material no está en la lista, sobre-escribir la cantidad.
        if ( ! selectedObjetivo.isMaterialInObjetivo(sm) ){
            sm.setCantidad(cantSelectedMaterial);           // Sobre-escribir cantidad.
            selectedObjetivo.addMaterial(sm);               // Añadir material.
        }
    }
}
