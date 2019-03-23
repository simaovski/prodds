/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean;

import br.util.Util;
import data.crud.Atividademembroequipe;
import data.crud.Equipe;
import data.crud.Membrosequipe;
import data.crud.Notificacaogerente;
import data.crud.Usuario;
import data.dao.AtividademembroequipeJpaController;
import data.dao.EquipeJpaController;
import data.dao.MembrosequipeJpaController;
import data.dao.NotificacaogerenteJpaController;
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
public class SolicitarEquipeBEAN {

    private List<Equipe> listaEqp;
    
    public SolicitarEquipeBEAN() {
    }

    public List<Equipe> getListaEqp() {
        return listaEqp;
    }

    public void setListaEqp(List<Equipe> listaEqp) {
        this.listaEqp = listaEqp;
    }
    
    public boolean permissao(){
        Util u = new Util();
        return u.permissaoMem();
    }
    
    @PostConstruct
    public void carregaTabela(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        
        EquipeJpaController daoEqp = new EquipeJpaController(emf);
        List<Equipe> aux = daoEqp.findEquipeEntities();
        MembrosequipeJpaController daoMem = new MembrosequipeJpaController(emf);
        NotificacaogerenteJpaController daoNot = new NotificacaogerenteJpaController(emf);
        
        listaEqp = new ArrayList<Equipe>();
        for(Equipe e : aux){
            int a = e.getId();
            int b = user.getId();
            if(e.getIdgerente().getId() != b){//Verifica se ele ja eh gerente
                if(daoMem.retornaMembro(user.getId(), a) == false){//verifica se ja é membro de uma equipe
                    if(daoNot.verificaNotificacao(e, b) == false){//verifica se ele ja se cadastrou nesse projeto
                        listaEqp.add(e);
                    }
                }
            }
        }
        
        
        
    }
    
    public String encerrarSecao(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        return "../index.xhtml?faces-redirect=true";
    }
    
    public Usuario retornaUsuario(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return user;
    }
    
    public void salvar(Equipe eqp){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        NotificacaogerenteJpaController daoNot = new NotificacaogerenteJpaController(emf);
        Notificacaogerente n = new Notificacaogerente();
        
        n.setIdusuario(user);
        n.setIdgerente(eqp.getIdgerente());
        n.setIdprojeto(eqp.getIdprojeto());
        n.setIdequipe(eqp);
        
        try {
            daoNot.create(n);
        } catch (Exception e) {
            System.out.println("Erro, solicitarEquipeBEAN, método salvar: " + e.toString());
        }
    }
    
    public String recarregar(){
        System.out.println("Entrei");
        return "CADs/solicitarEquipe.xhtml";
    }
}
