/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean;

import br.util.Util;
import data.crud.Atividade;
import data.crud.Atividademembroequipe;
import data.crud.Atividadesantecessoras;
import data.crud.Atividadesequipe;
import data.crud.Atividadesfaseprocesso;
import data.crud.Equipe;
import data.crud.Faseprojeto;
import data.crud.Fasesprocesso;
import data.crud.Gerente;
import data.crud.Gerenteprocesso;
import data.crud.Membrosequipe;
import data.crud.Projeto;
import data.crud.Usuario;
import data.dao.AtividadeJpaController;
import data.dao.AtividademembroequipeJpaController;
import data.dao.AtividadesfaseprocessoJpaController;
import data.dao.EquipeJpaController;
import data.dao.FaseprojetoJpaController;
import data.dao.FasesprocessoJpaController;
import data.dao.GerenteprocessoJpaController;
import data.dao.ProjetoJpaController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
public class visaoGeralGerBEAN {

    private Projeto p;
    private int pend;
    private Collection<Fasesprocesso> listaFases;
    private int fase;
    private Fasesprocesso faseSelecionada;
    private Collection<Atividadesfaseprocesso> listaAtividades;
    private String faseAtual;
    
    public visaoGeralGerBEAN() {
    }
    
    public Projeto getP() {
        return p;
    }

    public void setP(Projeto p) {
        this.p = p;
    }

    public int getPend() {
        return pend;
    }

    public void setPend(int pend) {
        this.pend = pend;
    }

    public Collection<Fasesprocesso> getListaFases() {
        return listaFases;
    }

    public void setListaFases(Collection<Fasesprocesso> listaFases) {
        this.listaFases = listaFases;
    }

    public int getFase() {
        return fase;
    }

    public void setFase(int fase) {
        this.fase = fase;
    }

    public Fasesprocesso getFaseSelecionada() {
        return faseSelecionada;
    }

    public void setFaseSelecionada(Fasesprocesso faseSelecionada) {
        this.faseSelecionada = faseSelecionada;
    }

    public Collection<Atividadesfaseprocesso> getListaAtividades() {
        return listaAtividades;
    }

    public void setListaAtividades(Collection<Atividadesfaseprocesso> listaAtividades) {
        this.listaAtividades = listaAtividades;
    }

    public String getFaseAtual() {
        return faseAtual;
    }

    public void setFaseAtual(String faseAtual) {
        this.faseAtual = faseAtual;
    }
    
    

    public boolean permissao(){
        Util u = new Util();
        return u.permissaoGer();
    }
    
    
    
    public String encerrarSecao(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("projetoGerente");
        return "index.xhtml?faces-redirect=true";
    }
    
    public Usuario retornaUsuario(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return user;
    }
    
    @PostConstruct
    public void carregaTabela(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        int idProjeto = (int)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("projetoGerente");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        ProjetoJpaController daoProj = new ProjetoJpaController(emf);
        p = daoProj.findProjeto(idProjeto);
        
        pend = 0;
        GerenteprocessoJpaController daoGerPro = new GerenteprocessoJpaController(emf);
        List<Gerenteprocesso> aux1 = daoGerPro.findGerenteprocessoEntities();
        for(Gerenteprocesso g : aux1){
            int a = g.getIdgerente().getId();
            int b = user.getId();
            
            int c = p.getId();
            int d = g.getIdprojeto().getId();
            if(a == b && c == d){
                pend++;
            }
        }
        
        FaseprojetoJpaController daoFase = new FaseprojetoJpaController(emf);
        List<Faseprojeto> auxFases = daoFase.findFaseprojetoEntities();
        for(Faseprojeto f : auxFases){
            int a = f.getIdprojeto().getId();
            int b = p.getId();
            
            int c = f.getIdusuariogerenteprocesso().getId();
            int d = user.getId();
            if(a == b && c == d)
                faseAtual = f.getIdfase().getNomefase();
        }
        
        listaFases = p.getIdprocesso().getFasesprocessoCollection();
    }
    
    public Collection<Gerente> gerentes(){
        return p.getGerenteCollection();
    }
    
    public Collection<Equipe> equipe(){
        return p.getEquipeCollection();
    }
    
    public String Atividadesequipe(Equipe e){
        Collection<Atividadesequipe> ab = e.getAtividadesequipeCollection();
        String atividades = "  ";
        for(Atividadesequipe a : ab){
            atividades += a.getIdatividade().getNomeatividade() + ", ";
        }
        return atividades.substring(0, atividades.length()-2);
    }
    
