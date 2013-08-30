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
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
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
    private Integer codSelectedMaterial;                                //  Código Material seleccionado.
    private Integer cantSelectedMaterial;                               //  Cantidad del material a añadir.
    private SelectedMaterial smSeleccionado;
    
    /**
     * Creates a new instance of ConsultarObjetivoManagedBean
     */
    public ConsultarObjetivoManagedBean() {
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        
    }
    
    /**
     * Obtener la lista de todos los objetivos y materiales del sistema.
     * 
     */
    @PostConstruct
    public void init(){
        rellenarListas();
        smSeleccionado = new SelectedMaterial();
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

    public SelectedMaterial getSmSeleccionado() {
        return smSeleccionado;
    }

    public void setSmSeleccionado(SelectedMaterial smSeleccionado) {
        this.smSeleccionado = smSeleccionado;
    }

    public Integer getCantSelectedMaterial() {
        return cantSelectedMaterial;
    }

    public void setCantSelectedMaterial(Integer cantSelectedMaterial) {
        this.cantSelectedMaterial = cantSelectedMaterial;
    }

    public Integer getCodSelectedMaterial() {
        return codSelectedMaterial;
    }

    public void setCodSelectedMaterial(Integer codSelectedMaterial) {
        this.codSelectedMaterial = codSelectedMaterial;
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
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        if ( smSeleccionado.getCantidad() <= 0 ){
            msg = new FacesMessage();
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            msg.setDetail("La cantidad de material debe ser positiva");
            context.addMessage(null, msg);
            return;
        }
        int index;
        index = selectedObjetivo.getMaterialList().indexOf(smSeleccionado);
        if( index != -1){
            SelectedMaterial obj = selectedObjetivo.getMaterialList().get(index);
            obj.setCantidad(smSeleccionado.getCantidad());
        }
        // Si no, añadir a la lista.
        else{
            for(Material p: materialList)
                if(p.getCodMaterial() == smSeleccionado.getCodMaterial())
                    smSeleccionado.setNombreMaterial(p.getNombreMaterial());
            selectedObjetivo.addMaterial(smSeleccionado);
        }
        
        smSeleccionado = new SelectedMaterial();
    }
    
    /**
     * Busca un material específico en la lista de materiales
     * en base a su código.
     * En caso de no encontrase, retorna null
     * 
     * @param codMaterial   int   Código del material a bsucar
     * @return Material Material buscado
     */
    private Material buscarMaterialEnLista(int codMaterial){
        for ( Material mat : materialList ){
            if ( mat.getCodMaterial().equals(codMaterial) )
                return mat;
        }
        return null;
    }
    
    /**
     * Elimina el objetivo seleccionado.
     */
    public void modificarObjetivo(){
        FacesContext cont = FacesContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage();
        if ( selectedObjetivo == null ){
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            msg.setDetail("No ha seleccionado ningún objetivo");
            cont.addMessage(null, msg);
        }
        else{
            int resp;
            
            /**
            * Crear lista de código y cantidades de materiales.
            * Para proveer de argumentos a objetivoMaterialFacade.agregarMaterialToObjetivo.
            * De manera de agregar los materiales al objetivo creado.
            **/
           int [] codMaterialesArray = new int[selectedObjetivo.getMaterialList().size()];
           int [] cantMaterialesArray = new int[selectedObjetivo.getMaterialList().size()];

           /**
            * Para cada material seleccionado.
            *  Agregar valor de su código al arreglo de códigos.
            *  Agregar valor de la cantidad del material al arreglo de cantidades.
            * */
           for(int i=0; i<selectedObjetivo.getMaterialList().size(); i++){
               codMaterialesArray[i] = selectedObjetivo.getMaterialList().get(i).getCodMaterial().intValue();
               cantMaterialesArray[i] = selectedObjetivo.getMaterialList().get(i).getCantidad();
           }
            System.out.println("Prioridad " + selectedObjetivo.getPrioridad());
            resp = objetivoFacade.editarObjetivo(
                    selectedObjetivo.getCodObjetivo()
                    , selectedObjetivo.getNombre()
                    , selectedObjetivo.getDescripcion()
                    , selectedObjetivo.getFechaInicial()
                    , selectedObjetivo.getFechaLimite()
                    , selectedObjetivo.getPrioridad()
                    , codMaterialesArray
                    , cantMaterialesArray);
            
            if (resp == 0){
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                msg.setDetail("El objetivo se ha editado con éxito");
                cont.addMessage(null, msg);
                
                String viewId = cont.getViewRoot().getViewId();
                ViewHandler handler = cont.getApplication().getViewHandler();
                UIViewRoot root = handler.createView(cont, viewId);
                root.setViewId(viewId);
                cont.setViewRoot(root);
            }
            else if (resp == -1){
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                msg.setDetail("Ya existe un objetivo con el nombre ingresado.");
                cont.addMessage(null, msg);
            }
            else if (resp == -2){
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                msg.setDetail("La fecha inicial debe ser menor a la fecha final y mayor a la actual.");
                cont.addMessage(null, msg);
            }
        }
    }
    
    public void borrarObjetivo(){
        FacesContext cont = FacesContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage();
        
        if ( selectedObjetivo == null ){
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            msg.setDetail("No ha seleccionado ningún objetivo");
            cont.addMessage(null, msg);
        }
        else{
            int resp = objetivoFacade.eliminarObjetivo(selectedObjetivo.getCodObjetivo());
            if (resp == 0){
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                msg.setDetail("El objetivo se ha eliminado con éxito");
                cont.addMessage(null, msg);
            }
        }
        String viewId = cont.getViewRoot().getViewId();
        ViewHandler handler = cont.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(cont, viewId);
        root.setViewId(viewId);
        cont.setViewRoot(root);
    }
}
