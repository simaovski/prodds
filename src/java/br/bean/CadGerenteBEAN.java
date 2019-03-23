/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean;

import br.util.Util;
import data.crud.Gerente;
import data.crud.Notificacao;
import data.crud.Usuario;
import data.dao.GerenteJpaController;
import data.dao.NotificacaoJpaController;
import data.dao.ProjetoJpaController;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.primefaces.context.RequestContext;

/**
 *
 * @author padrao
 */
@ManagedBean
@RequestScoped
public class CadGerenteBEAN {

    private List<Notificacao> notificacoes;
    
    public CadGerenteBEAN() {
    }

    public List<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(List<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }
    
    @PostConstruct
    public void carregaTabela() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        NotificacaoJpaController daoNot = new NotificacaoJpaController(emf);
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        
        List<Notificacao> listaAux = daoNot.findNotificacaoEntities();
        notificacoes = new ArrayList<Notificacao>();
        for(int i = 0; i < listaAux.size(); i++){
            ProjetoJpaController daoProj = new ProjetoJpaController(emf);
            int a = daoProj.findProjeto(listaAux.get(i).getIdprojeto()).getIdadm().getId();
            int b = user.getId();
            
            if(a == b){
                notificacoes.add(listaAux.get(i));
            }
        }
    }
    
    public String autenticarGerente(Notificacao ntf, boolean visualizado){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        NotificacaoJpaController daoNot = new NotificacaoJpaController(emf);
        Notificacao n = new Notificacao();
        ProjetoJpaController daoProj = new ProjetoJpaController(emf);
        if(visualizado == true){
            Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            GerenteJpaController gerenteDao = new GerenteJpaController(emf);
            Gerente g = new Gerente();
            
            g.setIdprojeto(ntf.getIdprojeto());
            g.setProjeto(daoProj.findProjeto(ntf.getIdprojeto()));
            g.setIdusuarioadm(user);
            g.setIdusuariogerente(ntf.getIdusuario());
            
            gerenteDao.create(g);     
            
        }
        try {
            daoNot.destroy(ntf.getId());
        } catch (Exception e) {
            System.out.println("Erro: " + e.toString());
        }
        return "cadGerente.xhtml?faces-redirect=true";
    }
    
    public boolean permissaoAdm(){
        Util u = new Util();
        return u.permissaoAdm();
        
    }
    
    public String nomeProjeto(int id){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        ProjetoJpaController daoProj = new ProjetoJpaController(emf);
        return daoProj.findProjeto(id).getNomeprojeto();
    }
    
    public String direcionaUrl(){
        Util u = new Util();
        return u.direcionaUrl();
    }
    
    public String encerrarSecao(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        return "index.xhtml?faces-redirect=true";
    }
    
    public Usuario retornaUsuario(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return user;
    }
    
}
