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
@Table(name = "atividadesequipegerente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atividadesequipegerente.findAll", query = "SELECT a FROM Atividadesequipegerente a"),
    @NamedQuery(name = "Atividadesequipegerente.findById", query = "SELECT a FROM Atividadesequipegerente a WHERE a.id = :id")})
public class Atividadesequipegerente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "idatividade", referencedColumnName = "id")
    @ManyToOne
    private Atividade idatividade;
    @JoinColumn(name = "idequipe", referencedColumnName = "id")
    @ManyToOne
    private Equipe idequipe;
    @JoinColumn(name = "idusuariogerente", referencedColumnName = "id")
    @ManyToOne
    private Usuario idusuariogerente;

    public Atividadesequipegerente() {
    }

    public Atividadesequipegerente(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Atividade getIdatividade() {
        return idatividade;
    }

    public void setIdatividade(Atividade idatividade) {
        this.idatividade = idatividade;
    }

    public Equipe getIdequipe() {
        return idequipe;
    }

    public void setIdequipe(Equipe idequipe) {
        this.idequipe = idequipe;
    }

    public Usuario getIdusuariogerente() {
        return idusuariogerente;
    }

    public void setIdusuariogerente(Usuario idusuariogerente) {
        this.idusuariogerente = idusuariogerente;
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
        if (!(object instanceof Atividadesequipegerente)) {
            return false;
        }
        Atividadesequipegerente other = (Atividadesequipegerente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.crud.Atividadesequipegerente[ id=" + id + " ]";
    }
    
}
