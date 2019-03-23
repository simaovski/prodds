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
import javax.persistence.Lob;
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
@Table(name = "artefato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artefato.findAll", query = "SELECT a FROM Artefato a"),
    @NamedQuery(name = "Artefato.findById", query = "SELECT a FROM Artefato a WHERE a.id = :id"),
    @NamedQuery(name = "Artefato.findByNome", query = "SELECT a FROM Artefato a WHERE a.nome = :nome"),
    @NamedQuery(name = "Artefato.findByDatacriacao", query = "SELECT a FROM Artefato a WHERE a.datacriacao = :datacriacao")})
public class Artefato implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nome")
    private String nome;
    @Lob
    @Column(name = "arquivo")
    private byte[] arquivo;
    @Column(name = "datacriacao")
    private String datacriacao;
    @JoinColumn(name = "idatividade", referencedColumnName = "id")
    @ManyToOne
    private Atividademembroequipe idatividade;

    public Artefato() {
    }

    public Artefato(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public String getDatacriacao() {
        return datacriacao;
    }

    public void setDatacriacao(String datacriacao) {
        this.datacriacao = datacriacao;
    }

    public Atividademembroequipe getIdatividade() {
        return idatividade;
    }

    public void setIdatividade(Atividademembroequipe idatividade) {
        this.idatividade = idatividade;
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
        if (!(object instanceof Artefato)) {
            return false;
        }
        Artefato other = (Artefato) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.crud.Artefato[ id=" + id + " ]";
    }
    
}
