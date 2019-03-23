/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean;

import br.util.Util;
import data.crud.Atividademembroequipe;
import data.crud.Logusuario;
import data.crud.Usuario;
import data.dao.AtividadeJpaController;
import data.dao.AtividademembroequipeJpaController;
import data.dao.AtividadesfaseprocessoJpaController;
import data.dao.LogusuarioJpaController;
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
public class LogBEAN {

    private List<Logusuario> listaLog;
    private String pesquisa;
    
    public LogBEAN() {
    }
    
    public boolean permissao(){
        Util u = new Util();
        return u.permissaoMem();
    }
    
    public String encerrarSecao(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        return "../index.xhtml?faces-redirect=true";
    }
    
    public Usuario retornaUsuario(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return user;
    }
    
    public List<Logusuario> getListaLog() {
        return listaLog;
    }

    public String getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }
    
    
    
    @PostConstruct
    public void carregaTabela(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        
        //Carregar log's
        LogusuarioJpaController daoLog = new LogusuarioJpaController(emf);
        List<Logusuario> auxLog = daoLog.findLogusuarioEntities();
        listaLog = new ArrayList<Logusuario>();
        
        for(Logusuario l : auxLog){
            int a = user.getId();
            int b = l.getIdusuario().getId();
            if(a == b){
                listaLog.add(l);
            }
        }
    }
    
    public String retornaAtividadePorId(int id){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        AtividademembroequipeJpaController daoAtv = new AtividademembroequipeJpaController(emf);
        Atividademembroequipe atv = daoAtv.findAtividademembroequipe(id);
        
        if(atv.getIdfuncao() == 0){
            AtividadesfaseprocessoJpaController dao = new AtividadesfaseprocessoJpaController(emf);
            return dao.findAtividadesfaseprocesso(atv.getIdatividade()).getNomeatividade();
        }else{
            AtividadeJpaController dao = new AtividadeJpaController(emf);
            return dao.findAtividade(atv.getIdatividade()).getNomeatividade();
        }
    }
    
    public void filtrar(){
        List<Logusuario> listaAux = new ArrayList<Logusuario>();
        //Carregar log's
        for(Logusuario l : listaLog){
            String nome = l.getIdprojeto().getNomeprojeto();
            System.out.println();
            if(nome.toUpperCase().contains(pesquisa.toUpperCase())){
                listaAux.add(l);
            }
        }
        listaLog = listaAux;
    }
}
