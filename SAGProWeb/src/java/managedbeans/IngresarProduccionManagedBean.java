/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import pojoclass.Material;
import entities.ProduccionDiaria;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.event.RowEditEvent;
import sessionbeans.MaterialFacadeLocal;
import sessionbeans.ProduccionDiariaFacadeLocal;

/**
 *
 * @author Gary
 */
@ManagedBean(name = "ingresarProduccionManagedBean")
@ViewScoped
public class IngresarProduccionManagedBean implements Serializable{

    @EJB
    private ProduccionDiariaFacadeLocal produccionDiariaFacade;                     //  Para obtener la lista de producciones diarias.
    @EJB
    private MaterialFacadeLocal materialFacade;                                     //  Para obtener la lista de materiales.
    private List<Material> listaMateriales;                                         // Listado de Materiales
    private Integer cantidad;                                                        // Cantidad de producción diaria ingresada
    private Material materialSelected;
    private String codMaterial;
    private List<ProduccionDiaria> listaProduccionDiaria;

    /**
     * Creates a new instance of ProduccionManagedBean
     */
    public IngresarProduccionManagedBean() {
    }

    @PostConstruct
    public void init()
    {
        // Fecha actual
        Calendar hoy = Calendar.getInstance();
        hoy.set(Calendar.HOUR_OF_DAY, 0);
        hoy.clear(Calendar.MINUTE);
        hoy.clear(Calendar.SECOND);
        hoy.clear(Calendar.MILLISECOND);
        
        // Lista de materiales del tipo entities.
        List<entities.Material> listaMaterialesEntity = materialFacade.findAll();
        // Lista de Producciones diarias, de la fecha actual. Son entity class.
        listaProduccionDiaria = produccionDiariaFacade.buscarPorFecha(hoy.getTime());
        
        // Array de materiales. POJO Class
        ArrayList<Material> arrayListMaterial = new ArrayList();
        
        // Para cada entidad material, mappear a POJO Class
        for( entities.Material p: listaMaterialesEntity ){
            arrayListMaterial.add(util.MappingFromEntitieToPojo.materialFromEntityToPojo(p));
        }
        
        listaMateriales = arrayListMaterial;
        
        this.materialSelected = listaMateriales.get(0);
        cantidad = 0;
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

    public List<ProduccionDiaria> getListaProduccionDiaria() {
        return listaProduccionDiaria;
    }

    public void setListaProduccionDiaria(List<ProduccionDiaria> listaProduccionDiaria) {
        this.listaProduccionDiaria = listaProduccionDiaria;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Material getMaterialSelected() {
        return materialSelected;
    }

    public void setMaterialSelected(Material materialSelected) {
        this.materialSelected = materialSelected;
    }

    public void materialSelectCantidadListener(ValueChangeEvent event) {
        String idMatSelect = (String) event.getNewValue();
        for (Material material : listaMateriales) {
            if (material.getCodMaterial().equals(Integer.parseInt(idMatSelect))) {
                this.materialSelected = material;
                codMaterial = idMatSelect;
                break;
            }
        }
    }

    /**
     * Ingresa una producción
     *
     */
    public void ingresarProduccion() {
        FacesContext fcontext = FacesContext.getCurrentInstance();
        FacesMessage msg;
        // Validar entradas
        if (cantidad <= 0){
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "La cantidad a ingresar debe ser un número entero y positivo");
            fcontext.addMessage(null, msg);
            return;
        }
        // Fin Validar entradas

        int resp = produccionDiariaFacade.agregarProduccionDiaria(Integer.parseInt(this.codMaterial)
                , this.cantidad);
        if (resp == 0) {
            String viewId = fcontext.getViewRoot().getViewId();
            ViewHandler handler = fcontext.getApplication().getViewHandler();
            UIViewRoot root = handler.createView(fcontext, viewId);
            root.setViewId(viewId);
            fcontext.setViewRoot(root);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Producción agregado con éxito");
            fcontext.addMessage(null, msg);
        } else if (resp == -1) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Los datos de entrada no son válidos.");
            fcontext.addMessage(null, msg);
        }
    }
    
    public String getDate(){
        DateFormat monthFormat = new SimpleDateFormat("dd/MM/yyyy");               // DateFormat para mes
        Date my_date = new Date();
        String fecha = monthFormat.format(my_date);
        return fecha;
    }
    public void onEdit(RowEditEvent event) {
        ProduccionDiaria prod = (ProduccionDiaria) event.getObject();
        int resp = produccionDiariaFacade.editarProduccion(prod.getProduccionDiariaPK().getCodMaterial()
                , prod.getProduccionDiariaPK().getFechaDiariaEstadistica()
                ,prod.getProduccionMaterial());
        FacesContext cont = FacesContext.getCurrentInstance();
        FacesMessage msg;
        if (resp == 0){
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"","Producción editada con éxito.");
            cont.addMessage(null, msg);
        }
        else if (resp == -1){
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"","Producción no se ha encontrado.");
            cont.addMessage(null, msg);
        }
    }
    
    public void onDelete(ProduccionDiaria prod){
        int resp = produccionDiariaFacade.eliminarProduccion(prod.getProduccionDiariaPK().getCodMaterial()
                , prod.getProduccionDiariaPK().getFechaDiariaEstadistica());
        FacesContext cont = FacesContext.getCurrentInstance();
        FacesMessage msg;
        if (resp == 0){
            String viewId = cont.getViewRoot().getViewId();
            ViewHandler handler = cont.getApplication().getViewHandler();
            UIViewRoot root = handler.createView(cont, viewId);
            root.setViewId(viewId);
            cont.setViewRoot(root);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"","Producción eliminada con éxito.");
            cont.addMessage(null, msg);
        }
        else if (resp == -1){
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"","Producción no se ha encontrado.");
            cont.addMessage(null, msg);
        }
                
    }
}
