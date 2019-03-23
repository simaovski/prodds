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
@Table(name = "faseprojeto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Faseprojeto.findAll", query = "SELECT f FROM Faseprojeto f"),
    @NamedQuery(name = "Faseprojeto.findById", query = "SELECT f FROM Faseprojeto f WHERE f.id = :id")})
public class Faseprojeto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "idfase", referencedColumnName = "id")
    @ManyToOne
    private Fasesprocesso idfase;
    @JoinColumn(name = "idusuariogerenteprocesso", referencedColumnName = "id")
    @ManyToOne
    private Usuario idusuariogerenteprocesso;
    @JoinColumn(name = "idprojeto", referencedColumnName = "id")
    @ManyToOne
    private Projeto idprojeto;

    public Faseprojeto() {
    }

    public Faseprojeto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Fasesprocesso getIdfase() {
        return idfase;
    }

    public void setIdfase(Fasesprocesso idfase) {
        this.idfase = idfase;
    }

    public Usuario getIdusuariogerenteprocesso() {
        return idusuariogerenteprocesso;
    }

    public void setIdusuariogerenteprocesso(Usuario idgerenteprocesso) {
        this.idusuariogerenteprocesso = idgerenteprocesso;
    }

    public Projeto getIdprojeto() {
        return idprojeto;
    }

    public void setIdprojeto(Projeto idprojeto) {
        this.idprojeto = idprojeto;
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
        if (!(object instanceof Faseprojeto)) {
            return false;
        }
        Faseprojeto other = (Faseprojeto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.crud.Faseprojeto[ id=" + id + " ]";
    }
    
}
