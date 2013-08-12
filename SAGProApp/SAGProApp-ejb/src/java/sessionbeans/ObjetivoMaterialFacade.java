package sessionbeans;

import entities.ObjetivoMaterial;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jose
 */
@Stateless
public class ObjetivoMaterialFacade extends AbstractFacade<ObjetivoMaterial> implements ObjetivoMaterialFacadeLocal {
    @PersistenceContext(unitName = "SAGProApp-ejbPU")
    private EntityManager em;                           // Necesario para persistir y obtener entidades.

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ObjetivoMaterialFacade() {
        super(ObjetivoMaterial.class);
    }

    /**
     * Agrega el vínculo entre la diversidad de materiales asociados a un objetivo particular.
     * Devuelve distintos códigos en función de las condiciones para realizar la operación
     * o el resultado de la operación.
     * 
     * Se asume que existe una asociación directa entre el índice de un elemento de la lista
     * de materiales y la lista de cantidades. Lo anterior se especifica como: Dado un elemento
     * con índice N en la lista de materiales, la cantidad correspondiente a dicho material
     * se encuentra en el índice N de la lista de cantidades.
     * 
     * @param codObjetivo Código del objetivo al cual se asocian los materiales.
     * @param materialList Lista de los códigos de los materiales.
     * @param cantidadList Lista de las cantidades de cada material.
     * @return 
     */
    @Override
    public int agregarMaterialToObjetivo(final int codObjetivo, final int [] materialList, final int [] cantidadList) {
        if (!isListaMaterEqualToCantidad(materialList, cantidadList))
            return -2;
        else if (!isListaMaterCorrect(materialList))
            return -3;
        else if (!isListaMaterCorrect(cantidadList))
            return -4;
        else if (!isObjetivo(codObjetivo))
            return -5;
        else{
            //  Para cada material
            for(int i=0; i < materialList.length; i++){
                // Relacionar material a objetivo.
                ObjetivoMaterial om = new ObjetivoMaterial(codObjetivo, materialList[i]);
                om.setCantidadObjetivo(cantidadList[i]);
                create(om);
            }
        }
        return 0;
    }
    
    /**
     * Perisistir entidad ObjetivoMaterial.
     * @param entity    ObjetivoMaterial    Entidad a persistir.
     */
    @Override
    public void create(ObjetivoMaterial entity) {
        super.create(entity);
    }
    
    /**
     * Determina si la lista de materiales tiene el mismo tamaño que la lista de cantidades.
     * @param materiales
     * @param cantidades
     * @return 
     */
    private Boolean isListaMaterEqualToCantidad(final int [] materiales, final int [] cantidades){
        return materiales.length == cantidades.length;
    }
    
    /**
     * Verifica que la lista de materiales esté correctamente definida
     * @param materiales
     * @return 
     */
    private Boolean isListaMaterCorrect(final int [] materiales){
        for ( int i = 0; i < materiales.length; i++ ){
            if (  materiales[i] <= 0 )
                return false;
        }
        return true;
    }
    
    /**
     * Determinar si código de objetivo ingresado es válido.
     * True si objetivo es válido. False caso contrario.
     * 
     * @param codObjetivo   int Código de objetivo ingresado.
     * @return Boolean  
     */
    private Boolean isObjetivo(final int codObjetivo) {
        return codObjetivo > 0;
    }
    
    /**
     * Buscar todos los materiales relacionados con un objetivo específico
     * ingresado como parámetro.
     * @param codObjetivo Objetivo a buscar.
     * @return List<ObjetivoMaterial>
     */
    @Override
    public List<ObjetivoMaterial> buscarPorObjetivo(Integer codObjetivo) {
        return em.createNamedQuery("ObjetivoMaterial.findByCodObjetivo")
                .setParameter("codObjetivo", codObjetivo)
                .getResultList();
    }
    
    
    
}
