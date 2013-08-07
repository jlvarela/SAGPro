/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import javax.ejb.Local;

/**
 *
 * @author Jose
 */
@Local
public interface StadisticPlannerLocal {

    void doMonthlyStadistic();
    
}
