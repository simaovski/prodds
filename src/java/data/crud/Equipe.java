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
@Table(name = "equipe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipe.findAll", query = "SELECT e FROM Equipe e"),
    @NamedQuery(name = "Equipe.findById", query = "SELECT e FROM Equipe e WHERE e.id = :id"),
    @NamedQuery(name = "Equipe.findByNomeequipe", query = "SELECT e FROM Equipe e WHERE e.nomeequipe = :nomeequipe"),
    @NamedQuery(name = "Equipe.findByDescricao", query = "SELECT e FROM Equipe e WHERE e.descricao = :descricao")})
public class Equipe implements Serializable {
    @OneToMany(mappedBy = "idequipe")
    private Collection<Atividadesequipegerente> atividadesequipegerenteCollection;
    @OneToMany(mappedBy = "idequipe")
    private Collection<Notificacaogerente> notificacaogerenteCollection;
    @OneToMany(mappedBy = "idequipe")
    private Collection<Atividademembroequipe> atividademembroequipeCollection;
    @OneToMany(mappedBy = "idequipe")
    private Collection<Membrosequipe> membrosequipeCollection;
    @OneToMany(mappedBy = "idequipe")
    private Collection<Atividadesequipe> atividadesequipeCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nomeequipe")
    private String nomeequipe;
    @Column(name = "descricao")
    private String descricao;
    @JoinColumn(name = "idprojeto", referencedColumnName = "id")
    @ManyToOne
    private Projeto idprojeto;
    @JoinColumn(name = "idgerente", referencedColumnName = "id")
    @ManyToOne
    private Usuario idgerente;

    public Equipe() {
    }

    public Equipe(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeequipe() {
        return nomeequipe;
    }

    public void setNomeequipe(String nomeequipe) {
        this.nomeequipe = nomeequipe;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Projeto getIdprojeto() {
        return idprojeto;
    }

    public void setIdprojeto(Projeto idprojeto) {
        this.idprojeto = idprojeto;
    }

    public Usuario getIdgerente() {
        return idgerente;
    }

    public void setIdgerente(Usuario idgerente) {
        this.idgerente = idgerente;
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
        if (!(object instanceof Equipe)) {
            return false;
        }
        Equipe other = (Equipe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.crud.Equipe[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Membrosequipe> getMembrosequipeCollection() {
        return membrosequipeCollection;
    }

    public void setMembrosequipeCollection(Collection<Membrosequipe> membrosequipeCollection) {
        this.membrosequipeCollection = membrosequipeCollection;
    }

    @XmlTransient
    public Collection<Atividadesequipe> getAtividadesequipeCollection() {
        return atividadesequipeCollection;
    }

    public void setAtividadesequipeCollection(Collection<Atividadesequipe> atividadesequipeCollection) {
        this.atividadesequipeCollection = atividadesequipeCollection;
    }

    @XmlTransient
    public Collection<Atividademembroequipe> getAtividademembroequipeCollection() {
        return atividademembroequipeCollection;
    }

    public void setAtividademembroequipeCollection(Collection<Atividademembroequipe> atividademembroequipeCollection) {
        this.atividademembroequipeCollection = atividademembroequipeCollection;
    }

    @XmlTransient
    public Collection<Notificacaogerente> getNotificacaogerenteCollection() {
        return notificacaogerenteCollection;
    }

    public void setNotificacaogerenteCollection(Collection<Notificacaogerente> notificacaogerenteCollection) {
        this.notificacaogerenteCollection = notificacaogerenteCollection;
    }

    @XmlTransient
    public Collection<Atividadesequipegerente> getAtividadesequipegerenteCollection() {
        return atividadesequipegerenteCollection;
    }

    public void setAtividadesequipegerenteCollection(Collection<Atividadesequipegerente> atividadesequipegerenteCollection) {
        this.atividadesequipegerenteCollection = atividadesequipegerenteCollection;
    }
    
}
