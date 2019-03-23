/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.crud;

import data.crud.Processo;
import data.crud.Usuario;
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
@Table(name = "projeto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Projeto.findAll", query = "SELECT p FROM Projeto p"),
    @NamedQuery(name = "Projeto.findById", query = "SELECT p FROM Projeto p WHERE p.id = :id"),
    @NamedQuery(name = "Projeto.findByDatacriacao", query = "SELECT p FROM Projeto p WHERE p.datacriacao = :datacriacao"),
    @NamedQuery(name = "Projeto.findByNomeprojeto", query = "SELECT p FROM Projeto p WHERE p.nomeprojeto = :nomeprojeto"),
    @NamedQuery(name = "Projeto.findByDescricao", query = "SELECT p FROM Projeto p WHERE p.descricao = :descricao")})
public class Projeto implements Serializable {
    @OneToMany(mappedBy = "idprojeto")
    private Collection<Faseprojeto> faseprojetoCollection;
    @OneToMany(mappedBy = "idprojeto")
    private Collection<Gerenteprocesso> gerenteprocessoCollection;
    @OneToMany(mappedBy = "idprojeto")
    private Collection<Gerenteprojeto> gerenteprojetoCollection;
    @OneToMany(mappedBy = "idprojeto")
    private Collection<Notificacaogerente> notificacaogerenteCollection;
    @OneToMany(mappedBy = "idprojeto")
    private Collection<Logusuario> logusuarioCollection;
    @OneToMany(mappedBy = "idprojeto")
    private Collection<Equipe> equipeCollection;
    @OneToMany(mappedBy = "projeto")
    private Collection<Gerente> gerenteCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "datacriacao")
    private String datacriacao;
    @Column(name = "nomeprojeto")
    private String nomeprojeto;
    @Column(name = "descricao")
    private String descricao;
    @JoinColumn(name = "idprocesso", referencedColumnName = "id")
    @ManyToOne
    private Processo idprocesso;
    @JoinColumn(name = "idadm", referencedColumnName = "id")
    @ManyToOne
    private Usuario idadm;

    public Projeto() {
    }

    public Projeto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDatacriacao() {
        return datacriacao;
    }

    public void setDatacriacao(String datacriacao) {
        this.datacriacao = datacriacao;
    }

    public String getNomeprojeto() {
        return nomeprojeto;
    }

    public void setNomeprojeto(String nomeprojeto) {
        this.nomeprojeto = nomeprojeto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Processo getIdprocesso() {
        return idprocesso;
    }

    public void setIdprocesso(Processo idprocesso) {
        this.idprocesso = idprocesso;
    }

    public Usuario getIdadm() {
        return idadm;
    }

    public void setIdadm(Usuario idadm) {
        this.idadm = idadm;
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
        if (!(object instanceof Projeto)) {
            return false;
        }
        Projeto other = (Projeto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.dao.Projeto[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Gerente> getGerenteCollection() {
        return gerenteCollection;
    }

    public void setGerenteCollection(Collection<Gerente> gerenteCollection) {
        this.gerenteCollection = gerenteCollection;
    }

    @XmlTransient
    public Collection<Equipe> getEquipeCollection() {
        return equipeCollection;
    }

    public void setEquipeCollection(Collection<Equipe> equipeCollection) {
        this.equipeCollection = equipeCollection;
    }

    @XmlTransient
    public Collection<Logusuario> getLogusuarioCollection() {
        return logusuarioCollection;
    }

    public void setLogusuarioCollection(Collection<Logusuario> logusuarioCollection) {
        this.logusuarioCollection = logusuarioCollection;
    }

    @XmlTransient
    public Collection<Notificacaogerente> getNotificacaogerenteCollection() {
        return notificacaogerenteCollection;
    }

    public void setNotificacaogerenteCollection(Collection<Notificacaogerente> notificacaogerenteCollection) {
        this.notificacaogerenteCollection = notificacaogerenteCollection;
    }

    @XmlTransient
    public Collection<Gerenteprojeto> getGerenteprojetoCollection() {
        return gerenteprojetoCollection;
    }

    public void setGerenteprojetoCollection(Collection<Gerenteprojeto> gerenteprojetoCollection) {
        this.gerenteprojetoCollection = gerenteprojetoCollection;
    }

    @XmlTransient
    public Collection<Gerenteprocesso> getGerenteprocessoCollection() {
        return gerenteprocessoCollection;
    }

    public void setGerenteprocessoCollection(Collection<Gerenteprocesso> gerenteprocessoCollection) {
        this.gerenteprocessoCollection = gerenteprocessoCollection;
    }

    @XmlTransient
    public Collection<Faseprojeto> getFaseprojetoCollection() {
        return faseprojetoCollection;
    }

    public void setFaseprojetoCollection(Collection<Faseprojeto> faseprojetoCollection) {
        this.faseprojetoCollection = faseprojetoCollection;
    }
    
}
