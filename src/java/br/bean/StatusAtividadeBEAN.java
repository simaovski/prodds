/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean;

import data.crud.Artefato;
import data.crud.Atividademembroequipe;
import data.crud.Equipe;
import data.crud.Logusuario;
import data.crud.Projeto;
import data.crud.Statusatividade;
import data.crud.Usuario;
import data.dao.ArtefatoJpaController;
import data.dao.AtividadeJpaController;
import data.dao.AtividademembroequipeJpaController;
import data.dao.AtividadesfaseprocessoJpaController;
import data.dao.LogusuarioJpaController;
import data.dao.ProjetoJpaController;
import data.dao.StatusatividadeJpaController;
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
public class StatusAtividadeBEAN {

    private List<Atividademembroequipe> listaAtv;
    private Projeto p;
    
    
    public StatusAtividadeBEAN() {
    }
    
    public List<Atividademembroequipe> getListaAtv() {
        return listaAtv;
    }

    public void setListaAtv(List<Atividademembroequipe> listaAtv) {
        this.listaAtv = listaAtv;
    }

    public Projeto getP() {
        return p;
    }

    public void setP(Projeto p) {
        this.p = p;
    }
    
    
    
    @PostConstruct
    public void carregaTabela(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        int idProjeto = (int)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("projetoGerente");
        //System.out.println("Projeto: " + idProjeto);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        p = new ProjetoJpaController(emf).findProjeto(idProjeto);
        //Carregar table
        AtividademembroequipeJpaController daoAtv = new AtividademembroequipeJpaController(emf);
        List<Atividademembroequipe> auxAtv = daoAtv.findAtividademembroequipeEntities();
        listaAtv = new ArrayList<Atividademembroequipe>();
        
        for(Atividademembroequipe atv : auxAtv){
            int a = atv.getIdequipe().getIdprojeto().getId();
            int b = idProjeto;
            
            int c = p.getId();
            int d = atv.getIdequipe().getIdprojeto().getId();
           
            int e = atv.getIdequipe().getIdgerente().getId();
            int f = user.getId();
            //System.out.println("A: " + a + " B: " + b + " C: " + c + " D: " + d + " E: " + e + " F: " + f);
            if(a == b && c == d && e == f){
                listaAtv.add(atv);
            }
        }
    }
    
    public Usuario retornaUsuario(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return user;
    }
    
    public String retornaAtividade(Atividademembroequipe atv){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        if(atv.getIdfuncao() == 0){
            AtividadesfaseprocessoJpaController dao = new AtividadesfaseprocessoJpaController(emf);
            return dao.findAtividadesfaseprocesso(atv.getIdatividade()).getNomeatividade();
        }else{
            AtividadeJpaController dao = new AtividadeJpaController(emf);
            return dao.findAtividade(atv.getIdatividade()).getNomeatividade();
        }
    }
    
    public String retornaArtefato(Atividademembroequipe atv){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        ArtefatoJpaController daoArt = new ArtefatoJpaController(emf);
        List<Artefato> art = daoArt.findArtefatoEntities();
        
        for(Artefato a : art){
            int b = a.getIdatividade().getId();
            int c = atv.getId();
            
            if(b == c){
                return "Sim";
            }
        }
        return "Não";
    }
    
    public String verificaStatus(Atividademembroequipe atv){
        if(atv.getStatus() == false){
            return "Em desenvolvimento";
        }
        return "Concluído";
    }
    
    public void mudarStatus(Atividademembroequipe atv){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        AtividademembroequipeJpaController daoAtv = new AtividademembroequipeJpaController(emf);
        
        if(atv.getStatus() == false){
            atv.setStatus(true);
        }else{
            atv.setStatus(false);
        }
        
        try {
            daoAtv.edit(atv);
        } catch (Exception e) {
            System.out.println("Erro classe statusAtividadeBEAN, método mudarStatus: " + e.toString());
        }
    }
}
