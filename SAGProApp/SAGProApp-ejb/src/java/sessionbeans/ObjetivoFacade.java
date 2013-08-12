package sessionbeans;

import entities.Objetivo;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
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
    @EJB
    private ObjetivoMaterialFacadeLocal objetivoMaterialFacade; // Necesario para agregar y consultar materiales de un objetivo.
    
    @PersistenceContext(unitName = "SAGProApp-ejbPU")
    private EntityManager em;                                   // Necesario para persistir los datos.

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ObjetivoFacade() {
        super(Objetivo.class);
    }

    /**
     * Persiste una nueva entidad en la base de datos.
     * @param entity Objetivo   Entidad a persistir.
     */
    @Override
    public void create(Objetivo entity) {
        super.create(entity);
    }

    /**
     * Busca todos los objetivos ingresados en el sistema.
     * Retorna la lista de EntityClass Objetivo encontrados.
     * @return List<Objetivo>   Lista de objetivos.
     */
    @Override
    public List<Objetivo> findAll() {
        return super.findAll();
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
            , final short prioridad
            , final int [] materialList
            , final int [] cantidadList) {
        
        // Si el objetivo ya existe, retornar -1.
        if (objetivoExists(nombre)){
            return -1;
        }
        // Si las fechas no son válidas, retornar -2.
        else if ( !validarFechas(fecha_inicial, fecha_final) ){
            return -2;
        }
        else{
            try{
                // Nuevo objetivo.
                Objetivo obj = new Objetivo();
                obj.setNombreObjetivo(nombre);              //  Setear nombre.
                obj.setDescripcionObjetivo(descripcion);    //  Setear descripción.
                obj.setPrioridadObjetivo(prioridad);        //  Setear prioridad.
                obj.setFechaInicial(fecha_inicial);         //  Setear fecha inicial.
                obj.setFechaLimite(fecha_final);            //  Setear fecha final.
                create(obj);                                //  Persistir objetivo.
                obj = buscarPorNombre(nombre);              //  Buscar para obtener código.
                
                // Agregar materiales al objetivo.
                objetivoMaterialFacade.agregarMaterialToObjetivo(obj.getCodObjetivo(), materialList, cantidadList);
                System.out.println("Creación de Objetivo realizada con éxito");
                
                // Retornar código satisfactorio.
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
     * Buscar objetivo según el nombre ingresado.
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
