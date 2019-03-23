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
@Table(name = "atividadesequipe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atividadesequipe.findAll", query = "SELECT a FROM Atividadesequipe a"),
    @NamedQuery(name = "Atividadesequipe.findById", query = "SELECT a FROM Atividadesequipe a WHERE a.id = :id")})
public class Atividadesequipe implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "idatividade", referencedColumnName = "id")
    @ManyToOne
    private Atividadesfaseprocesso idatividade;
    @JoinColumn(name = "idequipe", referencedColumnName = "id")
    @ManyToOne
    private Equipe idequipe;

    public Atividadesequipe() {
    }

    public Atividadesequipe(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Atividadesfaseprocesso getIdatividade() {
        return idatividade;
    }

    public void setIdatividade(Atividadesfaseprocesso idatividade) {
        this.idatividade = idatividade;
    }

    public Equipe getIdequipe() {
        return idequipe;
    }

    public void setIdequipe(Equipe idequipe) {
        this.idequipe = idequipe;
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
        if (!(object instanceof Atividadesequipe)) {
            return false;
        }
        Atividadesequipe other = (Atividadesequipe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.crud.Atividadesequipe[ id=" + id + " ]";
    }
    
}
