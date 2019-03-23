/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean;

import br.util.Util;
import data.crud.Atividade;
import data.crud.Atividademembroequipe;
import data.crud.Atividadesequipe;
import data.crud.Equipe;
import data.crud.Gerente;
import data.crud.Logusuario;
import data.crud.Projeto;
import data.crud.Usuario;
import data.dao.AtividadeJpaController;
import data.dao.AtividademembroequipeJpaController;
import data.dao.AtividadesfaseprocessoJpaController;
import data.dao.EquipeJpaController;
import data.dao.GerenteJpaController;
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
public class RelatorioGerBEAN {

    private Projeto p;
    private int pesquisa;
    private String pesq;
    private List<Logusuario> logPesq;
    
    public RelatorioGerBEAN() {
    }

    public Projeto getP() {
        return p;
    }

    public void setP(Projeto p) {
        this.p = p;
    }

    public int getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(int pesquisa) {
        this.pesquisa = pesquisa;
    }

    public String getPesq() {
        return pesq;
    }

    public void setPesq(String pesq) {
        this.pesq = pesq;
    }

    public List<Logusuario> getLogPesq() {
        return logPesq;
    }

    public void setLogPesq(List<Logusuario> logPesq) {
        this.logPesq = logPesq;
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
    }
    
    public Collection<Logusuario> retornaLogs(){
        return p.getLogusuarioCollection();
    }
    
    public String retornaAtividade(Atividademembroequipe atv){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        if(atv.getIdfuncao() == 0){
            AtividadesfaseprocessoJpaController dao = new AtividadesfaseprocessoJpaController(emf);
            return dao.findAtividadesfaseprocesso(atv.getIdatividade()).getNomeatividade();
        }
        AtividadeJpaController dao = new AtividadeJpaController(emf);
        return dao.findAtividade(atv.getIdatividade()).getNomeatividade();
    }
    
    public void pesquisar(){
        Collection<Logusuario> logs = p.getLogusuarioCollection();
        logPesq = new ArrayList<Logusuario>();
        if(pesquisa == 1){
            for(Logusuario l : logs){
                String aux = l.getIdusuario().getNomeusuario();
                if(aux.contains(pesq)){
                    logPesq.add(l);
                }
            }
        }else{
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
            for(Logusuario l : logs){
                Atividademembroequipe atv = l.getAtividade();
                if(atv.getIdfuncao() == 0){
                    AtividadesfaseprocessoJpaController dao = new AtividadesfaseprocessoJpaController(emf);
                    String aux = dao.findAtividadesfaseprocesso(atv.getIdatividade()).getNomeatividade();
                    if(aux.contains(pesq)){
                        logPesq.add(l);
                    }
                }else{
                    AtividadeJpaController dao = new AtividadeJpaController(emf);
                    String aux = dao.findAtividade(atv.getIdatividade()).getNomeatividade();
                    if(aux.contains(pesq)){
                        logPesq.add(l);
                    }
                }
            }
        }
    }
        
}
