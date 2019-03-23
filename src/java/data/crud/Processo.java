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
@Table(name = "processo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Processo.findAll", query = "SELECT p FROM Processo p"),
    @NamedQuery(name = "Processo.findById", query = "SELECT p FROM Processo p WHERE p.id = :id"),
    @NamedQuery(name = "Processo.findByNomeprocesso", query = "SELECT p FROM Processo p WHERE p.nomeprocesso = :nomeprocesso"),
    @NamedQuery(name = "Processo.findByTotalfases", query = "SELECT p FROM Processo p WHERE p.totalfases = :totalfases"),
    @NamedQuery(name = "Processo.findByDescricao", query = "SELECT p FROM Processo p WHERE p.descricao = :descricao")})
public class Processo implements Serializable {
    @OneToMany(mappedBy = "idprocesso")
    private Collection<Projeto> projetoCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nomeprocesso")
    private String nomeprocesso;
    @Column(name = "totalfases")
    private Integer totalfases;
    @Column(name = "descricao")
    private String descricao;
    @JoinColumn(name = "idusuario", referencedColumnName = "id")
    @ManyToOne
    private Usuario idusuario;
    @OneToMany(mappedBy = "idprocesso")
    private Collection<Fasesprocesso> fasesprocessoCollection;

    public Processo() {
    }

    public Processo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeprocesso() {
        return nomeprocesso;
    }

    public void setNomeprocesso(String nomeprocesso) {
        this.nomeprocesso = nomeprocesso;
    }

    public Integer getTotalfases() {
        return totalfases;
    }

    public void setTotalfases(Integer totalfases) {
        this.totalfases = totalfases;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    @XmlTransient
    public Collection<Fasesprocesso> getFasesprocessoCollection() {
        return fasesprocessoCollection;
    }

    public void setFasesprocessoCollection(Collection<Fasesprocesso> fasesprocessoCollection) {
        this.fasesprocessoCollection = fasesprocessoCollection;
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
        if (!(object instanceof Processo)) {
            return false;
        }
        Processo other = (Processo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.crud.Processo[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Projeto> getProjetoCollection() {
        return projetoCollection;
    }

    public void setProjetoCollection(Collection<Projeto> projetoCollection) {
        this.projetoCollection = projetoCollection;
    }
    
}
