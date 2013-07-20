/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Objetivo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jose
 */
@Local
public interface ObjetivoFacadeLocal {

    void create(Objetivo objetivo);

    void edit(Objetivo objetivo);

    void remove(Objetivo objetivo);

    Objetivo find(Object id);

    List<Objetivo> findAll();

    List<Objetivo> findRange(int[] range);

    int count();
    
}
