/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Material;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jose
 */
@Stateless
public class MaterialFacade extends AbstractFacade<Material> implements MaterialFacadeLocal {

    private final String[] medidasVentasValues = {"Ton", "m3"};
    private final String[] medidasProducValues = {"Ton", "m3"};
    @PersistenceContext(unitName = "SAGProApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MaterialFacade() {
        super(Material.class);
    }

    /**
     *
     * @return Lista de todos los materiales
     */
    @Override
    public List<Material> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param nombre_material
     * @param medida_produccion_material
     * @param medida_venta_material
     * @return
     */
    @Override
    public int agregarMaterial(final String nombre_material, final String medida_produccion_material, final String medida_venta_material) {

        if (!materialExists(nombre_material)) {
            try {
                Material material = new Material();
                material.setNombreMaterial(nombre_material);
                material.setMedidaProduccionMaterial(medida_produccion_material);
                material.setMedidaVentaMaterial(medida_venta_material);
                create(material);
                System.out.println("Creación de material realizada con éxito");
                return 0;
            } catch (EntityExistsException e) {
                System.out.println("Ingresando Material: Error -> " + e.getMessage());
                return -1;
            }
        } else {
            return -1;
        }
    }

    @Override
    public void create(Material entity) {
        super.create(entity); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Busca por la existencia de un Material para un nombre particular. En caso
     * de encontrarlo retorna True. Caso contrario, retorna False.
     *
     * @param material_name String Nombre del Material
     * @return Boolean Existencia del material buscado.
     */
    private Boolean materialExists(String material_name) {
        int resultados;
        resultados = em.createNamedQuery("Material.findByNombreMaterial")
                .setParameter("nombreMaterial", material_name)
                .getResultList().size();

        return resultados != 0;
    }

    @Override
    public String[] getMedidasVentasValues() {
        return medidasVentasValues;
    }

    @Override
    public String[] getMedidasProducValues() {
        return medidasProducValues;
    }

    @Override
    public String[] getValuesVentaMaterial() {
        return getMedidasVentasValues();
    }

    @Override
    public String[] getValuesProducMaterial() {
        return getMedidasProducValues();
    }

    @Override
    public Material buscarPorNombre(final String material_name) {
        try {
            Material usuario = (Material) em.createNamedQuery("Material.findByNombreMaterial")
                    .setParameter("nombreMaterial", Long.parseLong(material_name))
                    .getSingleResult();
            System.out.println("Material: '" + material_name + "' se ha encontrado con éxito");
            return usuario;

        } catch (NoResultException e) {
            System.out.println("Material: '" + material_name + "' no se encuentra registrado");
            return null;
        } catch (NonUniqueResultException e) {
            System.out.println("Material: '" + material_name + "', por alguna razón inesperada, se encuentra repetido");
            return null;
        }
    }

    @Override
    public int eliminarMaterial(final String material_name) {

        if (!materialExists(material_name)) {
            return -1;

        } else {
            try {
                Material material = buscarPorNombre(material_name);
                remove(material);
                System.out.println("Eliminación del material realizada con éxito");
                return 0;
            } catch (EntityExistsException e) {
                System.out.println("Eliminando material: Error -> " + e.getMessage());
                return -1;
            }
        }
    }
}
