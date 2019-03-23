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
@Table(name = "gerenteprojeto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gerenteprojeto.findAll", query = "SELECT g FROM Gerenteprojeto g"),
    @NamedQuery(name = "Gerenteprojeto.findById", query = "SELECT g FROM Gerenteprojeto g WHERE g.id = :id"),
    @NamedQuery(name = "Gerenteprojeto.findByVisualizado", query = "SELECT g FROM Gerenteprojeto g WHERE g.visualizado = :visualizado")})
public class Gerenteprojeto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "visualizado")
    private Boolean visualizado;
    @JoinColumn(name = "idprojeto", referencedColumnName = "id")
    @ManyToOne
    private Projeto idprojeto;
    @JoinColumn(name = "idgerente", referencedColumnName = "id")
    @ManyToOne
    private Usuario idgerente;

    public Gerenteprojeto() {
    }

    public Gerenteprojeto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getVisualizado() {
        return visualizado;
    }

    public void setVisualizado(Boolean visualizado) {
        this.visualizado = visualizado;
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
        if (!(object instanceof Gerenteprojeto)) {
            return false;
        }
        Gerenteprojeto other = (Gerenteprojeto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.crud.Gerenteprojeto[ id=" + id + " ]";
    }
    
}
