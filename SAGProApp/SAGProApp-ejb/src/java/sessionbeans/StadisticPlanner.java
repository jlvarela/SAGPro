/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.EstadisticaMensual;
import entities.Material;
import entities.ProduccionDiaria;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jose
 */
@Singleton
public class StadisticPlanner implements StadisticPlannerLocal {
    // Necesarios para obtener todos los materiales registrados.
    @EJB
    private MaterialFacadeLocal materialFacade;
    
    // Necesario para obtener todas las producciones diarias que cumplan cierta condición.
    @EJB
    private ProduccionDiariaFacadeLocal produccionDiariaFacade;
    
    // Necesario para obtener las entidades respectivas y persistir los datos nuevos.
    @PersistenceContext(unitName = "SAGProApp-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * Calcula las estadísticas mensuales del sistema. El Timer se ejecuta todos los días que cumplan
     * la condición de ser fin de mes, a la hora 23:50. Para realizar esto, se buscan todas las producciones
     * diarias realizadas en el mes, calculándose el promedio, y la cantidad total. Finalmente estos datos
     * son registrados.
     * Las estadísticas son calculadas para todos los materiales ingresados en el sistema a la hora de ejecutar
     * el timer.
     */
    @Schedule ( dayOfMonth = "Last", month = "*", hour = "23", minute = "50", second = "0" )
    @Override
    public void doMonthlyStadistic() {
        
        // Primer día del mes
        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        
        // Creando Date a partir de Calendar
        Date f_inicial;
        f_inicial = cal.getTime();
        
        // Último día del mes. Se restringe debido a que Scheduler se instancia en dicho día
        Date f_final;
        f_final = new Date();
        
        // Lista de todas las producciones encontradas en el mes
        List<ProduccionDiaria> listaProduccion;
        
        // Lita de todos los materiales a recorrer.
        List<Material> listaMaterial;
        
        // Pedir a EJB lista de materiales.
        listaMaterial = materialFacade.findAll();
        
        // Para cada material
        for (Material mat : listaMaterial){
            System.out.println("StadisticPlanner : ".concat(mat.getNombreMaterial()).concat(" Empieza"));
            // Obtener las produccione del mes para dicho material
            listaProduccion = produccionDiariaFacade.bucarPorRango(mat.getCodMaterial(), f_inicial, f_final);

            // Si no hay producción. Terminar
            if ( listaProduccion.isEmpty() ) {
                System.out.println("StadisticPlanner : ".concat(mat.getNombreMaterial()).concat(". No hay nada"));
            }
            // Si no, crear nueva estadística mensual.
            else{
                System.out.println("StadisticPlanner : ".concat(mat.getNombreMaterial()).concat(" agregando"));
                // Nueva estadística mensual.
                EstadisticaMensual new_stdm;
                
                // Calcular promedio y crear nueva estadística.
                new_stdm = calcPromedioMes(listaProduccion, f_inicial);
                
                // Persistir
                try{
                    em.persist(new_stdm);
                    System.out.println("StadisticPlanner : ".concat(mat.getNombreMaterial()).concat(" realizado"));
                }
                catch(Exception e){
                    System.out.println("Problemas en StadisticPlanner");
                }
            }
            System.out.println("StadisticPlanner : ".concat(" Fin"));
        }
    }
    
    /**
     * Calcula el promedio mensual para una lista determinada de producciones diarias.
     * El mes sobre el cual se calcula se ingresa como parámetro, y en base a éste se
     * filtran aquuelas producciones que no cumplen la condición de estar dentro del mes.
     * 
     * @param result    List<ProduccionDiaria>  Lista de producciones diarias.
     * @param mes   Date    Fecha sobre la que se realiza el promedio.
     * @return  EstadisticaMensual  EntityClass para persistir la estadística creada.
     */
    private EstadisticaMensual calcPromedioMes (List<ProduccionDiaria> result, Date mes){
        float promedio;     // Promedio final de la producción mensual.
        int cantidad;       // Cantidad total de producciones en el mes.
        int suma;           // Acumulador
        
        // Calcular promedio mensual.
        suma = 0;                       // Acumulado se inicia en cero.
        cantidad = result.size();       // Cantidad de resultados
        
        for ( ProduccionDiaria t : result ){        // Para cada producción diaria.
            suma += t.getProduccionMaterial();      // Sumar la cantidad de la producción al acumulado.
        }
        
        promedio = suma / (float)cantidad;                 // Promedio mensual es acumulado / cantidad
        
        // Agregar EstadisticaMensual
        EstadisticaMensual estmen;
        
        // Nueva EstadisticaMensual
        estmen = new EstadisticaMensual();
        estmen.setCodMaterial(result.get(0).getMaterial());     // Setear Código del material
        estmen.setMes(mes);                                     // Setear Mes
        estmen.setPromedioDiario(promedio);                     // Setear Promedio
        estmen.setDesvEstandar(calcularVarianza(result, promedio));                              // Setear Desviación Estandar.
        estmen.setFechaRealizacion(new Date());                 // Setear fecha de realización
        estmen.setProduccionMensual(suma);                      // Setear producción total
        estmen.setVarianza(0);                                  // Setear Varianza
        
        return estmen;                                          // Retornar Estadística
    }
    
    /**
     * Calcula la varianza para una determinada lista de elementos y el promedio
     * de dichos elementos entregados por parámetros.
     * La varianza es calculada como la suma de las diferencias de producción diaria y el promedio
     * al cuadrado, dividido por la cantidad de elementos.
     * 
     * Si la cantidad de elementos es nula, la varianza es nula.
     * 
     * @param result    List<ProduccionDiaria>    Lista de producciones diarias.
     * @param promedio  float   Promedio de las cantidades de produccion
     * @return  float   Varianza
     */
    private float calcularVarianza(List<ProduccionDiaria> result, float promedio){
        float varianza, diferencia;                         // Varianza final y diferencia
        int cantidad;                                       // Almacena cantidad de elementos
        
        cantidad = result.size();                           // Cantidad de elementos
        varianza = 0;                                       // Varianza por defecto es nula
        
        //  Para cada producción diaria en lista
        for(ProduccionDiaria prod: result){
            // Calcular diferencia entre la producción total diaria y el promedio.
            diferencia = prod.getProduccionMaterial() - promedio;
            
            // Elevar diferencia al cuadrado y sumar a varianza.
            varianza += Math.pow(diferencia, 2);
        }
        
        // Finalmente se normaliza la varianza dividiendo por la cantidad de elementos.
        varianza /= cantidad;
        
        // Retornar varianza.
        return varianza;
    }
    
}
