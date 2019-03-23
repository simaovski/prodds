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
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findById", query = "SELECT u FROM Usuario u WHERE u.id = :id"),
    @NamedQuery(name = "Usuario.findByNomeusuario", query = "SELECT u FROM Usuario u WHERE u.nomeusuario = :nomeusuario"),
    @NamedQuery(name = "Usuario.findByEmailusuario", query = "SELECT u FROM Usuario u WHERE u.emailusuario = :emailusuario"),
    @NamedQuery(name = "Usuario.findBySenha", query = "SELECT u FROM Usuario u WHERE u.senha = :senha"),
    @NamedQuery(name = "Usuario.findByFuncaousuario", query = "SELECT u FROM Usuario u WHERE u.funcaousuario = :funcaousuario")})
public class Usuario implements Serializable {
    @OneToMany(mappedBy = "idusuariogerente")
    private Collection<Atividadesequipegerente> atividadesequipegerenteCollection;
    @OneToMany(mappedBy = "idgerente")
    private Collection<Gerenteprocesso> gerenteprocessoCollection;
    @OneToMany(mappedBy = "idgerente")
    private Collection<Gerenteprojeto> gerenteprojetoCollection;
    @OneToMany(mappedBy = "idusuario")
    private Collection<Notificacaogerente> notificacaogerenteCollection;
    @OneToMany(mappedBy = "idgerente")
    private Collection<Notificacaogerente> notificacaogerenteCollection1;
    @OneToMany(mappedBy = "idusuario")
    private Collection<Logusuario> logusuarioCollection;
    @OneToMany(mappedBy = "idmembro")
    private Collection<Atividademembroequipe> atividademembroequipeCollection;
    @OneToMany(mappedBy = "idusuariocadatv")
    private Collection<Atividademembroequipe> atividademembroequipeCollection1;
    @OneToMany(mappedBy = "idgerente")
    private Collection<Atividade> atividadeCollection;
    @OneToMany(mappedBy = "idgerente")
    private Collection<Membrosequipe> membrosequipeCollection;
    @OneToMany(mappedBy = "idmembro")
    private Collection<Membrosequipe> membrosequipeCollection1;
    @OneToMany(mappedBy = "idgerente")
    private Collection<Localizacao> localizacaoCollection;
    @OneToMany(mappedBy = "idgerente")
    private Collection<Equipe> equipeCollection;
    @OneToMany(mappedBy = "idusuario")
    private Collection<Notificacao> notificacaoCollection;
    @OneToMany(mappedBy = "idusuariogerente")
    private Collection<Gerente> gerenteCollection;
    @OneToMany(mappedBy = "idusuarioadm")
    private Collection<Gerente> gerenteCollection1;
    @OneToMany(mappedBy = "idadm")
    private Collection<Statusprojeto> statusprojetoCollection;
    @OneToMany(mappedBy = "idadm")
    private Collection<Projeto> projetoCollection;
    @OneToMany(mappedBy = "idusuario")
    private Collection<Processo> processoCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nomeusuario")
    private String nomeusuario;
    @Column(name = "emailusuario")
    private String emailusuario;
    @Column(name = "senha")
    private String senha;
    @Column(name = "funcaousuario")
    private Integer funcaousuario;

