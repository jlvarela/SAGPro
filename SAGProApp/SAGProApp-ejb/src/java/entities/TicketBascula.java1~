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
@Table(name = "ticket_bascula")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TicketBascula.findAll", query = "SELECT t FROM TicketBascula t"),
    @NamedQuery(name = "TicketBascula.findByCodTicket", query = "SELECT t FROM TicketBascula t WHERE t.codTicket = :codTicket"),
    @NamedQuery(name = "TicketBascula.findByTonelajeTicket", query = "SELECT t FROM TicketBascula t WHERE t.tonelajeTicket = :tonelajeTicket"),
    @NamedQuery(name = "TicketBascula.findByFechaEmisionTicket", query = "SELECT t FROM TicketBascula t WHERE t.fechaEmisionTicket = :fechaEmisionTicket")})
public class TicketBascula implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_TICKET")
    private Integer codTicket;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TONELAJE_TICKET")
    private int tonelajeTicket;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_EMISION_TICKET")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmisionTicket;
    @JoinColumn(name = "COD_MATERIAL", referencedColumnName = "COD_MATERIAL")
    @ManyToOne(optional = false)
    private Material codMaterial;

    public TicketBascula() {
    }

    public TicketBascula(Integer codTicket) {
        this.codTicket = codTicket;
    }

    public TicketBascula(Integer codTicket, int tonelajeTicket, Date fechaEmisionTicket) {
        this.codTicket = codTicket;
        this.tonelajeTicket = tonelajeTicket;
        this.fechaEmisionTicket = fechaEmisionTicket;
    }

    public Integer getCodTicket() {
        return codTicket;
    }

    public void setCodTicket(Integer codTicket) {
        this.codTicket = codTicket;
    }

    public int getTonelajeTicket() {
        return tonelajeTicket;
    }

    public void setTonelajeTicket(int tonelajeTicket) {
        this.tonelajeTicket = tonelajeTicket;
    }

    public Date getFechaEmisionTicket() {
        return fechaEmisionTicket;
    }

    public void setFechaEmisionTicket(Date fechaEmisionTicket) {
        this.fechaEmisionTicket = fechaEmisionTicket;
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
        hash += (codTicket != null ? codTicket.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TicketBascula)) {
            return false;
        }
        TicketBascula other = (TicketBascula) object;
        if ((this.codTicket == null && other.codTicket != null) || (this.codTicket != null && !this.codTicket.equals(other.codTicket))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TicketBascula[ codTicket=" + codTicket + " ]";
    }
    
}
