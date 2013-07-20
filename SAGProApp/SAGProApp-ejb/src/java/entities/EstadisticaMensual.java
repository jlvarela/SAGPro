/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jose
 */
@Entity
@Table(name = "estadistica_mensual")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadisticaMensual.findAll", query = "SELECT e FROM EstadisticaMensual e"),
    @NamedQuery(name = "EstadisticaMensual.findByCodEstadisticaMensual", query = "SELECT e FROM EstadisticaMensual e WHERE e.codEstadisticaMensual = :codEstadisticaMensual"),
    @NamedQuery(name = "EstadisticaMensual.findByPromedioDiario", query = "SELECT e FROM EstadisticaMensual e WHERE e.promedioDiario = :promedioDiario"),
    @NamedQuery(name = "EstadisticaMensual.findByDesvEstandar", query = "SELECT e FROM EstadisticaMensual e WHERE e.desvEstandar = :desvEstandar"),
    @NamedQuery(name = "EstadisticaMensual.findByVarianza", query = "SELECT e FROM EstadisticaMensual e WHERE e.varianza = :varianza"),
    @NamedQuery(name = "EstadisticaMensual.findByProduccionMensual", query = "SELECT e FROM EstadisticaMensual e WHERE e.produccionMensual = :produccionMensual"),
    @NamedQuery(name = "EstadisticaMensual.findByMes", query = "SELECT e FROM EstadisticaMensual e WHERE e.mes = :mes"),
    @NamedQuery(name = "EstadisticaMensual.findByFechaRealizacion", query = "SELECT e FROM EstadisticaMensual e WHERE e.fechaRealizacion = :fechaRealizacion")})
public class EstadisticaMensual implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_ESTADISTICA_MENSUAL")
    private Integer codEstadisticaMensual;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROMEDIO_DIARIO")
    private float promedioDiario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DESV_ESTANDAR")
    private float desvEstandar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VARIANZA")
    private float varianza;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCCION_MENSUAL")
    private int produccionMensual;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MES")
    @Temporal(TemporalType.TIME)
    private Date mes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_REALIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRealizacion;
    @JoinColumn(name = "COD_MATERIAL", referencedColumnName = "COD_MATERIAL")
    @ManyToOne(optional = false)
    private Material codMaterial;

    public EstadisticaMensual() {
    }

    public EstadisticaMensual(Integer codEstadisticaMensual) {
        this.codEstadisticaMensual = codEstadisticaMensual;
    }

    public EstadisticaMensual(Integer codEstadisticaMensual, float promedioDiario, float desvEstandar, float varianza, int produccionMensual, Date mes, Date fechaRealizacion) {
        this.codEstadisticaMensual = codEstadisticaMensual;
        this.promedioDiario = promedioDiario;
        this.desvEstandar = desvEstandar;
        this.varianza = varianza;
        this.produccionMensual = produccionMensual;
        this.mes = mes;
        this.fechaRealizacion = fechaRealizacion;
    }

    public Integer getCodEstadisticaMensual() {
        return codEstadisticaMensual;
    }

    public void setCodEstadisticaMensual(Integer codEstadisticaMensual) {
        this.codEstadisticaMensual = codEstadisticaMensual;
    }

    public float getPromedioDiario() {
        return promedioDiario;
    }

    public void setPromedioDiario(float promedioDiario) {
        this.promedioDiario = promedioDiario;
    }

    public float getDesvEstandar() {
        return desvEstandar;
    }

    public void setDesvEstandar(float desvEstandar) {
        this.desvEstandar = desvEstandar;
    }

    public float getVarianza() {
        return varianza;
    }

    public void setVarianza(float varianza) {
        this.varianza = varianza;
    }

    public int getProduccionMensual() {
        return produccionMensual;
    }

    public void setProduccionMensual(int produccionMensual) {
        this.produccionMensual = produccionMensual;
    }

    public Date getMes() {
        return mes;
    }

    public void setMes(Date mes) {
        this.mes = mes;
    }

    public Date getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(Date fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public Material getCodMaterial() {
        return codMaterial;
    }

    public void setCodMaterial(Material codMaterial) {
        this.codMaterial = codMaterial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEstadisticaMensual != null ? codEstadisticaMensual.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadisticaMensual)) {
            return false;
        }
        EstadisticaMensual other = (EstadisticaMensual) object;
        if ((this.codEstadisticaMensual == null && other.codEstadisticaMensual != null) || (this.codEstadisticaMensual != null && !this.codEstadisticaMensual.equals(other.codEstadisticaMensual))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.EstadisticaMensual[ codEstadisticaMensual=" + codEstadisticaMensual + " ]";
    }
    
}
