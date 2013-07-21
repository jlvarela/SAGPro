/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jose
 */
@Entity
@Table(name = "objetivo_material")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ObjetivoMaterial.findAll", query = "SELECT o FROM ObjetivoMaterial o"),
    @NamedQuery(name = "ObjetivoMaterial.findByCodObjetivo", query = "SELECT o FROM ObjetivoMaterial o WHERE o.objetivoMaterialPK.codObjetivo = :codObjetivo"),
    @NamedQuery(name = "ObjetivoMaterial.findByCodMaterial", query = "SELECT o FROM ObjetivoMaterial o WHERE o.objetivoMaterialPK.codMaterial = :codMaterial"),
    @NamedQuery(name = "ObjetivoMaterial.findByCantidadObjetivo", query = "SELECT o FROM ObjetivoMaterial o WHERE o.cantidadObjetivo = :cantidadObjetivo")})
public class ObjetivoMaterial implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ObjetivoMaterialPK objetivoMaterialPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CANTIDAD_OBJETIVO")
    private int cantidadObjetivo;
    @JoinColumn(name = "COD_OBJETIVO", referencedColumnName = "COD_OBJETIVO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Objetivo objetivo;
    @JoinColumn(name = "COD_MATERIAL", referencedColumnName = "COD_MATERIAL", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Material material;

    public ObjetivoMaterial() {
    }

    public ObjetivoMaterial(ObjetivoMaterialPK objetivoMaterialPK) {
        this.objetivoMaterialPK = objetivoMaterialPK;
    }

    public ObjetivoMaterial(ObjetivoMaterialPK objetivoMaterialPK, int cantidadObjetivo) {
        this.objetivoMaterialPK = objetivoMaterialPK;
        this.cantidadObjetivo = cantidadObjetivo;
    }

    public ObjetivoMaterial(int codObjetivo, int codMaterial) {
        this.objetivoMaterialPK = new ObjetivoMaterialPK(codObjetivo, codMaterial);
    }

    public ObjetivoMaterialPK getObjetivoMaterialPK() {
        return objetivoMaterialPK;
    }

    public void setObjetivoMaterialPK(ObjetivoMaterialPK objetivoMaterialPK) {
        this.objetivoMaterialPK = objetivoMaterialPK;
    }

    public int getCantidadObjetivo() {
        return cantidadObjetivo;
    }

    public void setCantidadObjetivo(int cantidadObjetivo) {
        this.cantidadObjetivo = cantidadObjetivo;
    }

    public Objetivo getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(Objetivo objetivo) {
        this.objetivo = objetivo;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (objetivoMaterialPK != null ? objetivoMaterialPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ObjetivoMaterial)) {
            return false;
        }
        ObjetivoMaterial other = (ObjetivoMaterial) object;
        if ((this.objetivoMaterialPK == null && other.objetivoMaterialPK != null) || (this.objetivoMaterialPK != null && !this.objetivoMaterialPK.equals(other.objetivoMaterialPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ObjetivoMaterial[ objetivoMaterialPK=" + objetivoMaterialPK + " ]";
    }
    
}
