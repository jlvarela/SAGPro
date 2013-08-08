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
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;
import pojoclass.Material;
import pojoclass.Objetivo;
import pojoclass.ProduccionDiaria;
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
        // Por ahora sólo calcula por mes actual
        Calendar cal = Calendar.getInstance();
        int daysInMonth;
        daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        // Último día del mes.
        cal.set(Calendar.DAY_OF_MONTH, daysInMonth);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 0);
        
        Date f_final;
        f_final = cal.getTime();
        
        // Primer día del mes.
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        
        // Creando Date a partir de Calendar
        Date f_inicial;
        f_inicial = cal.getTime();
        
        
        
        List<ProduccionDiaria> prodDiariaList;
        prodDiariaList = getListaProduccionDiaria(selectedMaterial.getCodMaterial(), f_inicial, f_final);
        
        model = new CartesianChartModel();
        
        LineChartSeries line = new LineChartSeries();
        line.setLabel("Evolucion ".concat(selectedMaterial.getNombreMaterial()));

        // Creando puntos de la función
        int index;
        for ( int i = 0; i < daysInMonth ; i++ ){
            index = isDateInProduccionList(cal.getTime(), prodDiariaList);
            System.out.println(index);
            if ( index != -1 ){
                line.set(dateFormat.format(cal.getTime()), prodDiariaList.get(index).getCantidad());
            }
            else
                line.set(dateFormat.format(cal.getTime()), 0);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        model.addSeries(line);
    }
    
    private List<ProduccionDiaria> getListaProduccionDiaria(int codMaterial, Date f_inicial, Date f_final){
        List<entities.ProduccionDiaria> prodDiariaEntityList;
        prodDiariaEntityList = produccionDiariaFacade.bucarPorRango(codMaterial, f_inicial, f_final);
        
        ArrayList<ProduccionDiaria> prodDiariaList = new ArrayList();
        for(entities.ProduccionDiaria prodEntity : prodDiariaEntityList){
            prodDiariaList.add(util.MappingFromEntitieToPojo.produccionFromEntityToPojo(prodEntity));
        }
        
        return prodDiariaList;
    }
    
    /**
     * 
     * @param fecha
     * @param lista
     * @return int Index del objeto en la lista. -1 si no e encuentra.
     */
    private int isDateInProduccionList(Date fecha, List<ProduccionDiaria> lista ){
        for ( int i=0; i<lista.size(); i++ ){
            if ( lista.get(i).getFecha().compareTo(fecha) == 0 )
                return i;
        }
        return -1;
    }
}
