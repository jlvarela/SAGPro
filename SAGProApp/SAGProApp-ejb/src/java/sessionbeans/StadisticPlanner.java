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
    @EJB
    private MaterialFacadeLocal materialFacade;
    @EJB
    private ProduccionDiariaFacadeLocal produccionDiariaFacade;
    
    @PersistenceContext(unitName = "SAGProApp-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }
    
    //@Schedule ( dayOfMonth = "Last", month = "*", hour = "23", minute = "50", second = "0" )
    @Schedule(hour="*/1", persistent=false)
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
                System.out.println("StadisticPlanner : ".concat(mat.getNombreMaterial()).concat(" no hay nada"));
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
                    System.out.println("StadisticPlanner : ".concat(mat.getNombreMaterial()).concat(" agregado"));
                }
                catch(Exception e){
                    System.out.println("Problemas en StadisticPlanner");
                }
            }
            
            System.out.println("StadisticPlanner : ".concat(" Fin"));
        }
        
        
    }

    private EstadisticaMensual calcPromedioMes (List<ProduccionDiaria> result, Date mes){
        float promedio;
        int cantidad;
        int suma;
        
        // Calcular promedio mensual.
        suma = 0;                       // Acumulado se inicia en cero.
        cantidad = result.size();       // Cantidad de resultados
        
        for ( ProduccionDiaria t : result ){        // Para cada producción diaria.
            suma += t.getProduccionMaterial();      // Sumar la cantidad de la producción al acumulado.
        }
        
        promedio = suma / (float)cantidad;                 // Promedio mensual es acumulado / cantidad
        
        // Agregar EstadisticaMensual
        
        EstadisticaMensual estmen;
        
        estmen = new EstadisticaMensual();
        estmen.setCodMaterial(result.get(0).getMaterial());
        estmen.setMes(mes);
        estmen.setPromedioDiario(promedio);
        estmen.setDesvEstandar(0);
        estmen.setFechaRealizacion(new Date());
        estmen.setProduccionMensual(suma);
        estmen.setVarianza(0);
        
        return estmen;
    }
    
}
