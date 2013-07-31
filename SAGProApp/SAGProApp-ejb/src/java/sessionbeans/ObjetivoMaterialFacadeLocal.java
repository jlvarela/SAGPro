/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.ObjetivoMaterial;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jose
 */
@Local
public interface ObjetivoMaterialFacadeLocal {

    void create(ObjetivoMaterial objetivoMaterial);

    void edit(ObjetivoMaterial objetivoMaterial);

    void remove(ObjetivoMaterial objetivoMaterial);

    ObjetivoMaterial find(Object id);

    List<ObjetivoMaterial> findAll();

    List<ObjetivoMaterial> findRange(int[] range);

    int count();

    int agregarMaterialToObjetivo(final int codObjetivo, final int [] materialList, final int [] cantidadList);
    
}
