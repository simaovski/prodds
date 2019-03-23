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
@Table(name = "atividadesantecessoras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atividadesantecessoras.findAll", query = "SELECT a FROM Atividadesantecessoras a"),
    @NamedQuery(name = "Atividadesantecessoras.findById", query = "SELECT a FROM Atividadesantecessoras a WHERE a.id = :id")})
public class Atividadesantecessoras implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "idatividade", referencedColumnName = "id")
    @ManyToOne
    private Atividade idatividade;
    @JoinColumn(name = "idatividadeantecessora", referencedColumnName = "id")
    @ManyToOne
    private Atividade idatividadeantecessora;
    @JoinColumn(name = "idatividadeprocesso", referencedColumnName = "id")
    @ManyToOne
    private Atividadesfaseprocesso idatividadeprocesso;
    @JoinColumn(name = "idatividadeantecessoraprocesso", referencedColumnName = "id")
    @ManyToOne
    private Atividadesfaseprocesso idatividadeantecessoraprocesso;

    public Atividadesantecessoras() {
    }

    public Atividadesantecessoras(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Atividade getIdatividade() {
        return idatividade;
    }

    public void setIdatividade(Atividade idatividade) {
        this.idatividade = idatividade;
    }

    public Atividade getIdatividadeantecessora() {
        return idatividadeantecessora;
    }

    public void setIdatividadeantecessora(Atividade idatividadeantecessora) {
        this.idatividadeantecessora = idatividadeantecessora;
    }

    public Atividadesfaseprocesso getIdatividadeprocesso() {
        return idatividadeprocesso;
    }

    public void setIdatividadeprocesso(Atividadesfaseprocesso idatividadeprocesso) {
        this.idatividadeprocesso = idatividadeprocesso;
    }

    public Atividadesfaseprocesso getIdatividadeantecessoraprocesso() {
        return idatividadeantecessoraprocesso;
    }

    public void setIdatividadeantecessoraprocesso(Atividadesfaseprocesso idatividadeantecessoraprocesso) {
        this.idatividadeantecessoraprocesso = idatividadeantecessoraprocesso;
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
        if (!(object instanceof Atividadesantecessoras)) {
            return false;
        }
        Atividadesantecessoras other = (Atividadesantecessoras) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.crud.Atividadesantecessoras[ id=" + id + " ]";
    }
    
}
