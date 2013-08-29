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
import pojoclass.SelectedMaterial;
import sessionbeans.MaterialFacadeLocal;
import sessionbeans.ObjetivoFacadeLocal;
import sessionbeans.ObjetivoMaterialFacadeLocal;
import sessionbeans.ProduccionDiariaFacadeLocal;

/**
 *
 * @author Jose
 */
@ManagedBean(name = "evolucionManagedBean")
@ViewScoped
public class EvolucionManagedBean implements Serializable{
    @EJB
    private ObjetivoMaterialFacadeLocal objetivoMaterialFacade;
    @EJB
    private ProduccionDiariaFacadeLocal produccionDiariaFacade;                     //  Para obtener la lista de producciones diarias.
    @EJB
    private ObjetivoFacadeLocal objetivoFacade;                                     //  Para obtener la lista de objetivos.
    @EJB
    private MaterialFacadeLocal materialFacade;                                     //  Para obtener la lista de materiales.
    
    private final DateFormat dayFormat = new SimpleDateFormat("dd");                // DateFormat para días
    private DateFormat monthFormat = new SimpleDateFormat("MM/yyyy");               // DateFormat para mes
    private List<Material> materialList;                                            // Listado de Materiales
    private List<Objetivo> objectiveList;                                           // Listado de Objetivos
    private List<String> objetivosSeleccionados;                                  // Lista de Objetivos seleccionados
    private Material selectedMaterial;                                              // Material seleccionado
    private CartesianChartModel model;                                              // Modelo del gráfico
    private Calendar selectedDate;                                                  // Fecha seleccionada
    private String formattedDate;                                                   // Fecha seleccionada con formato
    
    /**
     * Creates a new instance of EvolucionManagedBean
     */
    public EvolucionManagedBean() {
        
    }

    public List<String> getObjetivosSeleccionados() {
        return objetivosSeleccionados;
    }

    public void setObjetivosSeleccionados(List<String> objetivosSeleccionados) {
        this.objetivosSeleccionados = objetivosSeleccionados;
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
        rellenarListas();                                           //  Rellenar listas de objetivo y material.
        selectedDate = Calendar.getInstance();                      //  Fecha actual.
        createLinearChart(selectedMaterial, selectedDate);                            //  Crear gráfico con fecha actual.
        formattedDate = monthFormat.format(selectedDate.getTime()); //  Aplicar formato para mostrar mes como string.
    }
    
    /**
     *  Rellena las listas de objetivo y materiales.
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
            Objetivo obj = util.MappingFromEntitieToPojo.objetivoFromEntityToPojo(objEntity);
            
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
            
            objetivoList.add(obj);
            
        }
        
        // Actualizar lista de objetivos
        objectiveList = objetivoList;
    }
    
    /**
     *  Crea gráfico en base 
     */
    private void createLinearChart(Material material, Calendar date){
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
        prodDiariaList = getListaProduccionDiaria(material.getCodMaterial(), f_inicial, f_final);
               
        LineChartSeries line = new LineChartSeries();
        line.setLabel("Evolucion ".concat(material.getNombreMaterial()));

        // Creando puntos de la función
        int index;
        int acumulado;
        acumulado = 0;
        for ( int i = 0; i < lastDay ; i++ ){
            // Buscar si se produjo algo en día i.
            index = isDateInProduccionList(month.getTime(), prodDiariaList);
            // Si dicho día se produjo, incrementar acumulado.
            if ( index != -1 ){
                acumulado += prodDiariaList.get(index).getCantidad();                   // Incrementar acumulado.
            }
            line.set(String.valueOf(month.get(Calendar.DAY_OF_MONTH)), acumulado);      // Agregar punto.
            month.add(Calendar.DAY_OF_MONTH, 1);                                        // Incrementar día del Calendario.
        }
        
        model.addSeries(line);
        
        agregarObjetivosAChart(selectedMaterial);
    }
    
    /**
     * Obtener la lista de producciones diarias en base a un determinado material y
     * a un intervalo de fechas.
     * Para esto se invocan los métodos del EJB produccionDiariaFacade.
     * 
     * @param codMaterial   int Código del material a buscar.
     * @param f_inicial Date    Fecha inicial del intervalo.
     * @param f_final   Date    Fecha final del intervalo.
     * @return  List<ProduccionDiaria>  Lista de producciones diarias.
     */
    private List<ProduccionDiaria> getListaProduccionDiaria(int codMaterial, Date f_inicial, Date f_final){
        List<entities.ProduccionDiaria> prodDiariaEntityList;   // Lista final de producciones diarias
        
        // Invocar método de EJB produccionDiariaFacade, buscarPorRango, para encontrar
        // todas las producciones dentro del intervalo.
        prodDiariaEntityList = produccionDiariaFacade.buscarPorRango(codMaterial, f_inicial, f_final);
        
        /// Lista de producciones diarias de clase POJO.
        ArrayList<ProduccionDiaria> prodDiariaList = new ArrayList();
        
        // Para cada producción encontrada.
        for(entities.ProduccionDiaria prodEntity : prodDiariaEntityList){
            // Mapping de clase Entity a POJO
            prodDiariaList.add(util.MappingFromEntitieToPojo.produccionFromEntityToPojo(prodEntity));
        }
        
        return prodDiariaList;      // Retornar lista de Producciones diarias.
    }
    
