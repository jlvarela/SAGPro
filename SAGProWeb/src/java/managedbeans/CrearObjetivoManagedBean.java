/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Material;
import entities.Objetivo;
import pojoclass.SelectedMaterial;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import sessionbeans.MaterialFacadeLocal;
import sessionbeans.ObjetivoFacadeLocal;
import sessionbeans.ObjetivoMaterialFacadeLocal;

/**
 *
 * @author Jose
 */
@ManagedBean(name = "crearObjetivoManagedBean")
@ViewScoped
public class CrearObjetivoManagedBean implements Serializable{
    @EJB
    private ObjetivoMaterialFacadeLocal objetivoMaterialFacade;
    @EJB
    private ObjetivoFacadeLocal objetivoFacade;
    @EJB
    private MaterialFacadeLocal materialFacade;
        
    private List<SelectedMaterial> selectedMateriales;
    private List<Material> listaMateriales;
    private String nombreObjetivo;
    private String initialDate;
    private String finishDate;
    private Integer codMaterialSelected;
    private Integer cantMaterialSelected;
    private SelectedMaterial smSeleccionado;
    private Short prioridadObjetivo;
    private String descripcionObjetivo;

    public SelectedMaterial getSmSeleccionado() {
        return smSeleccionado;
    }

    public void setSmSeleccionado(SelectedMaterial smSeleccionado) {
        this.smSeleccionado = smSeleccionado;
    }

    public String getDescripcionObjetivo() {
        return descripcionObjetivo;
    }

    public void setDescripcionObjetivo(String descripcionObjetivo) {
        this.descripcionObjetivo = descripcionObjetivo;
    }
    
    public Short getPrioridadObjetivo() {
        return prioridadObjetivo;
    }

    public void setPrioridadObjetivo(Short prioridadObjetivo) {
        this.prioridadObjetivo = prioridadObjetivo;
    }

    public Integer getCodMaterialSelected() {
        return codMaterialSelected;
    }

    public void setCodMaterialSelected(Integer codMaterialSelected) {
        this.codMaterialSelected = codMaterialSelected;
    }

    public Integer getCantMaterialSelected() {
        return cantMaterialSelected;
    }

    public void setCantMaterialSelected(Integer cantMaterialSelected) {
        this.cantMaterialSelected = cantMaterialSelected;
    }
    
    public String getNombreObjetivo() {
        return nombreObjetivo;
    }

    public void setNombreObjetivo(String nombreObjetivo) {
        this.nombreObjetivo = nombreObjetivo;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }
       
    public List<SelectedMaterial> getSelectedMateriales() {
        return selectedMateriales;
    }

    public void setSelectedMateriales(List<SelectedMaterial> selectedMateriales) {
        this.selectedMateriales = selectedMateriales;
    }

    public List<Material> getListaMateriales() {
        return listaMateriales;
    }

    public void setListaMateriales(List<Material> listaMateriales) {
        this.listaMateriales = listaMateriales;
    }
    
    /**
     * Creates a new instance of CrearObjetivoManagedBean
     */
    public CrearObjetivoManagedBean() {
    }
    
    @PostConstruct
    public void init (){
        listaMateriales = materialFacade.findAll();
        selectedMateriales = new ArrayList<>();
        smSeleccionado = new SelectedMaterial();
    }
    
    public void addMaterialObjetivo(){
        System.out.println("Fuck you");
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        if ( smSeleccionado.getCantidad() <= 0 ){
            msg = new FacesMessage();
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            msg.setDetail("La cantidad de material debe ser positiva");
            context.addMessage(null, msg);
            return;
        }
                
        // Si el elemento seleccionado se encuentra en la lista. Reemplazar su Cantidad.
        int index;
        index = selectedMateriales.indexOf(smSeleccionado);
        if( index != -1){
            SelectedMaterial obj = selectedMateriales.get(index);
            obj.setCantidad(smSeleccionado.getCantidad());
        }
        // Si no, añadir a la lista.
        else{
            for(Material p: listaMateriales)
                if(p.getCodMaterial() == smSeleccionado.getCodMaterial())
                    smSeleccionado.setNombreMaterial(p.getNombreMaterial());
            selectedMateriales.add(smSeleccionado);
        }
        
        smSeleccionado = new SelectedMaterial();
    }
    
    public void removeMaterialObjetivo(SelectedMaterial item){
        selectedMateriales.remove(item);
    }
    
