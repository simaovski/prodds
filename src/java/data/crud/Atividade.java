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
@Table(name = "atividade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atividade.findAll", query = "SELECT a FROM Atividade a"),
    @NamedQuery(name = "Atividade.findById", query = "SELECT a FROM Atividade a WHERE a.id = :id"),
    @NamedQuery(name = "Atividade.findByNomeatividade", query = "SELECT a FROM Atividade a WHERE a.nomeatividade = :nomeatividade"),
    @NamedQuery(name = "Atividade.findByDescricao", query = "SELECT a FROM Atividade a WHERE a.descricao = :descricao"),
    @NamedQuery(name = "Atividade.findByAtividadessucessoras", query = "SELECT a FROM Atividade a WHERE a.atividadessucessoras = :atividadessucessoras")})
public class Atividade implements Serializable {
    @OneToMany(mappedBy = "idatividade")
    private Collection<Atividadesequipegerente> atividadesequipegerenteCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nomeatividade")
    private String nomeatividade;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "atividadessucessoras")
    private String atividadessucessoras;
    @JoinColumn(name = "idfase", referencedColumnName = "id")
    @ManyToOne
    private Fasesprocesso idfase;
    @JoinColumn(name = "idgerente", referencedColumnName = "id")
    @ManyToOne
    private Usuario idgerente;
    @OneToMany(mappedBy = "idatividade")
    private Collection<Atividadesantecessoras> atividadesantecessorasCollection;
    @OneToMany(mappedBy = "idatividadeantecessora")
    private Collection<Atividadesantecessoras> atividadesantecessorasCollection1;

    public Atividade() {
    }

    public Atividade(Integer id) {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public Usuario getIdgerente() {
        return idgerente;
    }

    public void setIdgerente(Usuario idgerente) {
        this.idgerente = idgerente;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Atividade)) {
            return false;
        }
        Atividade other = (Atividade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.crud.Atividade[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Atividadesequipegerente> getAtividadesequipegerenteCollection() {
        return atividadesequipegerenteCollection;
    }

    public void setAtividadesequipegerenteCollection(Collection<Atividadesequipegerente> atividadesequipegerenteCollection) {
        this.atividadesequipegerenteCollection = atividadesequipegerenteCollection;
    }
}
