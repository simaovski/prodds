/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.crud;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author padrao
 */
@Entity
@Table(name = "fasesprocesso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fasesprocesso.findAll", query = "SELECT f FROM Fasesprocesso f"),
    @NamedQuery(name = "Fasesprocesso.findById", query = "SELECT f FROM Fasesprocesso f WHERE f.id = :id"),
    @NamedQuery(name = "Fasesprocesso.findByNomefase", query = "SELECT f FROM Fasesprocesso f WHERE f.nomefase = :nomefase"),
    @NamedQuery(name = "Fasesprocesso.findByDescricaofase", query = "SELECT f FROM Fasesprocesso f WHERE f.descricaofase = :descricaofase")})
public class Fasesprocesso implements Serializable {
    @OneToMany(mappedBy = "idfase")
    private Collection<Faseprojeto> faseprojetoCollection;
    @OneToMany(mappedBy = "idfase")
    private Collection<Atividade> atividadeCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nomefase")
    private String nomefase;
    @Column(name = "descricaofase")
    private String descricaofase;
    @JoinColumn(name = "idprocesso", referencedColumnName = "id")
    @ManyToOne
    private Processo idprocesso;
    @OneToMany(mappedBy = "idfase")
    private Collection<Atividadesfaseprocesso> atividadesfaseprocessoCollection;

    public Fasesprocesso() {
    }

    public Fasesprocesso(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomefase() {
        return nomefase;
    }

    public void setNomefase(String nomefase) {
        this.nomefase = nomefase;
    }

    public String getDescricaofase() {
        return descricaofase;
    }

    public void setDescricaofase(String descricaofase) {
        this.descricaofase = descricaofase;
    }

    public Processo getIdprocesso() {
        return idprocesso;
    }

    public void setIdprocesso(Processo idprocesso) {
        this.idprocesso = idprocesso;
    }

    @XmlTransient
    public Collection<Atividadesfaseprocesso> getAtividadesfaseprocessoCollection() {
        return atividadesfaseprocessoCollection;
    }

    public void setAtividadesfaseprocessoCollection(Collection<Atividadesfaseprocesso> atividadesfaseprocessoCollection) {
        this.atividadesfaseprocessoCollection = atividadesfaseprocessoCollection;
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
        if (!(object instanceof Fasesprocesso)) {
            return false;
        }
        Fasesprocesso other = (Fasesprocesso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.crud.Fasesprocesso[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Atividade> getAtividadeCollection() {
        return atividadeCollection;
    }

    public void setAtividadeCollection(Collection<Atividade> atividadeCollection) {
        this.atividadeCollection = atividadeCollection;
    }

    @XmlTransient
    public Collection<Faseprojeto> getFaseprojetoCollection() {
        return faseprojetoCollection;
    }

    public void setFaseprojetoCollection(Collection<Faseprojeto> faseprojetoCollection) {
        this.faseprojetoCollection = faseprojetoCollection;
    }
    
}
