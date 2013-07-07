/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jose
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByRutUser", query = "SELECT u FROM User u WHERE u.rutUser = :rutUser"),
    @NamedQuery(name = "User.findByNombreUser", query = "SELECT u FROM User u WHERE u.nombreUser = :nombreUser"),
    @NamedQuery(name = "User.findByApellidoUser", query = "SELECT u FROM User u WHERE u.apellidoUser = :apellidoUser"),
    @NamedQuery(name = "User.findByPasswordUser", query = "SELECT u FROM User u WHERE u.passwordUser = :passwordUser"),
    @NamedQuery(name = "User.findByRoleUser", query = "SELECT u FROM User u WHERE u.roleUser = :roleUser"),
    @NamedQuery(name = "User.findByEmailUser", query = "SELECT u FROM User u WHERE u.emailUser = :emailUser")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RUT_USER")
    private Long rutUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE_USER")
    private String nombreUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "APELLIDO_USER")
    private String apellidoUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "PASSWORD_USER")
    private String passwordUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "ROLE_USER")
    private String roleUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "EMAIL_USER")
    private String emailUser;

    public User() {
    }

    public User(Long rutUser) {
        this.rutUser = rutUser;
    }

    public User(Long rutUser, String nombreUser, String apellidoUser, String passwordUser, String roleUser, String emailUser) {
        this.rutUser = rutUser;
        this.nombreUser = nombreUser;
        this.apellidoUser = apellidoUser;
        this.passwordUser = passwordUser;
        this.roleUser = roleUser;
        this.emailUser = emailUser;
    }

    public Long getRutUser() {
        return rutUser;
    }

    public void setRutUser(Long rutUser) {
        this.rutUser = rutUser;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getApellidoUser() {
        return apellidoUser;
    }

    public void setApellidoUser(String apellidoUser) {
        this.apellidoUser = apellidoUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public String getRoleUser() {
        return roleUser;
    }

    public void setRoleUser(String roleUser) {
        this.roleUser = roleUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rutUser != null ? rutUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.rutUser == null && other.rutUser != null) || (this.rutUser != null && !this.rutUser.equals(other.rutUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.User[ rutUser=" + rutUser + " ]";
    }
    
}
