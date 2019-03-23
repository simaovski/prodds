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
@Table(name = "localizacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Localizacao.findAll", query = "SELECT l FROM Localizacao l"),
    @NamedQuery(name = "Localizacao.findById", query = "SELECT l FROM Localizacao l WHERE l.id = :id"),
    @NamedQuery(name = "Localizacao.findByPais", query = "SELECT l FROM Localizacao l WHERE l.pais = :pais"),
    @NamedQuery(name = "Localizacao.findByEstado", query = "SELECT l FROM Localizacao l WHERE l.estado = :estado"),
    @NamedQuery(name = "Localizacao.findByCidade", query = "SELECT l FROM Localizacao l WHERE l.cidade = :cidade"),
    @NamedQuery(name = "Localizacao.findByIdequipe", query = "SELECT l FROM Localizacao l WHERE l.idequipe = :idequipe")})
public class Localizacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "pais")
    private String pais;
    @Column(name = "estado")
    private String estado;
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "idequipe")
    private Integer idequipe;
    @JoinColumn(name = "idgerente", referencedColumnName = "id")
    @ManyToOne
    private Usuario idgerente;

    public Localizacao() {
    }

    public Localizacao(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Integer getIdequipe() {
        return idequipe;
    }

    public void setIdequipe(Integer idequipe) {
        this.idequipe = idequipe;
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
        if (!(object instanceof Localizacao)) {
            return false;
        }
        Localizacao other = (Localizacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.crud.Localizacao[ id=" + id + " ]";
    }
    
}
