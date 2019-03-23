/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.crud;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author padrao
 */
@Entity
@Table(name = "statusprojeto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Statusprojeto.findAll", query = "SELECT s FROM Statusprojeto s"),
    @NamedQuery(name = "Statusprojeto.findById", query = "SELECT s FROM Statusprojeto s WHERE s.id = :id"),
    @NamedQuery(name = "Statusprojeto.findByIdprojeto", query = "SELECT s FROM Statusprojeto s WHERE s.idprojeto = :idprojeto"),
    @NamedQuery(name = "Statusprojeto.findByStatus", query = "SELECT s FROM Statusprojeto s WHERE s.status = :status")})
public class Statusprojeto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "idprojeto")
    private Integer idprojeto;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "estado")
    private int estado;
    @JoinColumn(name = "idadm", referencedColumnName = "id")
    @ManyToOne
    private Usuario idadm;
    @Column(name = "datafinal")
    private String datafinal;

    public Statusprojeto() {
    }

    public Statusprojeto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdprojeto() {
        return idprojeto;
    }

    public void setIdprojeto(Integer idprojeto) {
        this.idprojeto = idprojeto;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Usuario getIdadm() {
        return idadm;
    }

    public void setIdadm(Usuario idadm) {
        this.idadm = idadm;
    }

    public String getDatafinal() {
        return datafinal;
    }

    public void setDatafinal(String datafinal) {
        this.datafinal = datafinal;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Statusprojeto)) {
            return false;
        }
        Statusprojeto other = (Statusprojeto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.crud.Statusprojeto[ id=" + id + " ]";
    }
    
}
