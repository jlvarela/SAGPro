/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Material;
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

/**
 *
 * @author Jose
 */
@ManagedBean(name = "crearObjetivoManagedBean")
@ViewScoped
public class CrearObjetivoManagedBean implements Serializable{
    @EJB
    private ObjetivoFacadeLocal objetivoFacade;
    @EJB
    private MaterialFacadeLocal materialFacade;
    
    public class SelectedMaterial{
        private Integer idMaterial;
        public int cantidad;
        public String nombreMaterial;
        
        public SelectedMaterial () {
            
        }

        public String getNombreMaterial() {
            return nombreMaterial;
        }

        public void setNombreMaterial(String nombreMaterial) {
            this.nombreMaterial = nombreMaterial;
        }

        public Integer getIdMaterial() {
            return idMaterial;
        }

        public void setIdMaterial(Integer idMaterial) {
            this.idMaterial = idMaterial;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final SelectedMaterial other = (SelectedMaterial) obj;
            if (this.idMaterial != other.idMaterial) {
                return false;
            }
            return true;
        }
        
    }
    
    private List<SelectedMaterial> selectedMateriales;
    private List<Material> listaMateriales;
    private String nombreObjetivo;
    private String initialDate;
    private String finishDate;
    private Integer codMaterialSelected;
    private Integer cantMaterialSelected;
    private Short prioridadObjetivo;
    private String descripcionObjetivo;

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
        cantMaterialSelected = 0;
    }
    
    public void addMaterialObjetivo(){
        SelectedMaterial newmaterial = new SelectedMaterial();
        newmaterial.setIdMaterial(codMaterialSelected);
        
        // Si el elemento seleccionado se encuentra en la lista. Reemplazar su Cantidad.
        int index;
        index = selectedMateriales.indexOf(newmaterial);
        if( index != -1){
            SelectedMaterial obj = selectedMateriales.get(index);
            obj.cantidad = cantMaterialSelected;
        }
        // Si no, añadir a la lista.
        else{
            for(Material p: listaMateriales)
                if(p.getCodMaterial() == codMaterialSelected)
                    newmaterial.setNombreMaterial(p.getNombreMaterial());
            newmaterial.setCantidad(cantMaterialSelected);
            selectedMateriales.add(newmaterial);
        }
    }
    
    public void removeMaterialObjetivo(SelectedMaterial item){
        selectedMateriales.remove(item);
    }
    
    public void agregarObjetivo(){
        try{
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            Date f_inicial = new Date(df.parse(initialDate).getTime());
            Date f_final = new Date(df.parse(finishDate).getTime());

            
            System.out.println(nombreObjetivo + descripcionObjetivo + f_inicial + f_final + prioridadObjetivo);
            int resp = objetivoFacade.agregarObjetivo(nombreObjetivo
                    , descripcionObjetivo
                    , f_inicial
                    , f_final
                    , prioridadObjetivo);
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msj = new FacesMessage();
            if (resp == 0){
                msj.setSeverity(FacesMessage.SEVERITY_INFO);
                msj.setDetail("Objetivo agregado con éxito");
                context.addMessage(null, msj);
            }
            else if(resp == -1){
                msj.setSeverity(FacesMessage.SEVERITY_ERROR);
                msj.setDetail("Objetivo imposible de agregar");
                context.addMessage(null, msj);
            }
        }
        catch(ParseException e){
            System.out.println("Agregando Objetivo (bean): " + e.getMessage());
        }

    }
    
}
