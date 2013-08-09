/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
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
    
    private final DateFormat dayFormat = new SimpleDateFormat("dd");                // DateFormat para días
    private DateFormat monthFormat = new SimpleDateFormat("MM/yyyy");               // DateFormat para mes
    private List<Material> materialList;                                            // Listado de Materiales
    private List<Objetivo> objectiveList;                                           // Listado de Objetivos
    private Material selectedMaterial;                                              // Material seleccionado
    private CartesianChartModel model;                                              // Modelo del gráfico
    private Calendar selectedDate;                                                      // Fecha seleccionada
    private String formattedDate;                                                   // Fecha seleccionada con formato
    
    /**
     * Creates a new instance of EvolucionManagedBean
     */
    public EvolucionManagedBean() {
        
    }

    public Calendar getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Calendar selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    
    public DateFormat getMonthFormat() {
        return monthFormat;
    }

    public void setMonthFormat(DateFormat monthFormat) {
        this.monthFormat = monthFormat;
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
        return objectiveList;
    }

    public void setListaObjetivos(List<Objetivo> listaObjetivos) {
        this.objectiveList = listaObjetivos;
    }

    public List<Material> getListaMateriales() {
        return materialList;
    }

    public void setListaMateriales(List<Material> listaMateriales) {
        this.materialList = listaMateriales;
    }
    
    @PostConstruct
    public void init(){
        rellenarListas();
        selectedDate = Calendar.getInstance();
        createLinearChart(selectedDate);
        formattedDate = monthFormat.format(selectedDate.getTime());
    }
    
    /**
     * 
     */
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
        
        // Auto-selector de material
        if (! this.materialList.isEmpty() ){
            selectedMaterial = this.materialList.get(0);
        }
        
        // Lista de Objetivo para seleccionar
        List<entities.Objetivo> objetivoEntitiesList = objetivoFacade.findAll();
        ArrayList<Objetivo> objetivoList = new ArrayList();
        
        // Mapping de clases Entity a POJO
        for (entities.Objetivo objEntity : objetivoEntitiesList){
            objetivoList.add(util.MappingFromEntitieToPojo.objetivoFromEntityToPojo(objEntity));
        }
        
        // Actualizar lista de objetivos
        objectiveList = objetivoList;        
    }
    
    /**
     * 
     */
    private void createLinearChart(Calendar date){
        model = new CartesianChartModel();
        
        Calendar month = (Calendar)date.clone();
        
        // Último día
        int lastDay;
        
        // Determinar el último día del gráfico
        // En caso de que el mes sea el actual, el último día del gráfico es hoy
        int resp;
        resp = compareSelectedMonthTodaysMonth(month);

        if (  resp == 0 ){
            Calendar cal = Calendar.getInstance();
            lastDay = cal.get(Calendar.DAY_OF_MONTH);
        }
        else
            lastDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);

        
        // Final del calendario
        month.set(Calendar.DAY_OF_MONTH, lastDay);
        month.set(Calendar.HOUR_OF_DAY, 23);
        month.set(Calendar.MINUTE, 59);
        month.set(Calendar.SECOND, 0);
        
        Date f_final;
        f_final = month.getTime();
        
        // Primer día del mes.
        month.set(Calendar.DAY_OF_MONTH, 1);
        month.set(Calendar.HOUR_OF_DAY, 0);
        month.clear(Calendar.MINUTE);
        month.clear(Calendar.SECOND);
        month.clear(Calendar.MILLISECOND);
        
        // Creando Date a partir de Calendar
        Date f_inicial;
        f_inicial = month.getTime();
        
        List<ProduccionDiaria> prodDiariaList;
        prodDiariaList = getListaProduccionDiaria(selectedMaterial.getCodMaterial(), f_inicial, f_final);
               
        LineChartSeries line = new LineChartSeries();
        line.setLabel("Evolucion ".concat(selectedMaterial.getNombreMaterial()));

        // Creando puntos de la función
        int index;
        int acumulado;
        acumulado = 0;
        for ( int i = 0; i < lastDay ; i++ ){
            // Bucar si se produjo algo en día i.
            index = isDateInProduccionList(month.getTime(), prodDiariaList);
            // Si dicho día se produjo, incrementar acumulado.
            if ( index != -1 ){
                acumulado += prodDiariaList.get(index).getCantidad();   // Incrementar acumulado.
            }
            line.set(String.valueOf(month.get(Calendar.DAY_OF_MONTH)), acumulado);      // Agregar punto.
            month.add(Calendar.DAY_OF_MONTH, 1);                          // Incrementar día del Calendario.
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
        
    /**
     * Listener del evento onChange de la lista de materiales.
     * Una vez se selecciona un nuevo material, se actualiza el
     * atributo selectedMaterial, y se actualiza el gráfico en
     * función de esto.
     * @param event 
     */
    public void handleMaterialSelectChange(ValueChangeEvent event){
        String idMatSelect = (String) event.getNewValue();
        for(Material material: materialList){
            if(material.getCodMaterial().equals(Integer.parseInt(idMatSelect))){
                this.selectedMaterial = material;
                break;
            }
        }
        createLinearChart(selectedDate);
    }
    
    /**
     * 
     * @param event 
     */
    public void handleDateSelectChange(ValueChangeEvent event){
        String newDateString;
        Date newDate;
        
        newDateString = (String) event.getNewValue();
        try {
            newDate = monthFormat.parse(newDateString);
            selectedDate.setTime(newDate);
        }
        catch (ParseException pe){
            System.out.println(pe.getMessage());
        }
        
        createLinearChart(selectedDate);
    }
    
    public String getNameOfMonth(){
        return selectedDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
    }

    /**
     * 
     * @param month
     * @return 
     */
    private int compareSelectedMonthTodaysMonth(Calendar month) {
        Calendar today = Calendar.getInstance();
        if ( (month.get(Calendar.MONTH) == today.get(Calendar.MONTH))
                && (month.get(Calendar.YEAR) == today.get(Calendar.YEAR) ) ){
            return 0;
        }
        else if ( (month.get(Calendar.YEAR) > today.get(Calendar.YEAR) ) )
            return 1;
        else if ( (month.get(Calendar.MONTH) > today.get(Calendar.MONTH))
                && (month.get(Calendar.YEAR) == today.get(Calendar.YEAR) ) )
            return 1;
        return -1;
    }
}
