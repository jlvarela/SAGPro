/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.ProduccionDiaria;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jose
 */
@Local
public interface ProduccionDiariaFacadeLocal {

    void create(ProduccionDiaria produccionDiaria);

    void edit(ProduccionDiaria produccionDiaria);

    void remove(ProduccionDiaria produccionDiaria);

    ProduccionDiaria find(Object id);

    List<ProduccionDiaria> findAll();

    List<ProduccionDiaria> findRange(int[] range);

    int count();
    
}
