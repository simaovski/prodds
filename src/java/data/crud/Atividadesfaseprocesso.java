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
@Table(name = "atividadesfaseprocesso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atividadesfaseprocesso.findAll", query = "SELECT a FROM Atividadesfaseprocesso a"),
    @NamedQuery(name = "Atividadesfaseprocesso.findById", query = "SELECT a FROM Atividadesfaseprocesso a WHERE a.id = :id"),
    @NamedQuery(name = "Atividadesfaseprocesso.findByNomeatividade", query = "SELECT a FROM Atividadesfaseprocesso a WHERE a.nomeatividade = :nomeatividade"),
    @NamedQuery(name = "Atividadesfaseprocesso.findByDescricaoatividade", query = "SELECT a FROM Atividadesfaseprocesso a WHERE a.descricaoatividade = :descricaoatividade"),
    @NamedQuery(name = "Atividadesfaseprocesso.findByAtividadessucessoras", query = "SELECT a FROM Atividadesfaseprocesso a WHERE a.atividadessucessoras = :atividadessucessoras")})
public class Atividadesfaseprocesso implements Serializable {
    @OneToMany(mappedBy = "idatividadeprocesso")
    private Collection<Atividadesantecessoras> atividadesantecessorasCollection;
    @OneToMany(mappedBy = "idatividadeantecessoraprocesso")
    private Collection<Atividadesantecessoras> atividadesantecessorasCollection1;
    @OneToMany(mappedBy = "idatividade")
    private Collection<Atividadesequipe> atividadesequipeCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nomeatividade")
    private String nomeatividade;
    @Column(name = "descricaoatividade")
    private String descricaoatividade;
    @Column(name = "atividadessucessoras")
    private String atividadessucessoras;
    @JoinColumn(name = "idfase", referencedColumnName = "id")
    @ManyToOne
    private Fasesprocesso idfase;
    @OneToMany(mappedBy = "idatividade")
    private Collection<Atividadesantecessorasfaseprocesso> atividadesantecessorasfaseprocessoCollection;
    @OneToMany(mappedBy = "idatividadesucessora")
    private Collection<Atividadesantecessorasfaseprocesso> atividadesantecessorasfaseprocessoCollection1;

    public Atividadesfaseprocesso() {
    }

    public Atividadesfaseprocesso(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeatividade() {
        return nomeatividade;
    }

    public void setNomeatividade(String nomeatividade) {
        this.nomeatividade = nomeatividade;
    }

    public String getDescricaoatividade() {
        return descricaoatividade;
    }

    public void setDescricaoatividade(String descricaoatividade) {
        this.descricaoatividade = descricaoatividade;
    }

    public String getAtividadessucessoras() {
        return atividadessucessoras;
    }

    public void setAtividadessucessoras(String atividadessucessoras) {
        this.atividadessucessoras = atividadessucessoras;
    }

    public Fasesprocesso getIdfase() {
        return idfase;
    }

    public void setIdfase(Fasesprocesso idfase) {
        this.idfase = idfase;
    }

    @XmlTransient
    public Collection<Atividadesantecessorasfaseprocesso> getAtividadesantecessorasfaseprocessoCollection() {
        return atividadesantecessorasfaseprocessoCollection;
    }

    public void setAtividadesantecessorasfaseprocessoCollection(Collection<Atividadesantecessorasfaseprocesso> atividadesantecessorasfaseprocessoCollection) {
        this.atividadesantecessorasfaseprocessoCollection = atividadesantecessorasfaseprocessoCollection;
    }

    @XmlTransient
    public Collection<Atividadesantecessorasfaseprocesso> getAtividadesantecessorasfaseprocessoCollection1() {
        return atividadesantecessorasfaseprocessoCollection1;
    }

    public void setAtividadesantecessorasfaseprocessoCollection1(Collection<Atividadesantecessorasfaseprocesso> atividadesantecessorasfaseprocessoCollection1) {
        this.atividadesantecessorasfaseprocessoCollection1 = atividadesantecessorasfaseprocessoCollection1;
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
        if (!(object instanceof Atividadesfaseprocesso)) {
            return false;
        }
        Atividadesfaseprocesso other = (Atividadesfaseprocesso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.crud.Atividadesfaseprocesso[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Atividadesequipe> getAtividadesequipeCollection() {
        return atividadesequipeCollection;
    }

    public void setAtividadesequipeCollection(Collection<Atividadesequipe> atividadesequipeCollection) {
        this.atividadesequipeCollection = atividadesequipeCollection;
    }

    @XmlTransient
    public Collection<Atividadesantecessoras> getAtividadesantecessorasCollection() {
        return atividadesantecessorasCollection;
    }

    public void setAtividadesantecessorasCollection(Collection<Atividadesantecessoras> atividadesantecessorasCollection) {
        this.atividadesantecessorasCollection = atividadesantecessorasCollection;
    }

    @XmlTransient
    public Collection<Atividadesantecessoras> getAtividadesantecessorasCollection1() {
        return atividadesantecessorasCollection1;
    }

    public void setAtividadesantecessorasCollection1(Collection<Atividadesantecessoras> atividadesantecessorasCollection1) {
        this.atividadesantecessorasCollection1 = atividadesantecessorasCollection1;
    }
    
}
