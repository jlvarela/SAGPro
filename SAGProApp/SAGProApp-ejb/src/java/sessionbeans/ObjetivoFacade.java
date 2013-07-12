/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Objetivo;
import javax.ejb.Stateless;
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
    
}
