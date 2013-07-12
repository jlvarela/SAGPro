/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.ProduccionDiaria;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
    
}
