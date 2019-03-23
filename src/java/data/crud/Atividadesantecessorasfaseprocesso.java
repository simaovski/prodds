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
@Table(name = "atividadesantecessorasfaseprocesso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atividadesantecessorasfaseprocesso.findAll", query = "SELECT a FROM Atividadesantecessorasfaseprocesso a"),
    @NamedQuery(name = "Atividadesantecessorasfaseprocesso.findById", query = "SELECT a FROM Atividadesantecessorasfaseprocesso a WHERE a.id = :id")})
public class Atividadesantecessorasfaseprocesso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "idatividade", referencedColumnName = "id")
    @ManyToOne
    private Atividadesfaseprocesso idatividade;
    @JoinColumn(name = "idatividadesucessora", referencedColumnName = "id")
    @ManyToOne
    private Atividadesfaseprocesso idatividadesucessora;

    public Atividadesantecessorasfaseprocesso() {
    }

    public Atividadesantecessorasfaseprocesso(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Atividadesfaseprocesso getIdatividade() {
        return idatividade;
    }

    public void setIdatividade(Atividadesfaseprocesso idatividade) {
        this.idatividade = idatividade;
    }

    public Atividadesfaseprocesso getIdatividadesucessora() {
        return idatividadesucessora;
    }

    public void setIdatividadesucessora(Atividadesfaseprocesso idatividadesucessora) {
        this.idatividadesucessora = idatividadesucessora;
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
        if (!(object instanceof Atividadesantecessorasfaseprocesso)) {
            return false;
        }
        Atividadesantecessorasfaseprocesso other = (Atividadesantecessorasfaseprocesso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.crud.Atividadesantecessorasfaseprocesso[ id=" + id + " ]";
    }
    
}
