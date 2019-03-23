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
@Table(name = "logusuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Logusuario.findAll", query = "SELECT l FROM Logusuario l"),
    @NamedQuery(name = "Logusuario.findById", query = "SELECT l FROM Logusuario l WHERE l.id = :id"),
    @NamedQuery(name = "Logusuario.findByDatainicio", query = "SELECT l FROM Logusuario l WHERE l.datainicio = :datainicio"),
    @NamedQuery(name = "Logusuario.findByDatafim", query = "SELECT l FROM Logusuario l WHERE l.datafim = :datafim"),
    @NamedQuery(name = "Logusuario.findByHorainicio", query = "SELECT l FROM Logusuario l WHERE l.horainicio = :horainicio"),
    @NamedQuery(name = "Logusuario.findByHorafim", query = "SELECT l FROM Logusuario l WHERE l.horafim = :horafim")})
public class Logusuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "datainicio")
    private String datainicio;
    @Column(name = "datafim")
    private String datafim;
    @Column(name = "horainicio")
    private String horainicio;
    @Column(name = "horafim")
    private String horafim;
    @JoinColumn(name = "atividade", referencedColumnName = "id")
    @ManyToOne
    private Atividademembroequipe atividade;
    @JoinColumn(name = "idprojeto", referencedColumnName = "id")
    @ManyToOne
    private Projeto idprojeto;
    @JoinColumn(name = "idusuario", referencedColumnName = "id")
    @ManyToOne
    private Usuario idusuario;

    public Logusuario() {
    }

    public Logusuario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDatainicio() {
        return datainicio;
    }

    public void setDatainicio(String datainicio) {
        this.datainicio = datainicio;
    }

    public String getDatafim() {
        return datafim;
    }

    public void setDatafim(String datafim) {
        this.datafim = datafim;
    }

    public String getHorainicio() {
        return horainicio;
    }

    public void setHorainicio(String horainicio) {
        this.horainicio = horainicio;
    }

    public String getHorafim() {
        return horafim;
    }

    public void setHorafim(String horafim) {
        this.horafim = horafim;
    }

    public Atividademembroequipe getAtividade() {
        return atividade;
    }

    public void setAtividade(Atividademembroequipe atividade) {
        this.atividade = atividade;
    }

    public Projeto getIdprojeto() {
        return idprojeto;
    }

    public void setIdprojeto(Projeto idprojeto) {
        this.idprojeto = idprojeto;
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
        if (!(object instanceof Logusuario)) {
            return false;
        }
        Logusuario other = (Logusuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.crud.Logusuario[ id=" + id + " ]";
    }
    
}
