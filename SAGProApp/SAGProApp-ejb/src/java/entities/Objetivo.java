/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jose
 */
@Entity
@Table(name = "objetivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Objetivo.findAll", query = "SELECT o FROM Objetivo o"),
    @NamedQuery(name = "Objetivo.findByCodObjetivo", query = "SELECT o FROM Objetivo o WHERE o.codObjetivo = :codObjetivo"),
    @NamedQuery(name = "Objetivo.findByNombreObjetivo", query = "SELECT o FROM Objetivo o WHERE o.nombreObjetivo = :nombreObjetivo"),
    @NamedQuery(name = "Objetivo.findByPrioridadObjetivo", query = "SELECT o FROM Objetivo o WHERE o.prioridadObjetivo = :prioridadObjetivo"),
    @NamedQuery(name = "Objetivo.findByFechaInicial", query = "SELECT o FROM Objetivo o WHERE o.fechaInicial = :fechaInicial"),
    @NamedQuery(name = "Objetivo.findByFechaLimite", query = "SELECT o FROM Objetivo o WHERE o.fechaLimite = :fechaLimite")})
public class Objetivo implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "objetivo")
    private List<ObjetivoMaterial> objetivoMaterialList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COD_OBJETIVO")
    private Integer codObjetivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE_OBJETIVO")
    private String nombreObjetivo;
    @Column(name = "PRIORIDAD_OBJETIVO")
    private Short prioridadObjetivo;
    @Lob
    @Size(max = 65535)
    @Column(name = "DESCRIPCION_OBJETIVO")
    private String descripcionObjetivo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_INICIAL")
    @Temporal(TemporalType.DATE)
    private Date fechaInicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_LIMITE")
    @Temporal(TemporalType.DATE)
    private Date fechaLimite;
    @JoinTable(name = "objetivo_material", joinColumns = {
        @JoinColumn(name = "COD_OBJETIVO", referencedColumnName = "COD_OBJETIVO")}, inverseJoinColumns = {
        @JoinColumn(name = "COD_MATERIAL", referencedColumnName = "COD_MATERIAL")})
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Material.class)
    private List<Material> materialList;

    public Objetivo() {
    }

    public Objetivo(Integer codObjetivo) {
        this.codObjetivo = codObjetivo;
    }

    public Objetivo(Integer codObjetivo, String nombreObjetivo, Date fechaInicial, Date fechaLimite) {
        this.codObjetivo = codObjetivo;
        this.nombreObjetivo = nombreObjetivo;
        this.fechaInicial = fechaInicial;
        this.fechaLimite = fechaLimite;
    }

    public Integer getCodObjetivo() {
        return codObjetivo;
    }

    public void setCodObjetivo(Integer codObjetivo) {
        this.codObjetivo = codObjetivo;
    }

    public String getNombreObjetivo() {
        return nombreObjetivo;
    }

    public void setNombreObjetivo(String nombreObjetivo) {
        this.nombreObjetivo = nombreObjetivo;
    }

    public Short getPrioridadObjetivo() {
        return prioridadObjetivo;
    }

    public void setPrioridadObjetivo(Short prioridadObjetivo) {
        this.prioridadObjetivo = prioridadObjetivo;
    }

    public String getDescripcionObjetivo() {
        return descripcionObjetivo;
    }

    public void setDescripcionObjetivo(String descripcionObjetivo) {
        this.descripcionObjetivo = descripcionObjetivo;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    @XmlTransient
    public List<Material> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<Material> materialList) {
        this.materialList = materialList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codObjetivo != null ? codObjetivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Objetivo)) {
            return false;
        }
        Objetivo other = (Objetivo) object;
        if ((this.codObjetivo == null && other.codObjetivo != null) || (this.codObjetivo != null && !this.codObjetivo.equals(other.codObjetivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Objetivo[ codObjetivo=" + codObjetivo + " ]";
    }

    @XmlTransient
    public List<ObjetivoMaterial> getObjetivoMaterialList() {
        return objetivoMaterialList;
    }

    public void setObjetivoMaterialList(List<ObjetivoMaterial> objetivoMaterialList) {
        this.objetivoMaterialList = objetivoMaterialList;
    }
    
}