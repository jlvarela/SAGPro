/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;
import pojoclass.Material;
import pojoclass.Objetivo;
import sessionbeans.MaterialFacadeLocal;
import sessionbeans.ObjetivoFacadeLocal;
import sessionbeans.ProduccionDiariaFacadeLocal;

/**
 *
 * @author Jose
 */
@ManagedBean(name = "evolucionManagedBean")
@ViewScoped
public class EvolucionManagedBean implements Serializable{
    @EJB
    private ProduccionDiariaFacadeLocal produccionDiariaFacade;
    @EJB
    private ObjetivoFacadeLocal objetivoFacade;
    @EJB
    private MaterialFacadeLocal materialFacade;

    /**
     * Creates a new instance of EvolucionManagedBean
     */
    
    private final static DateFormat dateFormat = new SimpleDateFormat("dd");
    private List<Material> listaMateriales;
    private List<Objetivo> listaObjetivos;
    private Material selectedMaterial;
    private CartesianChartModel model;
    
    public EvolucionManagedBean() {
        
    }

    public CartesianChartModel getModel() {
        return model;
    }

    public void setModel(CartesianChartModel model) {
        this.model = model;
    }
    
    
    public Material getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(Material selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }

    public List<Objetivo> getListaObjetivos() {
        return listaObjetivos;
    }

    public void setListaObjetivos(List<Objetivo> listaObjetivos) {
        this.listaObjetivos = listaObjetivos;
    }

    public List<Material> getListaMateriales() {
        return listaMateriales;
    }

    public void setListaMateriales(List<Material> listaMateriales) {
        this.listaMateriales = listaMateriales;
    }
    
    @PostConstruct
    public void init(){
        rellenarListas();
        createLinearChart();
        
    }
    
    private void rellenarListas(){
        List<entities.Material> materialEntitiesList = materialFacade.findAll();
        ArrayList<Material> materialList = new ArrayList();
        for (entities.Material matEntitie : materialEntitiesList){
            materialList.add(util.MappingFromEntitieToPojo.materialFromEntityToPojo(matEntitie));
        }
        listaMateriales = materialList;
        if (! listaMateriales.isEmpty() )
            selectedMaterial = listaMateriales.get(0);
        
        List<entities.Objetivo> objetivoEntitiesList = objetivoFacade.findAll();
        ArrayList<Objetivo> objetivoList = new ArrayList();
        for (entities.Objetivo objEntity : objetivoEntitiesList){
            objetivoList.add(util.MappingFromEntitieToPojo.objetivoFromEntityToPojo(objEntity));
        }
        listaObjetivos = objetivoList;        
    }
    
    private void createLinearChart(){
        // Por ahora sólo calcula por mes
        Calendar cal = Calendar.getInstance();
        int daysInMonth;
        daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(daysInMonth);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        
        model = new CartesianChartModel();
        
        LineChartSeries line = new LineChartSeries();
        line.setLabel("Fx");
        for ( int i = 0; i < daysInMonth ; i++ ){
            System.out.println(dateFormat.format(cal.getTime())+ "__" + i);
            line.set(dateFormat.format(cal.getTime()), i);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        model.addSeries(line);
    }
}
