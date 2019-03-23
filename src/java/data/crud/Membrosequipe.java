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
@Table(name = "membrosequipe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Membrosequipe.findAll", query = "SELECT m FROM Membrosequipe m"),
    @NamedQuery(name = "Membrosequipe.findById", query = "SELECT m FROM Membrosequipe m WHERE m.id = :id")})
public class Membrosequipe implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "idequipe", referencedColumnName = "id")
    @ManyToOne
    private Equipe idequipe;
    @JoinColumn(name = "idgerente", referencedColumnName = "id")
    @ManyToOne
    private Usuario idgerente;
    @JoinColumn(name = "idmembro", referencedColumnName = "id")
    @ManyToOne
    private Usuario idmembro;

    public Membrosequipe() {
    }

    public Membrosequipe(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Equipe getIdequipe() {
        return idequipe;
    }

    public void setIdequipe(Equipe idequipe) {
        this.idequipe = idequipe;
    }

    public Usuario getIdgerente() {
        return idgerente;
    }

    public void setIdgerente(Usuario idgerente) {
        this.idgerente = idgerente;
    }

    public Usuario getIdmembro() {
        return idmembro;
    }

    public void setIdmembro(Usuario idmembro) {
        this.idmembro = idmembro;
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
        if (!(object instanceof Membrosequipe)) {
            return false;
        }
        Membrosequipe other = (Membrosequipe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.crud.Membrosequipe[ id=" + id + " ]";
    }
    
}
