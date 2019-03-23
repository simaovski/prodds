/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean;

import br.util.Util;
import data.crud.Projeto;
import data.crud.Statusprojeto;
import data.crud.Usuario;
import data.dao.ProjetoJpaController;
import data.dao.StatusprojetoJpaController;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author padrao
 */
@ManagedBean
@RequestScoped
public class PgAdmBEAN {

    private List<Projeto> listaProjeto;
    
    
    public PgAdmBEAN() {
    }

    public List<Projeto> getListaProjeto() {
        return listaProjeto;
    }

    public void setListaProjeto(List<Projeto> listaProjeto) {
        this.listaProjeto = listaProjeto;
    }
    
    public boolean permissao(){
        Util u = new Util();
        return u.permissaoAdm();
    }
    
    public String encerrarSecao(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("projetoAdm");
        return "index.xhtml?faces-redirect=true";
    }
    
    public Usuario retornaUsuario(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return user;
    }
    
    @PostConstruct
    public void carregaTabela(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        ProjetoJpaController daoProj = new ProjetoJpaController(emf);
        List<Projeto> aux = daoProj.findProjetoEntities();
        listaProjeto = new ArrayList<Projeto>();
        
        for(Projeto p : aux){
            int a = p.getIdadm().getId();
            int b = user.getId();
            
            if(a == b){
                listaProjeto.add(p);
            }
        }
    }
    
    public String estado(Projeto p){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        StatusprojetoJpaController daoStatus = new StatusprojetoJpaController(emf);
        List<Statusprojeto> status = daoStatus.findStatusprojetoEntities();
        
        for(Statusprojeto s : status){
            int a = s.getIdprojeto();
            int b = p.getId();
            
            if(a == b){
                if(s.getStatus() == false)
                    return "  (Desenvolvimento)";
                return " (Concluído)";
            }
        }
        return " - Não encontrado";
        
    }
    
    public String notificacao(){
        return "CADs/cadGerente.xhtml?faces-redirect=true";
    }
    
    public String proj(){
        return "CADs/cadProjeto.xhtml?faces-redirect=true";
    }
    
    public String status(){
        return "CADs/cadStatusProjeto.xhtml?faces-redirect=true";
    }
    
    public String processo(){
        return "CADs/cadProcesso.xhtml?faces-redirect=true";
    }
    
    public String fase(){
        return "ALTs/gerFase.xhtml?faces-redirect=true";
    }
    
    public String relatorio(Projeto p){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("projetoAdm", p);
        return "Relatorios/relatorioAdm.xhtml?faces-redirect=true";
    }
    
}
