/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Objetivo;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jose
 */
@Stateless
public class ObjetivoFacade extends AbstractFacade<Objetivo> implements ObjetivoFacadeLocal {
    @PersistenceContext(unitName = "SAGProApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ObjetivoFacade() {
        super(Objetivo.class);
    }

    @Override
    public void create(Objetivo entity) {
        super.create(entity); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Objetivo> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Agregar un objetivo al sistema.
     * Los argumentos de la función son las características del objetivo a crear.
     * Los códigos de resultado de la operación son los siguientes.
     * Código 0: Operación realizada satisfactoriamente.
     * Código -1: Objetivo ya existe.
     * Código -2: Fechas inválidas
     * @param nombre    String  Nombre del objetivo.
     * @param descripcion   String  descripción del objetivo.
     * @param fecha_inicial Date    Fecha inicial del objetivo
     * @param fecha_final   Date    Fecha final del objetivo.
     * @param prioridad     short   Prioridad del objetivo.
     * @return int código del resultado de la operación.
     */
    @Override
    public int agregarObjetivo(final String nombre
            , final String descripcion
            , Date fecha_inicial
            , Date fecha_final
            , final short prioridad) {
        
        if (objetivoExists(nombre)){
            return -1;
        }
        else if ( !validarFechas(fecha_inicial, fecha_final) ){
            return -2;
        }
        else{
            try{
                Objetivo obj = new Objetivo();
                obj.setNombreObjetivo(nombre);
                obj.setDescripcionObjetivo(descripcion);
                obj.setPrioridadObjetivo(prioridad);
                obj.setFechaInicial(fecha_inicial);
                obj.setFechaLimite(fecha_final);
                create(obj);
                System.out.println("Creación de Objetivo realizada con éxito");
                return 0;
            }catch(EntityExistsException e){
                System.out.println("Ingresando objetivo:" + e.getMessage());
                return -1;
            }
        }
    }
    
    /**
     * Determina si ya existe en el sistema un objetivo con el nombre
     * ingresado como parámetro de ésta función.
     * @param obj_nombre    String Nombre del objetivo a buscar.
     * @return  Boolean Existencia del objetivo.
     */
    public Boolean objetivoExists(String obj_nombre){
        int resultados; // Cantidad de objetivos encontrados con dicho nombre.
        
        /**
         * Solicitar a EntityManager, la búsqueda de objetivos con el nombre
         * ingresado como argumento.
         * */
        resultados = em.createNamedQuery("Objetivo.findByNombreObjetivo")
                .setParameter("nombreObjetivo", obj_nombre)
                .getResultList().size();
        
        return resultados != 0; // Existencia del objetivo
    }

    /**
     * 
     * @param nombre
     * @return 
     */
    @Override
    public Objetivo buscarPorNombre(final String nombre) {
        Objetivo result;
        
        result = (Objetivo) em.createNamedQuery("Objetivo.findByNombreObjetivo")
                .setParameter("nombreObjetivo", nombre)
                .getSingleResult();
        
        return result;
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
    
}
