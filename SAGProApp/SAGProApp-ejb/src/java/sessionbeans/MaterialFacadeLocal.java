/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Material;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jose
 */
@Local
public interface MaterialFacadeLocal {

    void create(Material material);

    void edit(Material material);

    void remove(Material material);

    Material find(Object id);

    List<Material> findAll();

    List<Material> findRange(int[] range);

    int count();

    int agregarMaterial(final String nombre_material, final String medida_produccion_material, final String medida_venta_material);

    String[] getValuesVentaMaterial();

    String[] getValuesProducMaterial();
    
    Material buscarPorNombre(final String material_name);
    
    public int eliminarMaterial(final String material_name);

    public String[] getMedidasVentasValues();

    public String[] getMedidasProducValues();
    
}
