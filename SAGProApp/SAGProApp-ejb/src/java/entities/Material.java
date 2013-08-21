/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jose
 */
@Entity
@Table(name = "material")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Material.findAll", query = "SELECT m FROM Material m"),
    @NamedQuery(name = "Material.findByCodMaterial", query = "SELECT m FROM Material m WHERE m.codMaterial = :codMaterial"),
    @NamedQuery(name = "Material.findByNombreMaterial", query = "SELECT m FROM Material m WHERE m.nombreMaterial = :nombreMaterial"),
    @NamedQuery(name = "Material.findByMedidaProduccionMaterial", query = "SELECT m FROM Material m WHERE m.medidaProduccionMaterial = :medidaProduccionMaterial"),
    @NamedQuery(name = "Material.findByMedidaVentaMaterial", query = "SELECT m FROM Material m WHERE m.medidaVentaMaterial = :medidaVentaMaterial")})
public class Material implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "material")
    private List<ObjetivoMaterial> objetivoMaterialList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "material")
    private List<ProduccionDiaria> produccionDiariaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codMaterial")
    private List<EstadisticaMensual> estadisticaMensualCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COD_MATERIAL")
    private Integer codMaterial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE_MATERIAL")
    private String nombreMaterial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "MEDIDA_PRODUCCION_MATERIAL")
    private String medidaProduccionMaterial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "MEDIDA_VENTA_MATERIAL")
    private String medidaVentaMaterial;
    @ManyToMany(mappedBy = "materialList")
    private List<Objetivo> objetivoList;

    public Material() {
    }

    public Material(Integer codMaterial) {
        this.codMaterial = codMaterial;
    }

    public Material(Integer codMaterial, String nombreMaterial, String medidaProduccionMaterial, String medidaVentaMaterial) {
        this.codMaterial = codMaterial;
        this.nombreMaterial = nombreMaterial;
        this.medidaProduccionMaterial = medidaProduccionMaterial;
        this.medidaVentaMaterial = medidaVentaMaterial;
    }

    public Integer getCodMaterial() {
        return codMaterial;
    }

    public void setCodMaterial(Integer codMaterial) {
        this.codMaterial = codMaterial;
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }

    public String getMedidaProduccionMaterial() {
        return medidaProduccionMaterial;
    }

    public void setMedidaProduccionMaterial(String medidaProduccionMaterial) {
        this.medidaProduccionMaterial = medidaProduccionMaterial;
    }

    public String getMedidaVentaMaterial() {
        return medidaVentaMaterial;
    }

    public void setMedidaVentaMaterial(String medidaVentaMaterial) {
        this.medidaVentaMaterial = medidaVentaMaterial;
    }

    @XmlTransient
    public List<Objetivo> getObjetivoList() {
        return objetivoList;
    }

    public void setObjetivoList(List<Objetivo> objetivoList) {
        this.objetivoList = objetivoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codMaterial != null ? codMaterial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Material)) {
            return false;
        }
        Material other = (Material) object;
        if ((this.codMaterial == null && other.codMaterial != null) || (this.codMaterial != null && !this.codMaterial.equals(other.codMaterial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Material[ codMaterial=" + codMaterial + " ]";
    }

    @XmlTransient
    public Collection<ProduccionDiaria> getProduccionDiariaCollection() {
        return produccionDiariaCollection;
    }

    public void setProduccionDiariaCollection(List<ProduccionDiaria> produccionDiariaCollection) {
        this.produccionDiariaCollection = produccionDiariaCollection;
    }

    @XmlTransient
    public Collection<EstadisticaMensual> getEstadisticaMensualCollection() {
        return estadisticaMensualCollection;
    }

    public void setEstadisticaMensualCollection(List<EstadisticaMensual> estadisticaMensualCollection) {
        this.estadisticaMensualCollection = estadisticaMensualCollection;
    }

    @XmlTransient
    public List<ObjetivoMaterial> getObjetivoMaterialList() {
        return objetivoMaterialList;
    }

    public void setObjetivoMaterialList(List<ObjetivoMaterial> objetivoMaterialList) {
        this.objetivoMaterialList = objetivoMaterialList;
    }
    
}