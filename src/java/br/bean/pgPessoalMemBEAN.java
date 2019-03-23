/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean;

import br.util.Util;
import data.crud.Atividademembroequipe;
import data.crud.Atividadesequipe;
import data.crud.Atividadesfaseprocesso;
import data.crud.Equipe;
import data.crud.Gerente;
import data.crud.Gerenteprocesso;
import data.crud.Membrosequipe;
import data.crud.Notificacao;
import data.crud.Notificacaogerente;
import data.crud.Projeto;
import data.crud.Usuario;
import data.dao.AtividadeJpaController;
import data.dao.AtividademembroequipeJpaController;
import data.dao.AtividadesequipeJpaController;
import data.dao.AtividadesfaseprocessoJpaController;
import data.dao.EquipeJpaController;
import data.dao.GerenteJpaController;
import data.dao.GerenteprocessoJpaController;
import data.dao.MembrosequipeJpaController;
import data.dao.NotificacaoJpaController;
import data.dao.NotificacaogerenteJpaController;
import data.dao.ProjetoJpaController;
import java.util.ArrayList;
import java.util.Collection;
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
public class pgPessoalMemBEAN {

    /**
     * Creates a new instance of pgPessoalMemBEAN
     */
    public pgPessoalMemBEAN() {
    }
    
    private List<Membrosequipe> listaEqp;
    private List<Gerente> listaGer;
    private List<Object> listaObj;
    private List<Atividademembroequipe> listaAtividades;
    private String notificacoes;
    private String pendencias;
    private List<Notificacaogerente> listaNotGer;
    private Membrosequipe mem;
    private List<Gerenteprocesso> listaGerPro;
    
    public List<Membrosequipe> getListaEqp() {
        return listaEqp;
    }

    public void setListaEqp(List<Membrosequipe> listaEqp) {
        this.listaEqp = listaEqp;
    }

    public List<Gerente> getListaGer() {
        return listaGer;
    }

    public void setListaGer(List<Gerente> listaGer) {
        this.listaGer = listaGer;
    }

    public List<Object> getListaObj() {
        return listaObj;
    }

    public void setListaObj(List<Object> listaObj) {
        this.listaObj = listaObj;
    }

    public List<Atividademembroequipe> getListaAtividades() {
        return listaAtividades;
    }

    public void setListaAtividades(List<Atividademembroequipe> listaAtividades) {
        this.listaAtividades = listaAtividades;
    }

    public String getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(String notificacoes) {
        this.notificacoes = notificacoes;
    }

    public List<Notificacaogerente> getListaNotGer() {
        return listaNotGer;
    }

    public void setListaNotGer(List<Notificacaogerente> listaNotGer) {
        this.listaNotGer = listaNotGer;
    }

    public Membrosequipe getMem() {
        return mem;
    }

    public void setMem(Membrosequipe mem) {
        this.mem = mem;
    }

    public String getPendencias() {
        return pendencias;
    }

    public void setPendencias(String pendencias) {
        this.pendencias = pendencias;
    }

    public List<Gerenteprocesso> getListaGerPro() {
        return listaGerPro;
    }

    public void setListaGerPro(List<Gerenteprocesso> listaGerPro) {
        this.listaGerPro = listaGerPro;
    }
    
    
    
    @PostConstruct
    public void listarSolicitacaoGerente(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
                
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        NotificacaogerenteJpaController daoNot = new NotificacaogerenteJpaController(emf);
        List<Notificacaogerente> aux = daoNot.findNotificacaogerenteEntities();
        listaNotGer = new ArrayList<Notificacaogerente>();
        for(Notificacaogerente n : aux){
            int a = n.getIdgerente().getId();
            int b = user.getId();
            if(a == b){
                listaNotGer.add(n);
            }
        }
        
        MembrosequipeJpaController daoEqp = new MembrosequipeJpaController(emf);
        List<Membrosequipe> auxEqp = daoEqp.findMembrosequipeEntities();
        listaEqp = new ArrayList<Membrosequipe>();
        listaObj = new ArrayList<Object>();
        for(Membrosequipe e : auxEqp){
            int a = e.getIdmembro().getId();
            int b = user.getId();
            if(a == b){
                listaEqp.add(e);
                listaObj.add(e);
            }
        }
        
        GerenteJpaController daoGer = new GerenteJpaController(emf);
        List<Gerente> auxGer = daoGer.findGerenteEntities();
        listaGer = new ArrayList<Gerente>();
        
        for(Gerente g : auxGer){
            int a = g.getIdusuariogerente().getId();
            int b = user.getId();
            
            if(a == b){
                listaGer.add(g);
                listaObj.add(g);
            }
        }
        
        NotificacaogerenteJpaController daoNotGer = new NotificacaogerenteJpaController(emf);
        List<Notificacaogerente> listaNotGer = daoNotGer.findNotificacaogerenteEntities();
        int nots = 0;
        for(Notificacaogerente n : listaNotGer){
            int a = n.getIdgerente().getId();
            int b = user.getId();
            
            if(a == b){
                nots++;
            }
        }
        notificacoes = nots + "";
        
        int pend = 0;
        listaGerPro = new ArrayList<Gerenteprocesso>();
        GerenteprocessoJpaController daoGerPro = new GerenteprocessoJpaController(emf);
        List<Gerenteprocesso> aux1 = daoGerPro.findGerenteprocessoEntities();
        for(Gerenteprocesso g : aux1){
            int a = g.getIdgerente().getId();
            int b = user.getId();
            if(a == b){
                pend++;
                listaGerPro.add(g);
            }
        }
        pendencias = "" + pend;
    }
    
    
    public boolean verificaObj(Object o){
        Gerente g = new Gerente();
        if(o instanceof Gerente){
            System.out.println("Sou membro de equipe");
        }else{
            System.out.println("Su gerente");
        }
        return true;
    }
    
