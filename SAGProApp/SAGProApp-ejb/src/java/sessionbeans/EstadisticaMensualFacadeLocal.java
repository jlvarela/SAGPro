/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.EstadisticaMensual;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jose
 */
@Local
public interface EstadisticaMensualFacadeLocal {

    void create(EstadisticaMensual estadisticaMensual);

    void edit(EstadisticaMensual estadisticaMensual);

    void remove(EstadisticaMensual estadisticaMensual);

    EstadisticaMensual find(Object id);

    List<EstadisticaMensual> findAll();

    List<EstadisticaMensual> findRange(int[] range);

    int count();
    
}
