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

    private final String[] medidasVentasValues = {"Ton", "m3"}; //  Arreglo de medidas de venta.
    private final String[] medidasProducValues = {"Ton", "m3"}; //  Arreglo de medidas de producción.
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
     * Persistir nueva entidad Material.
     *
     * @param entity Entidad a persistir,
     */
    @Override
    public void create(Material entity) {
        super.create(entity);
    }

    @Override
    public void edit(Material entity) {
        super.edit(entity); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Material entity) {
        super.remove(entity); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Obtiene la lista de todos los materiales.
     *
     * @return List<Material> Lista de todos los materiales
     */
    @Override
    public List<Material> findAll() {
        return super.findAll();
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

    /**
     * Busca a un material por su ID.
     *
     * @param material_id
     * @return
     */
    @Override
    public Material buscarPorID(final String material_id) {
        try {
            Material material = (Material) em.createNamedQuery("Material.findByCodMaterial")
                    .setParameter("codMaterial", Long.parseLong(material_id))
                    .getSingleResult();
            System.out.println("Material: '" + material_id + "' se ha encontrado con éxito");
            return material;

        } catch (NoResultException e) {
            System.out.println("Material: '" + material_id + "' no se encuentra registrado");
            return null;
        } catch (NonUniqueResultException e) {
            System.out.println("Material: '" + material_id + "', por alguna razón inesperada, se encuentra repetido");
            return null;
        }
    }

    /**
     * Agregar un nuevo material al sistema.
     *
     * @param nombre_material Nombre del material
     * @param medida_produccion_material Medida de producción del material.
     * @param medida_venta_material Medida de venta del material.
     * @return int Código de verificación de la transacción.
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

    /**
     * Editar información de un material. Para esto se ingresa el código, nuevo
     * nombre, medida de producción y venta.
     *
     * @param codMaterial
     * @param nombre_material
     * @param medida_produccion_material
     * @param medida_venta_material
     * @return
     */
    @Override
    public int editarMaterial(final String codMaterial, final String nombre_material, final String medida_produccion_material, final String medida_venta_material) {
        Material material = buscarPorID(codMaterial);
        if (material == null) {
            return -1;
        } else {
            try {
                material.setNombreMaterial(nombre_material);
                material.setMedidaProduccionMaterial(medida_produccion_material);
                material.setMedidaVentaMaterial(medida_venta_material);
                edit(material);
                System.out.println("edición del material realizada con éxito");
                return 0;
            } catch (EntityExistsException e) {
                System.out.println("Editando material: Error -> " + e.getMessage());
                return -1;
            }
        }
    }

    /**
     * Eliminar un material por su código.
     *
     * @param material_id Id del material a eliminar
     * @return int código de satisfacción de la transacción.
     */
    @Override
    public int eliminarMaterial(final String material_id) {
        Material material = buscarPorID(material_id);
        if (material == null) {
            return -1;

        } else {
            try {
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
