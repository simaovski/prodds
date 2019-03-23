/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean;

import br.util.Util;
import data.crud.Atividadesfaseprocesso;
import data.crud.Fasesprocesso;
import data.crud.Processo;
import data.crud.Projeto;
import data.crud.Statusprojeto;
import data.crud.Usuario;
import data.dao.AtividadesfaseprocessoJpaController;
import data.dao.FasesprocessoJpaController;
import data.dao.ProcessoJpaController;
import data.dao.ProjetoJpaController;
import data.dao.StatusprojetoJpaController;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author padrao
 */
@ManagedBean
@RequestScoped
public class ProjetoBEAN implements Serializable {

    private List<Processo> processos;
    private String nomeProjeto;
    private String nomeProcesso;
    private int processo;
    private String descricao;
    private String artefatos;
    private Date dataCriacao;
    private int id;
    private boolean verifica;
    
    public ProjetoBEAN() {
    }

    public List<Processo> getProcessos() {
        return processos;
    }

    public void setProcessos(List<Processo> processos) {
        this.processos = processos;
    }
    
    public String getNomeProjeto() {
        return nomeProjeto;
    }

    public void setNomeProjeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }

    public String getNomeProcesso() {
        return nomeProcesso;
    }

    public void setNomeProcesso(String nomeProcesso) {
        this.nomeProcesso = nomeProcesso;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getArtefatos() {
        return artefatos;
    }

    public void setArtefatos(String artefatos) {
        this.artefatos = artefatos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isVerifica() {
        return verifica;
    }

    public int getProcesso() {
        return processo;
    }

    public void setProcesso(int processo) {
        this.processo = processo;
    }
    
    
    
    @PostConstruct
    public void carregaTabela() {
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        ProcessoJpaController daoProc = new ProcessoJpaController(emf);
        List<Processo> aux = daoProc.findProcessoEntities();
        processos = new ArrayList<Processo>();
        for(Processo p : aux){
            int a = p.getIdusuario().getId();
            int b = user.getId();
            if(a == b)
                processos.add(p);
        }
    }
    
    public List listaProcessos(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        ProcessoJpaController daoProc = new ProcessoJpaController(emf);
        return daoProc.findProcessoEntities();
    }
    
    public void selecionarProcesso(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        ProjetoJpaController daoProj = new ProjetoJpaController(emf);
        ProcessoJpaController daoProc = new ProcessoJpaController(emf);
        if(daoProj.verificarNomeProjeto(nomeProjeto, user) == true){
            
            if(nomeProjeto.isEmpty() == true || dataCriacao == null){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro", "O nome do projeto ou a data de criação não foi selecionada!");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }else{
                Projeto p = new Projeto();

                p.setNomeprojeto(nomeProjeto);
                p.setIdprocesso(daoProc.findProcesso(processo));
                p.setIdadm(user);
                p.setDescricao(descricao);
                p.setDatacriacao(dataCriacao.getDate() + "/" + (dataCriacao.getMonth() + 1) + "/" + (dataCriacao.getYear()+1900));
                //gerMonth: Soma o mês com 1 pq janeiro significa zero. O getYear retorna o valor de 1900 - dataSelecionada
                daoProj.create(p);

                StatusprojetoJpaController daoStatusProj = new StatusprojetoJpaController(emf);
                Statusprojeto sp = new Statusprojeto();

                sp.setIdadm(user);
                sp.setIdprojeto(p.getId());
                sp.setStatus(Boolean.FALSE);
                sp.setDatafinal("-");
                sp.setEstado(1);
                daoStatusProj.create(sp);
                
                nomeProjeto="";
                descricao="";
                
                verifica = true;
            }
            //daoStatusProj.create(sp);
        }else{
            verifica = false;
            //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Projeto", "O nome do projeto já está cadastrado");
            //RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
    }
    
    public void descricaoProcesso(Processo p){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        FasesprocessoJpaController faseProcDao = new FasesprocessoJpaController(emf);
        AtividadesfaseprocessoJpaController atvDao = new AtividadesfaseprocessoJpaController(emf);
        List<Fasesprocesso> listaFases = faseProcDao.findFasesprocessoEntities();
        List<Atividadesfaseprocesso> listaAtv = atvDao.findAtividadesfaseprocessoEntities();
        
        String msg = "Total de Fases (" + p.getTotalfases() + "): ";
        for(int i = 0; i < listaFases.size(); i++){
            int a = listaFases.get(i).getIdprocesso().getId();
            int b = p.getId();
            if(a == b){
                msg += listaFases.get(i).getNomefase() + "; ";
            }
        }
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Processo: " + p.getNomeprocesso(), msg.substring(0, msg.length()-2));
        RequestContext.getCurrentInstance().showMessageInDialog(message); 
    }
    
    
    
    public boolean permissaoAdm(){
        Util u = new Util();
        return u.permissaoAdm();
        
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