    /**
     * Buscar si en una fecha, ingresada como parámetro, se registró alguna producción.
     * Se busca la producción en la lista, en caso de encontrar se retorna su índice en la Lista.
     * En caso de no encontrar se retorna -1.
     * 
     * @param fecha Date    Fecha en la que se busca la producción.
     * @param lista List<produccionDiaria>  Lista de producciones en la que se debe buscar.
     * @return int Index del objeto en la lista. -1 si no e encuentra.
     */
    private int isDateInProduccionList(Date fecha, List<ProduccionDiaria> lista ){
        // Para cada producción en la lista
        for ( int i=0; i<lista.size(); i++ ){
            // Si la fecha de la producción equivale a la fecha ingresada como parámetro
            if ( lista.get(i).getFecha().compareTo(fecha) == 0 )
                // Retornar el index en la lista de dicha producción.
                return i;
        }
        
        // Si no se encuentra, retornar -1.
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
        createLinearChart(selectedMaterial, selectedDate);
    }
    
    /**
     * Listener del evento selección de fecha.
     * Una vez se ha seleccionado una fecha para generar el gráfico,
     * se obtiene la fecha ingresada y se crea el nuevo gráfico en base
     * al material ya seleccionado y a la fecha nueva ingresada.
     * 
     * @param event 
     */
    public void handleDateSelectChange(ValueChangeEvent event){
        String newDateString;                       // Nuevo string de la fecha.
        Date newDate;                               // Nueva fecha en clase Date.
        
        newDateString = (String) event.getNewValue();   // Obtener el valor de la nueva fecha en string.
        
        try {
            newDate = monthFormat.parse(newDateString); //  Parsear string a Date de la fecha ingresada.
            selectedDate.setTime(newDate);              //  Setear la nueva fecha como la fecha seleccionada.
        }
        catch (ParseException pe){
            System.out.println(pe.getMessage());        // En caso de que el parseo sea erróneo, imprimir error por pantalla.
        }
        
        createLinearChart(selectedMaterial, selectedDate);  // Crear nuevo gráfico.
    }
    
    // Obtener nombre en español del mes seleccionado.
    public String getNameOfMonth(){
        return selectedDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.forLanguageTag("ES"));
    }

    /**
     * Comparar el mes ingresado con el mes de la fecha actual.
     * Para esto se considera también el año.
     * 
     * Si el mes es posterior al actual, retornar 1.
     * Si el mes es equivalente al actual, retornar 0.
     * Si el mes es anterior al actual, retornar -1.
     * 
     * @param month Calendar    Mes ingresado a comparar.
     * @return int  Si
     */
    private int compareSelectedMonthTodaysMonth(Calendar month) {
        // Nueva fecha actual.
        Calendar today = Calendar.getInstance();
        
        // Si el mes y año son iguales. Retornar 0.
        if ( (month.get(Calendar.MONTH) == today.get(Calendar.MONTH))
                && (month.get(Calendar.YEAR) == today.get(Calendar.YEAR) ) ){
            return 0;
        }
        // Si año es mayor que el año actual. Retornar 1.
        else if ( (month.get(Calendar.YEAR) > today.get(Calendar.YEAR) ) )
            return 1;
        // Si mes es mayor que el actual y año es el mismo. Retornar 1.
        else if ( (month.get(Calendar.MONTH) > today.get(Calendar.MONTH))
                && (month.get(Calendar.YEAR) == today.get(Calendar.YEAR) ) )
            return 1;
        
        // Caso contrario, mes ingresado es anterior. retornar -1.
        return -1;
    }
    
    public void selectObjetivo(){
        createLinearChart(selectedMaterial, selectedDate);
    }
    
    private void agregarObjetivosAChart(Material material){
        if (objetivosSeleccionados == null)
            return;
        
        SelectedMaterial sm = new SelectedMaterial();
        sm.setCodMaterial(material.getCodMaterial());
        
        Calendar month = (Calendar) selectedDate.clone();
        // Final del calendario
        int lastDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Agregar objetivos al gráfico.
        for( int j=0; j<objetivosSeleccionados.size(); j++){
            Objetivo obj = getObjFromSeleccionados(j);
            if (obj == null)
                continue;
            List<SelectedMaterial> selectMatList = obj.getMaterialList();
            int index = selectMatList.indexOf(sm);
            if ( index >= 0  ){
                LineChartSeries chart = new LineChartSeries();
                SelectedMaterial smObj = selectMatList.get(index);
                month.set(Calendar.DAY_OF_MONTH, 0);
                for ( int i=0; i < lastDay; i++ ){
                    chart.set(String.valueOf(month.get(Calendar.DAY_OF_MONTH)), smObj.getCantidad() );
                    month.add(Calendar.DAY_OF_MONTH, 1);
                }
                chart.setLabel(obj.getNombre());
                model.addSeries(chart);
            }
        }
    }
    
    /**
     * 
     * @param month
     * @param obj
     * @return 
     */
    private Boolean estaObjetivoEnMes(Calendar month, Objetivo obj){
        if (obj.getFechaInicial().before(month.getTime()) || obj.getFechaInicial().equals(month.getTime()))
            if ( month.getTime().before(obj.getFechaLimite()) || month.getTime().before(obj.getFechaLimite()) )
                return true;
        return false;   
    }
    
    /**
     * 
     * @param index
     * @return 
     */
    private Objetivo getObjFromSeleccionados(int index){
        String cod_objetivo = objetivosSeleccionados.get(index);
        Integer codigo = Integer.parseInt(cod_objetivo);
        for ( Objetivo obj: objectiveList ){
            if ( obj.getCodObjetivo().compareTo(codigo) == 0 )
                return obj;
        }
        return null;
    }
}