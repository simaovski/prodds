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
@Table(name = "atividademembroequipe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atividademembroequipe.findAll", query = "SELECT a FROM Atividademembroequipe a"),
    @NamedQuery(name = "Atividademembroequipe.findById", query = "SELECT a FROM Atividademembroequipe a WHERE a.id = :id"),
    @NamedQuery(name = "Atividademembroequipe.findByIdatividade", query = "SELECT a FROM Atividademembroequipe a WHERE a.idatividade = :idatividade"),
    @NamedQuery(name = "Atividademembroequipe.findByIdfuncao", query = "SELECT a FROM Atividademembroequipe a WHERE a.idfuncao = :idfuncao"),
    @NamedQuery(name = "Atividademembroequipe.findByStatus", query = "SELECT a FROM Atividademembroequipe a WHERE a.status = :status")})
public class Atividademembroequipe implements Serializable {
    @OneToMany(mappedBy = "idatividade")
    private Collection<Statusatividade> statusatividadeCollection;
    @OneToMany(mappedBy = "idatividade")
    private Collection<Artefato> artefatoCollection;
    @OneToMany(mappedBy = "atividade")
    private Collection<Logusuario> logusuarioCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "idatividade")
    private Integer idatividade;
    @Column(name = "idfuncao")
    private Integer idfuncao;
    @Column(name = "status")
    private Boolean status;
    @JoinColumn(name = "idequipe", referencedColumnName = "id")
    @ManyToOne
    private Equipe idequipe;
    @JoinColumn(name = "idmembro", referencedColumnName = "id")
    @ManyToOne
    private Usuario idmembro;
    @JoinColumn(name = "idusuariocadatv", referencedColumnName = "id")
    @ManyToOne
    private Usuario idusuariocadatv;

    public Atividademembroequipe() {
    }

    public Atividademembroequipe(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdatividade() {
        return idatividade;
    }

    public void setIdatividade(Integer idatividade) {
        this.idatividade = idatividade;
    }

    public Integer getIdfuncao() {
        return idfuncao;
    }

    public void setIdfuncao(Integer idfuncao) {
        this.idfuncao = idfuncao;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Equipe getIdequipe() {
        return idequipe;
    }

    public void setIdequipe(Equipe idequipe) {
        this.idequipe = idequipe;
    }

    public Usuario getIdmembro() {
        return idmembro;
    }

    public void setIdmembro(Usuario idmembro) {
        this.idmembro = idmembro;
    }

    public Usuario getIdusuariocadatv() {
        return idusuariocadatv;
    }

    public void setIdusuariocadatv(Usuario idusuariocadatv) {
        this.idusuariocadatv = idusuariocadatv;
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
        if (!(object instanceof Atividademembroequipe)) {
            return false;
        }
        Atividademembroequipe other = (Atividademembroequipe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.crud.Atividademembroequipe[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Logusuario> getLogusuarioCollection() {
        return logusuarioCollection;
    }

    public void setLogusuarioCollection(Collection<Logusuario> logusuarioCollection) {
        this.logusuarioCollection = logusuarioCollection;
    }

    @XmlTransient
    public Collection<Artefato> getArtefatoCollection() {
        return artefatoCollection;
    }

    public void setArtefatoCollection(Collection<Artefato> artefatoCollection) {
        this.artefatoCollection = artefatoCollection;
    }

    @XmlTransient
    public Collection<Statusatividade> getStatusatividadeCollection() {
        return statusatividadeCollection;
    }

    public void setStatusatividadeCollection(Collection<Statusatividade> statusatividadeCollection) {
        this.statusatividadeCollection = statusatividadeCollection;
    }
    
}
