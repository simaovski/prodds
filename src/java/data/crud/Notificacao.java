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
@Table(name = "notificacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notificacao.findAll", query = "SELECT n FROM Notificacao n"),
    @NamedQuery(name = "Notificacao.findById", query = "SELECT n FROM Notificacao n WHERE n.id = :id"),
    @NamedQuery(name = "Notificacao.findByIdprojeto", query = "SELECT n FROM Notificacao n WHERE n.idprojeto = :idprojeto"),
    @NamedQuery(name = "Notificacao.findBySolicitagerencia", query = "SELECT n FROM Notificacao n WHERE n.solicitagerencia = :solicitagerencia"),
    @NamedQuery(name = "Notificacao.findByVisualizado", query = "SELECT n FROM Notificacao n WHERE n.visualizado = :visualizado")})
public class Notificacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "idprojeto")
    private Integer idprojeto;
    @Column(name = "solicitagerencia")
    private Boolean solicitagerencia;
    @Column(name = "visualizado")
    private Boolean visualizado;
    @JoinColumn(name = "idusuario", referencedColumnName = "id")
    @ManyToOne
    private Usuario idusuario;

    public Notificacao() {
    }

    public Notificacao(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdprojeto() {
        return idprojeto;
    }

    public void setIdprojeto(Integer idprojeto) {
        this.idprojeto = idprojeto;
    }

    public Boolean getSolicitagerencia() {
        return solicitagerencia;
    }

    public void setSolicitagerencia(Boolean solicitagerencia) {
        this.solicitagerencia = solicitagerencia;
    }

    public Boolean getVisualizado() {
        return visualizado;
    }

    public void setVisualizado(Boolean visualizado) {
        this.visualizado = visualizado;
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
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
        if (!(object instanceof Notificacao)) {
            return false;
        }
        Notificacao other = (Notificacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.crud.Notificacao[ id=" + id + " ]";
    }
    
}