    /**
     * Crea el objetivo ingresado a través del EJB ObjetivoFacade
     */
    public void agregarObjetivo(){
        // Contexto de PrimeFaces.
        FacesContext context = FacesContext.getCurrentInstance();

        // Nuevo mensaje a usuario.
        FacesMessage msj = new FacesMessage();
        try{
            
            if (!validarDatos()){
                msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Debe ingresar un nombre y ambas fechas.");
                context.addMessage(null, msj);
            }
            
            // Obtener tiempo, aplicando formato específico del calendario de jQuery (MM/dd/yyyy)
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            //Fecha inicial
            Date f_inicial = new Date(df.parse(initialDate).getTime());
            //Fecha final
            Date f_final = new Date(df.parse(finishDate).getTime());
            
            // Validar Fechas ingresadas
            //validarFechas(f_inicial, f_final);
            
            // Código de respuesta
            int resp;
            
            /**
            * Crear lista de código y cantidades de materiales.
            * Para proveer de argumentos a objetivoMaterialFacade.agregarMaterialToObjetivo.
            * De manera de agregar los materiales al objetivo creado.
            **/
           int [] codMaterialesArray = new int[selectedMateriales.size()];
           int [] cantMaterialesArray = new int[selectedMateriales.size()];

           /**
            * Para cada material seleccionado.
            *  Agregar valor de su código al arreglo de códigos.
            *  Agregar valor de la cantidad del material al arreglo de cantidades.
            * */
           for(int i=0; i<selectedMateriales.size(); i++){
               codMaterialesArray[i] = selectedMateriales.get(i).getCodMaterial().intValue();
               cantMaterialesArray[i] = selectedMateriales.get(i).getCantidad();
           }
           
            // Solicitar a EJB Objetivo, la función aagregarObjetivo.
            resp = objetivoFacade.agregarObjetivo(nombreObjetivo
                , descripcionObjetivo
                , f_inicial
                , f_final
                , prioridadObjetivo
                , codMaterialesArray
                , cantMaterialesArray);
            
            /**
             * Si código de respuesta es cero. La operación se ha realizado con
             * éxito
             * */
            if (resp == 0){
                                
                // Buscar Objetivo creado. Obtener su código.
                Objetivo obj = objetivoFacade.buscarPorNombre(nombreObjetivo);
                
                // Si no se ha encontrado objeto objetivo. Error desconocido.
                if (obj == null){
                    msj.setSeverity(FacesMessage.SEVERITY_ERROR);
                    msj.setDetail("Error desconocido. Por favor refresque la página y vuelva a intentarlo");
                    context.addMessage(null, msj);
                    return;
                }
                msj.setSeverity(FacesMessage.SEVERITY_INFO);
                msj.setDetail("Objetivo agregado con éxito");
                context.addMessage(null, msj);
                
            }
            // Código -1: 
            else if(resp == -1){
                msj.setSeverity(FacesMessage.SEVERITY_ERROR);
                msj.setDetail("Ya existe un objetivo con este nombre");
                context.addMessage(null, msj);
            }
            else if (resp == -2){
                msj.setSeverity(FacesMessage.SEVERITY_ERROR);
                msj.setDetail("La fecha inicial debe ser menor a la fecha final y mayor a la actual.");
                context.addMessage(null, msj);
            }
        }
        catch(ParseException e){
            System.out.println("Agregando Objetivo (bean): " + e.getMessage());
        }
    }
    
    /**
     * Determina la validez de las fechas ingresadas como argumento del
     * objetivo.
     * @param finicial  Date    Fecha inicial del objetivo.
     * @param ffinal    Date    Fecha final del objetivo.
     * @return 
     */
    private Boolean validarFechas(Date finicial, Date ffinal){
        // Si fecha inicial es posterior a fecha final
        if (!finicial.before(ffinal))
            return false;               // Fecha inválida
        
        Date now = new Date();          // Fecha del momento
        if(now.before(finicial))        // Si fecha del objetivo es anterior a hoy.
            return false;               // Fecha inválida
                
        return true;                    // Fecha válida
    }

    private boolean validarDatos() {
        if (nombreObjetivo == null || nombreObjetivo.isEmpty())
            return false;
        if (initialDate == null || initialDate.isEmpty())
            return false;
        if (finishDate == null || finishDate.isEmpty())
            return false;
        return true;
    }
    
}
