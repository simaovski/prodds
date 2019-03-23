/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean;

import data.dao.AtividadeJpaController;
import data.dao.AtividadesantecessorasJpaController;
import data.crud.Atividade;
import data.crud.Atividadesantecessoras;
import data.crud.Usuario;
import br.util.Util;
import data.crud.Atividadesfaseprocesso;
import data.crud.Fasesprocesso;
import data.crud.Projeto;
import data.dao.AtividadesantecessorasfaseprocessoJpaController;
import data.dao.AtividadesfaseprocessoJpaController;
import data.dao.FasesprocessoJpaController;
import data.dao.ProjetoJpaController;
import java.util.ArrayList;
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

/**
 *
 * @author padrao
 */
@ManagedBean
@RequestScoped
public class AtividadeBEAN {

    private String nomeAtividade;
    private String preRequisito;
    private String descricao;
    private String AtvsSucessoras;
    private Date dataInicio;
    private Date dataFim;
    private int[] atvsAntecessoras;
    private int[] atvsAntecessorasProcesso;
    private Projeto p;
    private List<Fasesprocesso> listaFases;
    private int idFase;
    
    public AtividadeBEAN() {
    }

    public String getNomeAtividade() {
        return nomeAtividade;
    }

    public void setNomeAtividade(String nomeAtividade) {
        this.nomeAtividade = nomeAtividade;
    }

    public String getPreRequisito() {
        return preRequisito;
    }

    public void setPreRequisito(String preRequisito) {
        this.preRequisito = preRequisito;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAtvsSucessoras() {
        return AtvsSucessoras;
    }

    public void setAtvsSucessoras(String AtvsSucessoras) {
        this.AtvsSucessoras = AtvsSucessoras;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public int[] getAtvsAntecessoras() {
        return atvsAntecessoras;
    }

    public void setAtvsAntecessoras(int[] atvsAntecessoras) {
        this.atvsAntecessoras = atvsAntecessoras;
    }

    public List<Fasesprocesso> getListaFases() {
        return listaFases;
    }

    public void setListaFases(List<Fasesprocesso> listaFases) {
        this.listaFases = listaFases;
    }

    public int getIdFase() {
        return idFase;
    }

    public void setIdFase(int idFase) {
        this.idFase = idFase;
    }

    public int[] getAtvsAntecessorasProcesso() {
        return atvsAntecessorasProcesso;
    }

    public void setAtvsAntecessorasProcesso(int[] atvsAntecessorasProcesso) {
        this.atvsAntecessorasProcesso = atvsAntecessorasProcesso;
    }
    
    
    
    @PostConstruct
    public void carregaInfo(){
        int idProjeto = (int)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("projetoGerente");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        ProjetoJpaController daoProj = new ProjetoJpaController(emf);
        p = daoProj.findProjeto(idProjeto);
        
        FasesprocessoJpaController daoFase = new FasesprocessoJpaController(emf);
        List<Fasesprocesso> aux = daoFase.findFasesprocessoEntities();
        listaFases = new ArrayList<Fasesprocesso>();
        
        for(Fasesprocesso f : aux){
            int a = f.getIdprocesso().getId();
            int b = p.getIdprocesso().getId();
            
            if(a == b){
                listaFases.add(f);
            }
        }
    }
    
    public Projeto getP() {
        return p;
    }
    
    public List<Atividade> retornaAtividades() {
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        
        AtividadeJpaController daoAtiv = new AtividadeJpaController(emf);
        List<Atividade> atvAux = daoAtiv.findAtividadeEntities();
        List<Atividade> listaAtividades = new ArrayList<Atividade>();
        
        for(Atividade atv : atvAux){
            int a = atv.getIdfase().getIdprocesso().getId();
            int b = p.getIdprocesso().getId();
            if(a == b){
                listaAtividades.add(atv);
            }
        }
        return listaAtividades;
    }
    
    public List<Atividadesfaseprocesso> retornaAtividadesProcesso(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        AtividadesfaseprocessoJpaController daoAtvPro = new AtividadesfaseprocessoJpaController(emf);
        List<Atividadesfaseprocesso> listAtvPro = daoAtvPro.findAtividadesfaseprocessoEntities();
        List<Atividadesfaseprocesso> listaAtv = new ArrayList<Atividadesfaseprocesso>(); 
        for(Atividadesfaseprocesso atv : listAtvPro){
            int a = atv.getIdfase().getIdprocesso().getId();
            int b = p.getIdprocesso().getId();
            if(a == b){
                listaAtv.add(atv);
            }
        }
        return listaAtv; 
    }
       
    public void salvar(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        Fasesprocesso f = new FasesprocessoJpaController(emf).findFasesprocesso(idFase);
        AtividadeJpaController ativDao = new AtividadeJpaController(emf);
        AtividadesantecessorasJpaController ativDaoAnt = new AtividadesantecessorasJpaController(emf);
        AtividadesfaseprocessoJpaController ativProcDao = new AtividadesfaseprocessoJpaController(emf);
        Atividade a = new Atividade();
        
        a.setIdgerente(user);
        a.setNomeatividade(nomeAtividade);
        a.setDescricao(descricao);
        a.setAtividadessucessoras(AtvsSucessoras);
        a.setIdfase(f);
        
        try {
            ativDao.create(a);
            
            Atividadesantecessoras at = new Atividadesantecessoras();
            for(int i = 0; i < atvsAntecessoras.length; i++){
                at.setIdatividade(a);
                at.setIdatividadeantecessora(ativDao.findAtividade(atvsAntecessoras[i]));
                at.setIdatividadeantecessoraprocesso(null);
                at.setIdatividadeprocesso(null);
                
                ativDaoAnt.create(at);
            }
            for(int i = 0; i < atvsAntecessorasProcesso.length; i++){
                at.setIdatividade(a);
                at.setIdatividadeantecessora(null);
                at.setIdatividadeantecessoraprocesso(null);
                at.setIdatividadeprocesso(ativProcDao.findAtividadesfaseprocesso(atvsAntecessorasProcesso[i]));
                ativDaoAnt.create(at);
            }
            nomeAtividade = "";
            descricao = "";
            AtvsSucessoras = "";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Atividade cadastrada com sucesso");
            RequestContext.getCurrentInstance().showMessageInDialog(message);  
        } catch (Exception e) {
            System.out.println("Erro, classe atividade BEAN, método salvar: " + e.toString());
        }
    }
        
    public boolean permissao(){
        Util u = new Util();
        return u.permissaoGer();
        
    }
    
    public String direcionaUrl(){
        Util u = new Util();
        return u.direcionaUrl();
    }
    
    public Usuario retornaUsuario(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return user;
    }
    
    public String encerrarSecao(){
        //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        return "../index.xhtml?faces-redirect=true";
    }
}
