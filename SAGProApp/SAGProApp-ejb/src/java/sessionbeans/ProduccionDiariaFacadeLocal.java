/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.ProduccionDiaria;
import java.util.Date;
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

    int agregarProduccionDiaria(final int codMaterial, final int cantidadMaterial);
    
    public List<ProduccionDiaria> buscarPorFecha(final Date produccion_fecha);
    
    public ProduccionDiaria buscarProduccion(final Date produccion_fecha, final int codMaterial);
    
    public int editarProduccion(final int codMaterial, final Date produccionFecha, final int cantidad);

    public List<ProduccionDiaria> buscarPorRango(final int cod_mat, final Date fecha_inicial, final Date fecha_final);
   
    
}