    public Usuario() {
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeusuario() {
        return nomeusuario;
    }

    public void setNomeusuario(String nomeusuario) {
        this.nomeusuario = nomeusuario;
    }

    public String getEmailusuario() {
        return emailusuario;
    }

    public void setEmailusuario(String emailusuario) {
        this.emailusuario = emailusuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getFuncaousuario() {
        return funcaousuario;
    }

    public void setFuncaousuario(Integer funcaousuario) {
        this.funcaousuario = funcaousuario;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.crud.Usuario[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Processo> getProcessoCollection() {
        return processoCollection;
    }

    public void setProcessoCollection(Collection<Processo> processoCollection) {
        this.processoCollection = processoCollection;
    }

    @XmlTransient
    public Collection<Projeto> getProjetoCollection() {
        return projetoCollection;
    }

    public void setProjetoCollection(Collection<Projeto> projetoCollection) {
        this.projetoCollection = projetoCollection;
    }

    @XmlTransient
    public Collection<Statusprojeto> getStatusprojetoCollection() {
        return statusprojetoCollection;
    }

    public void setStatusprojetoCollection(Collection<Statusprojeto> statusprojetoCollection) {
        this.statusprojetoCollection = statusprojetoCollection;
    }

    @XmlTransient
    public Collection<Notificacao> getNotificacaoCollection() {
        return notificacaoCollection;
    }

    public void setNotificacaoCollection(Collection<Notificacao> notificacaoCollection) {
        this.notificacaoCollection = notificacaoCollection;
    }

    @XmlTransient
    public Collection<Gerente> getGerenteCollection() {
        return gerenteCollection;
    }

    public void setGerenteCollection(Collection<Gerente> gerenteCollection) {
        this.gerenteCollection = gerenteCollection;
    }

    @XmlTransient
    public Collection<Gerente> getGerenteCollection1() {
        return gerenteCollection1;
    }

    public void setGerenteCollection1(Collection<Gerente> gerenteCollection1) {
        this.gerenteCollection1 = gerenteCollection1;
    }

    @XmlTransient
    public Collection<Equipe> getEquipeCollection() {
        return equipeCollection;
    }

    public void setEquipeCollection(Collection<Equipe> equipeCollection) {
        this.equipeCollection = equipeCollection;
    }

    @XmlTransient
    public Collection<Membrosequipe> getMembrosequipeCollection() {
        return membrosequipeCollection;
    }

    public void setMembrosequipeCollection(Collection<Membrosequipe> membrosequipeCollection) {
        this.membrosequipeCollection = membrosequipeCollection;
    }

    @XmlTransient
    public Collection<Membrosequipe> getMembrosequipeCollection1() {
        return membrosequipeCollection1;
    }

    public void setMembrosequipeCollection1(Collection<Membrosequipe> membrosequipeCollection1) {
        this.membrosequipeCollection1 = membrosequipeCollection1;
    }

    @XmlTransient
    public Collection<Localizacao> getLocalizacaoCollection() {
        return localizacaoCollection;
    }

    public void setLocalizacaoCollection(Collection<Localizacao> localizacaoCollection) {
        this.localizacaoCollection = localizacaoCollection;
    }

    @XmlTransient
    public Collection<Atividade> getAtividadeCollection() {
        return atividadeCollection;
    }

    public void setAtividadeCollection(Collection<Atividade> atividadeCollection) {
        this.atividadeCollection = atividadeCollection;
    }    

    @XmlTransient
    public Collection<Atividademembroequipe> getAtividademembroequipeCollection() {
        return atividademembroequipeCollection;
    }

    public void setAtividademembroequipeCollection(Collection<Atividademembroequipe> atividademembroequipeCollection) {
        this.atividademembroequipeCollection = atividademembroequipeCollection;
    }

    @XmlTransient
    public Collection<Atividademembroequipe> getAtividademembroequipeCollection1() {
        return atividademembroequipeCollection1;
    }

    public void setAtividademembroequipeCollection1(Collection<Atividademembroequipe> atividademembroequipeCollection1) {
        this.atividademembroequipeCollection1 = atividademembroequipeCollection1;
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
    public Collection<Notificacaogerente> getNotificacaogerenteCollection1() {
        return notificacaogerenteCollection1;
    }

    public void setNotificacaogerenteCollection1(Collection<Notificacaogerente> notificacaogerenteCollection1) {
        this.notificacaogerenteCollection1 = notificacaogerenteCollection1;
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
    public Collection<Atividadesequipegerente> getAtividadesequipegerenteCollection() {
        return atividadesequipegerenteCollection;
    }

    public void setAtividadesequipegerenteCollection(Collection<Atividadesequipegerente> atividadesequipegerenteCollection) {
        this.atividadesequipegerenteCollection = atividadesequipegerenteCollection;
    }
}
