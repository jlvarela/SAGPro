/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.EstadisticaMensual;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jose
 */
@Stateless
public class EstadisticaMensualFacade extends AbstractFacade<EstadisticaMensual> implements EstadisticaMensualFacadeLocal {
    @PersistenceContext(unitName = "SAGProApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadisticaMensualFacade() {
        super(EstadisticaMensual.class);
    }
    
}
