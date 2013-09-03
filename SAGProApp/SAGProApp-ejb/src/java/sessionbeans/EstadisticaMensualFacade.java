/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.EstadisticaMensual;
import entities.Material;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jose
 */
@Stateless
public class EstadisticaMensualFacade extends AbstractFacade<EstadisticaMensual> implements EstadisticaMensualFacadeLocal {
    @EJB
    private MaterialFacadeLocal materialFacade;
    @PersistenceContext(unitName = "SAGProApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadisticaMensualFacade() {
        super(EstadisticaMensual.class);
    }

    @Override
    public List<EstadisticaMensual> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EstadisticaMensual buscarPorMes(final Date mes, final int codMaterial) {
        try{
            Material mat = materialFacade.buscarPorID(String.valueOf(codMaterial));
            EstadisticaMensual estm = (EstadisticaMensual)em.createQuery("SELECT em FROM EstadisticaMensual em WHERE em.codMaterial = :codMaterial and em.mes = :mes ")
                    .setParameter("codMaterial", mat)
                    .setParameter("mes", mes)
                    .getSingleResult();
            return estm;
        }
        catch(NoResultException e ){
            return null;
        }
    }
}
