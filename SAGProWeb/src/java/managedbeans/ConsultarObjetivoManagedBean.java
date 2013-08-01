/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Objetivo;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sessionbeans.ObjetivoFacadeLocal;

/**
 *
 * @author Jose
 */
@ManagedBean(name = "consultarObjetivoManagedBean")
@ViewScoped
public class ConsultarObjetivoManagedBean implements Serializable {
    @EJB
    private ObjetivoFacadeLocal objetivoFacade;
    
    private List<Objetivo> listaObjetivos;
    private Objetivo selectedObjetivo;
    private SimpleDateFormat sdf;
    

    /**
     * Creates a new instance of ConsultarObjetivoManagedBean
     */
    public ConsultarObjetivoManagedBean() {
        sdf = new SimpleDateFormat("dd/MM/YYYY");
    }
    
    /**
     * Obtener la lista de todos los objetivos del sistema.
     * 
     */
    @PostConstruct
    public void init(){
        listaObjetivos = objetivoFacade.findAll();
    }

    public List<Objetivo> getListaObjetivos() {
        return listaObjetivos;
    }

    public void setListaObjetivos(List<Objetivo> listaObjetivos) {
        this.listaObjetivos = listaObjetivos;
    }

    public Objetivo getSelectedObjetivo() {
        return selectedObjetivo;
    }

    public void setSelectedObjetivo(Objetivo selectedObjetivo) {
        this.selectedObjetivo = selectedObjetivo;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }
    
}
