/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import pojoclass.Material;
import pojoclass.ProduccionDiaria;
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
    private String cantidad;                                                        // Cantidad de producción diaria ingresada
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
        List<entities.ProduccionDiaria> listaProduccionDiariaEntity = produccionDiariaFacade.buscarPorFecha(hoy.getTime());
        
        // Array de materiales. POJO Class
        ArrayList<Material> arrayListMaterial = new ArrayList();
        
        // Array de producciones. POJO Class
        ArrayList<ProduccionDiaria> arrayListProdDiaria = new ArrayList();
        
        // Para cada entidad, mappear a POJO Class
        for( entities.ProduccionDiaria p : listaProduccionDiariaEntity ){
            System.out.println("asf " + p.getProduccionDiariaPK().getCodMaterial());
            arrayListProdDiaria.add(util.MappingFromEntitieToPojo.produccionFromEntityToPojo(p));
        }
        
        listaProduccionDiaria = arrayListProdDiaria;
        
        // Para cada entidad material, mappear a POJO Class
        for( entities.Material p: listaMaterialesEntity ){
            arrayListMaterial.add(util.MappingFromEntitieToPojo.materialFromEntityToPojo(p));
        }
        
        listaMateriales = arrayListMaterial;
        
        this.materialSelected = listaMateriales.get(0);
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

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
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
        // Validar entradas
        // Fin Validar entradas
        FacesContext fcontext = FacesContext.getCurrentInstance();
        String viewId = fcontext.getViewRoot().getViewId();
        ViewHandler handler = fcontext.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(fcontext, viewId);
        root.setViewId(viewId);
        fcontext.setViewRoot(root);
        
        int resp = produccionDiariaFacade.agregarProduccionDiaria(Integer.parseInt(this.codMaterial)
                , Integer.parseInt(this.cantidad));

        //FacesContext fcontext = FacesContext.getCurrentInstance();
        if (resp == 0) {
            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Producción agregado con éxito"));
        } else if (resp == -1) {
            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ha ocurrido un error"));
        }
       
        init();
    }
}
