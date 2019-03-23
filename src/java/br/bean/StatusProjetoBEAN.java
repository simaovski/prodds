/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean;

import br.util.ObjectAux;
import br.util.Util;
import data.crud.Statusprojeto;
import data.crud.Usuario;
import data.dao.ProjetoJpaController;
import data.dao.StatusprojetoJpaController;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.eclipse.persistence.internal.libraries.antlr.runtime.misc.Stats;

/**
 *
 * @author padrao
 */
@ManagedBean
@RequestScoped
public class StatusProjetoBEAN {

    List<Statusprojeto> listaStatus;
    
    public StatusProjetoBEAN() {
    }

    public List<Statusprojeto> getListaStatus() {
        return listaStatus;
    }

    public void setListaStatus(List<Statusprojeto> listaStatus) {
        this.listaStatus = listaStatus;
    }
    
    @PostConstruct
    public void carregaTabela(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        StatusprojetoJpaController daoStatus = new StatusprojetoJpaController(emf);
        List<Statusprojeto> listaAux = daoStatus.findStatusprojetoEntities();
        listaStatus = new ArrayList<Statusprojeto>();
        
        for(Statusprojeto s : listaAux){
            int a = s.getIdadm().getId();
            int b = user.getId();
            
            if(a == b){
                listaStatus.add(s);
            }
        }
    }
    
    public String projeto(int id){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        ProjetoJpaController daoProj = new ProjetoJpaController(emf);
        return daoProj.findProjeto(id).getNomeprojeto();
    }
    
    
    public String mudarStatus(Statusprojeto status){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        StatusprojetoJpaController daoStatus = new StatusprojetoJpaController(emf);
        
        if(status.getStatus() == true){
            Date d = new Date();
            status.setDatafinal(d.getDate() + "/" + (d.getMonth() + 1) + "/" + (d.getYear()+1900));
            System.out.println("Entrei: " + d.getDate() + "/" + (d.getMonth() + 1) + "/" + (d.getYear()+1900));
            status.setStatus(false);
        }else{
            status.setStatus(true);
        }
        try {
            daoStatus.edit(status);
        } catch (Exception e) {
            System.out.println("Erro StatusProjetoBEAN, método mudarStatus: " + e.toString());
        }
        
        return "/CADs/cadStatusProjeto.xhtml?faces-redirect=true";
    }
    
    public String retornarStatus(boolean b){
        if(b == true){
            return "Concluído";
        }
        return "Em Desenvolvimento";
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
}
