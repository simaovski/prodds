/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean;

import data.dao.GerenteJpaController;
import data.dao.NotificacaoJpaController;
import data.dao.ProjetoJpaController;
import data.dao.StatusprojetoJpaController;
import data.dao.UsuarioJpaController;
import data.crud.Gerente;
import data.crud.Notificacao;
import data.crud.Projeto;
import data.crud.Statusprojeto;
import data.crud.Usuario;
import br.util.Util;
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
public class SolicitarGerenciaBEAN {

    private List<Projeto> projetos;
    private String info = "";

    public SolicitarGerenciaBEAN() {
    }

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    
    @PostConstruct
    public void carregaTabela() {
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
                
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        ProjetoJpaController daoProj = new ProjetoJpaController(emf);
        projetos = new ArrayList<Projeto>();
        
        StatusprojetoJpaController daoStatusProj = new StatusprojetoJpaController(emf);
        List <Statusprojeto> aux = daoStatusProj.findStatusprojetoEntities();
        for(int i = 0; i < aux.size(); i++){
            Statusprojeto s = aux.get(i);
            if(s.getStatus() == false){
                projetos.add(daoProj.findProjeto(s.getIdprojeto()));
            }
        }
        
        //Verificar projetos que usuário já pe gerente
        GerenteJpaController daoGer = new GerenteJpaController(emf);
        List<Gerente> auxGer = daoGer.findGerenteEntities();
        for(int i = 0; i < auxGer.size(); i++){
            int a = auxGer.get(i).getIdusuariogerente().getId();
            int b = user.getId();
            //System.out.println("Usuario: "+ a + "Gerente: " + b);
            if(a == b){
                projetos.remove(daoProj.findProjeto(auxGer.get(i).getIdprojeto()));
            }
        }
        
        //Verificar projetos aos quais já enviou solicitação
        NotificacaoJpaController daoNot = new NotificacaoJpaController(emf);
        List<Notificacao> auxNot = daoNot.findNotificacaoEntities();
        for(int i = 0; i < auxNot.size(); i++){
            int a = auxNot.get(i).getIdusuario().getId();
            int b = user.getId();
            if(a == b){
                projetos.remove(daoProj.findProjeto(auxNot.get(i).getIdprojeto()));
                //projetos.remove(auxNot.get(i).getIdprojeto());
            }
        }
    }
    
    public List listaProcessos(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        ProjetoJpaController daoProj = new ProjetoJpaController(emf);
        return daoProj.findProjetoEntities();
    }
    
    public void salvarSolicitacao(Projeto p){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        NotificacaoJpaController daoNot = new NotificacaoJpaController(emf);
        Notificacao n = new Notificacao();
        //System.out.println("PRojeto: " + p.getNomeprojeto());
        n.setIdprojeto(p.getId());
        n.setIdusuario(user);
        n.setSolicitagerencia(Boolean.TRUE);
        n.setVisualizado(Boolean.FALSE);
        
        try {
            daoNot.create(n);
        } catch (Exception e) {
            System.out.println("Erro bean SolicitarGerencia: " + e.toString());
            info = "Houve um erro ao processar a solicitação";
        }
        info = "Foi enviada uma solicitação de gerência ao Administrador do projeto";
    }
    
    public void selecionarProjeto(Projeto proc){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        GerenteJpaController daoGerente = new GerenteJpaController(emf);
        NotificacaoJpaController daoNot = new NotificacaoJpaController(emf);
        List<Notificacao> listNot = daoNot.findNotificacaoEntities();
        List<Gerente> listGer = daoGerente.findGerenteEntities();
        ProjetoJpaController daoProj = new ProjetoJpaController(emf);
    
        if(listNot.size() > 0){
            for(int i = 0; i < listNot.size(); i++){
                //verifica se já o usuário ja enviou uma solicitação na tabela NOTIFICAÇÃO
                int a = listNot.get(i).getIdusuario().getId();
                int b = user.getId();

                //verifica se é o mesmo projeto
                int c = daoProj.findProjeto(listNot.get(i).getIdprojeto()).getId();
                int d = proc.getId();

                if(a == b && c == d){
                    //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Você já solicitou gerência para este projeto! Aguarde a definição do Administrador");
                    //RequestContext.getCurrentInstance().showMessageInDialog(message);
                    info = "Você já solicitou gerência para este projeto! Aguarde a definição do Administrador";
                    break;
                   
                }
            }
        }
        if(info.isEmpty()){
            for(int j = 0; j < listGer.size(); j++){
                int a = listGer.get(j).getIdusuariogerente().getId();
                int b= user.getId();
                
                int c = daoProj.findProjeto(listGer.get(j).getIdprojeto()).getId();
                int d = proc.getId();
                if(a == b && c == d){
                    info = "Você já é gerente deste projeto";
                    break;
                }
            }
        }
        if(info.isEmpty()){
            Notificacao n = new Notificacao();
            //n.setIdprojeto(proc);
            n.setIdusuario(user);
            n.setSolicitagerencia(Boolean.TRUE);
            n.setVisualizado(Boolean.FALSE);

            daoNot.create(n); 

            //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Foi enviada uma solicitação de gerência do projeto: " + proc.getNomeprojeto() + " ao Administrador: " + proc.getIdadm().getNomeusuario());
            //RequestContext.getCurrentInstance().showMessageInDialog(message);
            info = "Foi enviada uma solicitação de gerência ao Administrador do projeto";
        }
    }
   
    public String verificaGerencia(Projeto projeto){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        GerenteJpaController daoGerente = new GerenteJpaController(emf);
        List<Gerente> aux = daoGerente.findGerenteEntities();
        ProjetoJpaController daoProj = new ProjetoJpaController(emf);
        
        //Verifica se já é gerente
        for(int i = 0; i < aux.size(); i++){
            int a = aux.get(i).getIdusuariogerente().getId();
            int b = user.getId();
            int c = daoProj.findProjeto(aux.get(i).getIdprojeto()).getId();
           
            int d = projeto.getId();
            
            if(a == b && c == d){
                return "Você já é gerente desse projeto";
            }
        }
        
        //Verifica se ja solicitou gerencia
        NotificacaoJpaController daoNot = new NotificacaoJpaController(emf);
        List<Notificacao> auxNot = daoNot.findNotificacaoEntities();
        
        for(int i = 0; i < auxNot.size(); i++){
            int a = auxNot.get(i).getIdusuario().getId();
            int b = user.getId();
            int c = daoProj.findProjeto(auxNot.get(i).getIdprojeto()).getId();
            //int c = auxNot.get(i).getIdprojeto().getId();
            int d = projeto.getId();
            
            if(a == b && c == d){
                return "Solicitação enviada";
            }
        }
        
        return "Solicitar gerência";
    }
    
    public String recarregar(){
        return "solicitarGerencia.xhtml?faces-redirect=true";
    }
    
    public String nomeAdm(Projeto proj){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        UsuarioJpaController daoUser = new UsuarioJpaController(emf);
        
       return daoUser.getNomeUsuarioAdm(proj.getIdadm());
    }
    
    public String emailAdm(Projeto proj){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        UsuarioJpaController daoUser = new UsuarioJpaController(emf);
        
       return daoUser.getEmailUsuarioAdm(proj.getIdadm());
    }
    
    public boolean permissao(){
        Util u = new Util();
        return u.permissaoMem();
        
    }
    
    public String direcionaUrl(){
        Util u = new Util();
        return u.direcionaUrl();
    }
    
    public String encerrarSecao(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        return "../index.xhtml?faces-redirect=true";
    }
    
    public Usuario retornaUsuario(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return user;
    }
}
