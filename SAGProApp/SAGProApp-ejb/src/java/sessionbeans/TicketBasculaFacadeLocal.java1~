/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.TicketBascula;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jose
 */
@Local
public interface TicketBasculaFacadeLocal {

    void create(TicketBascula ticketBascula);

    void edit(TicketBascula ticketBascula);

    void remove(TicketBascula ticketBascula);

    TicketBascula find(Object id);

    List<TicketBascula> findAll();

    List<TicketBascula> findRange(int[] range);

    int count();
    
}
