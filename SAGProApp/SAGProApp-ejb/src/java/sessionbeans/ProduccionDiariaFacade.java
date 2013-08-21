/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.ProduccionDiaria;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class ProduccionDiariaFacade extends AbstractFacade<ProduccionDiaria> implements ProduccionDiariaFacadeLocal {

    @PersistenceContext(unitName = "SAGProApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProduccionDiariaFacade() {
        super(ProduccionDiaria.class);
    }

    @Override
    public void create(ProduccionDiaria entity) {
        super.create(entity); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void edit(ProduccionDiaria entity) {
        super.edit(entity); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProduccionDiaria find(Object id) {
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ProduccionDiaria> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Busca la producción para los materiales ingresados en una fecha.
     *
     * @param produccion_fecha Fecha inicial del rango.
     * @return
     */
    @Override
    public List<ProduccionDiaria> buscarPorFecha(final Date produccion_fecha) {
        try {
            List<ProduccionDiaria> produccionDiaria = (List<ProduccionDiaria>) em.createNamedQuery("ProduccionDiaria.findByFechaDiariaEstadistica")
                    .setParameter("fechaDiariaEstadistica", produccion_fecha)
                    .getResultList();
            System.out.println("Producción: '" + produccion_fecha + "' se ha encontrado con éxito");
            return produccionDiaria;

        } catch (NoResultException e) {
            System.out.println("Producción: '" + produccion_fecha + "' no se encuentra registrado");
            return new ArrayList<ProduccionDiaria>();
        }
    }

    /**
     * Busca la producción para un material dada una fecha.
     *
     * @param codMaterial Código del material.
     * @param produccion_fecha Fecha inicial del rango.
     * @return
     */
    @Override
    public ProduccionDiaria buscarProduccion(final Date produccion_fecha, final int codMaterial) {
        try {
            ProduccionDiaria produccion = (ProduccionDiaria) em.createQuery("SELECT p FROM ProduccionDiaria p WHERE p.produccionDiariaPK.fechaDiariaEstadistica = :fechaDiariaEstadistica and p.produccionDiariaPK.codMaterial = :codMaterial")
                    .setParameter("fechaDiariaEstadistica", produccion_fecha)
                    .setParameter("codMaterial", codMaterial)
                    .getSingleResult();
            System.out.println("Producción: '" + produccion_fecha + " " + codMaterial + "' se ha encontrado con éxito");
            return produccion;
        } catch (NoResultException e) {
            System.out.println("Producción: '" + produccion_fecha + " " + codMaterial + "' no se encuentra registrado");
            return null;
        } catch (NonUniqueResultException e) {
            System.out.println("Producción: '" + produccion_fecha + " " + codMaterial + "', por alguna razón inesperada, se encuentra repetido");
            return null;
        }
    }

    /**
     * Busca la producción para un material dado un rango de fechas.
     *
     * @param cod_mat Código del material.
     * @param fecha_inicial Fecha inicial del rango.
     * @param fecha_final Fecha final del rango.
     * @return
     */
    @Override
    public List<ProduccionDiaria> buscarPorRango(final int cod_mat, final Date fecha_inicial, final Date fecha_final) {
        List<ProduccionDiaria> lista;
        lista = em.createQuery("SELECT p FROM ProduccionDiaria p WHERE p.produccionDiariaPK.codMaterial = :codMaterial and p.produccionDiariaPK.fechaDiariaEstadistica BETWEEN :fecha_inicial and :fecha_final")
                .setParameter("fecha_inicial", fecha_inicial)
                .setParameter("fecha_final", fecha_final)
                .setParameter("codMaterial", cod_mat)
                .getResultList();
        return lista;
    }

    /**
     * Agregar una nueva producción diaria al sistema.
     *
     * @param codMaterial Código del material
     * @param cantidadMaterial Cantidad del material.
     * @return int Código de verificación de la transacción.
     */
    @Override
    public int agregarProduccionDiaria(final int codMaterial, final int cantidadMaterial) {
        if (codMaterial > 0 && cantidadMaterial > 0) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.clear(Calendar.MINUTE);
            cal.clear(Calendar.SECOND);
            cal.clear(Calendar.MILLISECOND);

            ProduccionDiaria prod = new ProduccionDiaria(cal.getTime(), codMaterial);
            prod.setProduccionMaterial(cantidadMaterial);
            create(prod);
            return 0;
        } else {
            return -1;  // Invalid arguments
        }

    }

    /**
     * Editar información de una producción. Para esto se ingresa el código,
     * nuevo nombre, medida de producción y venta.
     *
     * @param codMaterial Código del material
     * @param produccionFecha Fecha de ingreso de la producción
     * @param cantidad Cantidad del material.
     * @return int Código de verificación de la transacción.
     */
    @Override
    public int editarProduccion(final int codMaterial, final Date produccionFecha, final int cantidad) {
        try {
            ProduccionDiaria produccion = buscarProduccion(produccionFecha, codMaterial);
            produccion.setProduccionMaterial(cantidad);
            edit(produccion);
            System.out.println("edición de produccion realizada con éxito");
            return 0;
        } catch (EntityExistsException e) {
            System.out.println("Editando produccion: Error -> " + e.getMessage());
            return -1;
        }
    }
}