    public Equipe retornaEquipe(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        for(Equipe e : p.getEquipeCollection()){
            int a = e.getIdgerente().getId();
            int b = user.getId();
            
            int c = p.getId();
            int d = e.getIdprojeto().getId();
            if(a == b && c == d){
                return e;
            }
        }
        return null;
    }
    
    public Collection<Membrosequipe> retornaNomeColaborador(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        Collection<Equipe> colEqp = p.getEquipeCollection();
        Collection<Membrosequipe> listaMem = new ArrayList<Membrosequipe>();
        for(Equipe eqp : colEqp){
            int a = eqp.getIdprojeto().getId();
            int b = p.getId();
            
            int c = eqp.getIdgerente().getId();
            int d = user.getId();
            
            if(a == b && c == d){
                listaMem = eqp.getMembrosequipeCollection();
            }
        }
        return listaMem;
    }
    
    public List<Atividademembroequipe> retornaAtividade(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        AtividademembroequipeJpaController daoAtv = new AtividademembroequipeJpaController(emf);
        List<Atividademembroequipe> lista = daoAtv.findAtividademembroequipeEntities();
        List<Atividademembroequipe> listaAtv = new ArrayList<Atividademembroequipe>();
        for(Atividademembroequipe atv : lista){
            int a = atv.getIdequipe().getIdprojeto().getId();
            int b = p.getId();
            
            int c = atv.getIdusuariocadatv().getId();
            int d = user.getId();
            
            if(atv.getIdfuncao() == 0){
                if(a == b){
                    listaAtv.add(atv);
                }
            }else{
                if(a == b && c == d){
                    listaAtv.add(atv);
                }
            }
        }
        return listaAtv;
    }
    
    public String retornaAtividadeMembro(Membrosequipe mem){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        AtividademembroequipeJpaController daoAtv = new AtividademembroequipeJpaController(emf);
        Equipe e = mem.getIdequipe();
        Collection<Atividademembroequipe> lista =  e.getAtividademembroequipeCollection();
        String atividades = "  ";
        for(Atividademembroequipe atv : lista){
            int a = atv.getIdmembro().getId();
            int b = mem.getIdmembro().getId();
            if(a == b){
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
    
    public List<Atividade> retornaAtividadesProjeto(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        AtividadeJpaController atvDao = new AtividadeJpaController(emf);
        List<Atividade> listaAux = atvDao.findAtividadeEntities();
        List<Atividade> lista = new ArrayList<Atividade>();
        Collection<Fasesprocesso> fases = p.getIdprocesso().getFasesprocessoCollection();
        for(Atividade atv : listaAux){
            int a = atv.getIdfase().getId();
            for(Fasesprocesso fas : fases){
                int b = fas.getId();
                
                if(a == b){
                    lista.add(atv);
                }
            }
        }
        return lista;
    }
    
    public String retornaAtividadesAnt(Atividade a){
        String atividades = " ";
        Collection<Atividadesantecessoras> colAtv = a.getAtividadesantecessorasCollection();
        for(Atividadesantecessoras atv: colAtv){
            if(atv.getIdatividadeantecessora() != null){
                atividades += atv.getIdatividadeantecessora().getNomeatividade() + "; ";
            }
            if(atv.getIdatividadeprocesso()!= null){
                atividades += atv.getIdatividadeprocesso().getNomeatividade() + "; ";
            }
        }
        return atividades.substring(0, atividades.length()-2);
    }
    
    public void carregaFase(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        FasesprocessoJpaController daoFase = new FasesprocessoJpaController(emf);
        faseSelecionada = daoFase.findFasesprocesso(fase);
        listaAtividades = daoFase.findFasesprocesso(fase).getAtividadesfaseprocessoCollection();
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("faseSelecionada", faseSelecionada);
    }
    
    public String salvarFase(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        FaseprojetoJpaController daoFase = new FaseprojetoJpaController(emf);
        Faseprojeto f = new Faseprojeto();
        try {
            Fasesprocesso aux = (Fasesprocesso) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("faseSelecionada");
            f.setIdfase(aux);
            
            f.setIdusuariogerenteprocesso(new Usuario(user.getId()));
            f.setIdprojeto(p);
            daoFase.create(f);
            encerrarSessao();
        } catch (Exception e) {
            System.out.println("Erro, classe visaoGeralBEAN, método salvarFase: " + e.toString());
        }
        return "Relatorios/visaoGeralGer.xhtml?faces-redirect=true";
    }
    
    public void encerrarSessao(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("faseSelecionada");
    }
    
}
