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
@Table(name = "statusatividade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Statusatividade.findAll", query = "SELECT s FROM Statusatividade s"),
    @NamedQuery(name = "Statusatividade.findById", query = "SELECT s FROM Statusatividade s WHERE s.id = :id"),
    @NamedQuery(name = "Statusatividade.findByStatus", query = "SELECT s FROM Statusatividade s WHERE s.status = :status")})
public class Statusatividade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "status")
    private Boolean status;
    @JoinColumn(name = "idatividade", referencedColumnName = "id")
    @ManyToOne
    private Atividademembroequipe idatividade;

    public Statusatividade() {
    }

    public Statusatividade(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Atividademembroequipe getIdatividade() {
        return idatividade;
    }

    public void setIdatividade(Atividademembroequipe idatividade) {
        this.idatividade = idatividade;
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
        if (!(object instanceof Statusatividade)) {
            return false;
        }
        Statusatividade other = (Statusatividade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.crud.Statusatividade[ id=" + id + " ]";
    }
    
}