    public String dialogEquipe(Membrosequipe e){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        AtividadesequipeJpaController daoAtvEqp = new AtividadesequipeJpaController(emf);
        List<Atividadesequipe> listaAtv = daoAtvEqp.findAtividadesequipeEntities();
        String atividades = "";
        for(Atividadesequipe atv : listaAtv){
            int a = atv.getIdequipe().getId();
            int b = e.getIdequipe().getId();
            if(a == b){
                atividades += atv.getIdatividade().getNomeatividade() + "; ";
            }
        }
        if(atividades.isEmpty()){
            atividades = "Não foi cadastrada nenhuma atividade para esta equipe...";
        }else{
            atividades = atividades.substring(0, atividades.length()-2);
        }
        return atividades;
    }
    
    public String pgPessoalGer(int idProjeto){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("projetoGerente", idProjeto);
        //int id = (int)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("projetoGerente");
        //System.out.println("Id da seção: " + id);
        return "pgPessoalGer.xhtml?faces-redirect=true";
    }
    
    public boolean permissao(){
        Util u = new Util();
        return u.permissaoMem();
    }
    
    public String solicitarGerencia(){
        return "CADs/solicitarGerencia.xhtml?faces-redirect=true";
    }
    
    public String verLog(){
        return "CADs/log.xhtml?faces-redirect=true";
    }
    
    public String solicitarEquipe(){
        return "CADs/solicitarEquipe.xhtml?faces-redirect=true";
    }
    
    public String encerrarSecao(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("projetoGerente");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("log");
        return "index.xhtml?faces-redirect=true";
    }
    
    public Usuario retornaUsuario(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return user;
    }
    
    public String atividades(Membrosequipe membro){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        AtividademembroequipeJpaController daoAtv = new AtividademembroequipeJpaController(emf);
        List<Atividademembroequipe> lista = daoAtv.findAtividademembroequipeEntities();
        listaAtividades = new ArrayList<Atividademembroequipe>();
        String atividades = "Atividades: ";
        for(Atividademembroequipe atv : lista){
            int a = membro.getIdequipe().getIdprojeto().getId();
            int b = atv.getIdequipe().getIdprojeto().getId();
            
            int c = atv.getIdmembro().getId();
            int d = user.getId();
            
            int e = atv.getIdequipe().getId();
            int f = membro.getIdequipe().getId();
            if(a == b && c == d && e == f){
                if(atv.getIdfuncao() == 0){
                    AtividadesfaseprocessoJpaController dao = new AtividadesfaseprocessoJpaController(emf);
                    atividades += dao.findAtividadesfaseprocesso(atv.getIdatividade()).getNomeatividade() + "; ";
                }else{
                    AtividadeJpaController dao = new AtividadeJpaController(emf);
                    atividades += dao.findAtividade(atv.getIdatividade()).getNomeatividade() + "; ";
                }
            }
        }
        return atividades.substring(0, atividades.length()-2);
    }
    
    public String registroLog(Membrosequipe membro){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("projetoGerente", membro.getIdequipe().getIdprojeto().getId());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("log", membro.getIdequipe());
        return "CADs/registroLog.xhtml?faces-redirect=true";
    }
    
    public void notificacaoSelecionada(Notificacaogerente not){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("NotificacaoGerente", not);
    }
    
    public String salvarMembro(){
        Notificacaogerente not = (Notificacaogerente)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("NotificacaoGerente");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        MembrosequipeJpaController daoMem = new MembrosequipeJpaController(emf);
        Membrosequipe m = new Membrosequipe();
        
        m.setIdequipe(not.getIdequipe());
        m.setIdgerente(not.getIdgerente());
        m.setIdmembro(not.getIdusuario());
        
        try {
            daoMem.create(m);
            NotificacaogerenteJpaController daoNot = new NotificacaogerenteJpaController(emf);
            daoNot.destroy(not.getId());
        } catch (Exception e) {
            System.out.println("Erro pgPessoalBEAN, método salvarMembro: " + e.toString());
        }
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("NotificacaoGerente");
        return "pgPessoalMem.xhtml?faces-redirect=true";
    }
    
     public String naoSalvarMembro(){
        Notificacaogerente not = (Notificacaogerente)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("NotificacaoGerente");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        try {
            NotificacaogerenteJpaController daoNot = new NotificacaogerenteJpaController(emf);
            daoNot.destroy(not.getId());
        } catch (Exception e) {
            System.out.println("Erro pgPessoalBEAN, método salvarMembro: " + e.toString());
        }
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("NotificacaoGerente");
        return "pgPessoalMem.xhtml?faces-redirect=true";
     }
     
    public Collection<Gerenteprocesso> listaNotPro(){
        System.out.println("Chegeui");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        System.out.println("Cheguei");
        
        GerenteprocessoJpaController daoGer = new GerenteprocessoJpaController(emf);
        List<Gerenteprocesso> aux = daoGer.findGerenteprocessoEntities();
        for(Gerenteprocesso g : aux)
            System.out.println(g.getIdgerente().getNomeusuario());
        return aux;
    }
    
    public String redirecionarPendencia(Gerenteprocesso proc){
        pgPessoalGer(proc.getIdprojeto().getId());
        return "Relatorios/visaoGeralGer.xhtml?faces-redirect=true";
    }
    
}
