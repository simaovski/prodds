/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean;

import br.bean.gerenciar.GerenciarProcessoBEAN;
import br.util.Util;
import br.util.UtilDao;
import data.crud.Atividadesantecessorasfaseprocesso;
import data.crud.Atividadesfaseprocesso;
import data.crud.Fasesprocesso;
import data.crud.Processo;
import data.crud.Usuario;
import data.dao.AtividadesantecessorasfaseprocessoJpaController;
import data.dao.AtividadesfaseprocessoJpaController;
import data.dao.FasesprocessoJpaController;
import data.dao.ProcessoJpaController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.primefaces.context.RequestContext;

/**
 *
 * @author padrao
 */
@ManagedBean
@SessionScoped
public class ProcessoBEAN {

    private String nomeProcesso;
    private int totalFases = 1;
    private String descricaoProcesso;
    private boolean btnProsseguir = false;
    private int contFase = 1;
    private Processo p;
    private boolean fase;
    
    private String nomeFase;
    private String descricaoFase;
    private List<Fasesprocesso> listaFases;
    
    private int[] atividadesAntecessoras;
    private String nomeAtividade;
    private String descricaoAtividade;
    private String atividadesSucessoras;
    private List<Atividadesfaseprocesso> listaAtividades;
    
    private List<Processo> listaProcessos;
    public ProcessoBEAN() {
    }

    public String getNomeProcesso() {
        return nomeProcesso;
    }

    public void setNomeProcesso(String nomeProcesso) {
        this.nomeProcesso = nomeProcesso;
    }

    public int getTotalFases() {
        return totalFases;
    }

    public void setTotalFases(int totalFases) {
        this.totalFases = totalFases;
    }

    public String getDescricaoProcesso() {
        return descricaoProcesso;
    }

    public void setDescricaoProcesso(String descricaoProcesso) {
        this.descricaoProcesso = descricaoProcesso;
    }

    public boolean getBtnProsseguir() {
        return btnProsseguir;
    }

    public void setBtnProsseguir(boolean btnProsseguir) {
        this.btnProsseguir = btnProsseguir;
    }

    public boolean isFase() {
        return fase;
    }

    public void setFase(boolean fase) {
        this.fase = fase;
    }

    public List<Processo> getListaProcessos() {
        return listaProcessos;
    }

    public void setListaProcessos(List<Processo> listaProcessos) {
        this.listaProcessos = listaProcessos;
    }
    
    
    @PostConstruct
    public void carregaTabela(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        ProcessoJpaController daoProc = new ProcessoJpaController(emf);
        List<Processo> auxProc = daoProc.findProcessoEntities();
        listaProcessos = new ArrayList<Processo>();
        for(Processo p : auxProc){
            int a = user.getId();
            int b = p.getIdusuario().getId();
            if(a == b){
                listaProcessos.add(p);
            }
        }
        processo = new Processo();
        nomeProcesso = "";
    }
    
    public void prosseguir(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        ProcessoJpaController daoPro = new ProcessoJpaController(emf);
        
        if(daoPro.verificaNomeProcesso(nomeProcesso, user) == false){
            p = new Processo();
            
            p.setNomeprocesso(nomeProcesso);
            p.setTotalfases(totalFases);
            p.setDescricao(descricaoProcesso);
            p.setIdusuario(user);
            btnProsseguir = true;
           
            daoPro.create(p);
            
            nomeProcesso = "";
            totalFases = 1;
            descricaoProcesso = "";
            
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("totalFases", 1);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("processo", p.getId());
            fase = true;
            
        }else{
            fase = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nome do Processo", "O nome do processo já está cadastrado");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
    }
    
    public boolean permissao(){
        Util u = new Util();
        return u.permissaoAdm();
    }
    
    /**
     *Parte do BEAN responsável por gerenciar a tabela faseProcesso
     */
    
    public String getNomeFase() {
        return nomeFase;
    }

    public void setNomeFase(String nomeFase) {
        this.nomeFase = nomeFase;
    }

    public String getDescricaoFase() {
        return descricaoFase;
    }

    public void setDescricaoFase(String descricaoFase) {
        this.descricaoFase = descricaoFase;
    }

    public int getContFase() {
        return contFase;
        
    }

    public void setContFase(int contFase) {
        this.contFase = contFase;
    }

    public List<Fasesprocesso> getListaFases() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        FasesprocessoJpaController daoFasesProc = new FasesprocessoJpaController(emf);
        List<Fasesprocesso> aux = daoFasesProc.findFasesprocessoEntities();
        listaFases = new ArrayList<Fasesprocesso>();
        
        try {
            
            for(int i = 0; i < aux.size(); i++){
                int a = aux.get(i).getIdprocesso().getId();
                ProcessoJpaController daoProc = new ProcessoJpaController(emf);
                int idProcesso = (int)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("processo");

                int b = daoProc.findProcesso(idProcesso).getId();

                if(a == b){
                    listaFases.add(aux.get(i));
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: processo bean: " + e.toString());
        }
        
        return listaFases;
    }

    public void setListaFases(List<Fasesprocesso> listaFases) {
        this.listaFases = listaFases;
    }
    
    
    
    public void cadastrarFase(){
        try {
            contFase = (int)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("totalFases");
        } catch (Exception e) {
            System.out.println("Erro processo BEAN, método cadastrarFase: " + e.toString());
        }
        
        
        if(contFase <= totalFases){
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
            FasesprocessoJpaController daoFasesProc = new FasesprocessoJpaController(emf);
            Fasesprocesso fp = new Fasesprocesso();
            
            ProcessoJpaController daoProc = new ProcessoJpaController(emf);
            int idProcesso = (int)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("processo");
            
            fp.setNomefase(nomeFase);
            fp.setDescricaofase(descricaoFase);
            fp.setIdprocesso(daoProc.findProcesso(idProcesso));
            
            daoFasesProc.create(fp);
            if(contFase < totalFases)
                contFase++;
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("totalFases", contFase);
            
            nomeFase = "";
            descricaoFase = "";
            fase = true;
        }
    }
    
    
    /**
    Parte do BEAN responsável por adicionar as atividades
     */
    public int[] getAtividadesAntecessoras() {
        return atividadesAntecessoras;
    }

    public void setAtividadesAntecessoras(int[] atividadesAntecessoras) {
        this.atividadesAntecessoras = atividadesAntecessoras;
    }

    public String getNomeAtividade() {
        return nomeAtividade;
    }

    public void setNomeAtividade(String nomeAtividade) {
        this.nomeAtividade = nomeAtividade;
    }

    public String getDescricaoAtividade() {
        return descricaoAtividade;
    }

    public void setDescricaoAtividade(String descricaoAtividade) {
        this.descricaoAtividade = descricaoAtividade;
    }

    public String getAtividadesSucessoras() {
        return atividadesSucessoras;
    }

    public void setAtividadesSucessoras(String atividadesSucessoras) {
        this.atividadesSucessoras = atividadesSucessoras;
    }

    
    public List<Atividadesfaseprocesso> getListaAtividades() {
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        AtividadesfaseprocessoJpaController daoAtvFaseProc = new AtividadesfaseprocessoJpaController(emf);
        List<Atividadesfaseprocesso> aux = daoAtvFaseProc.findAtividadesfaseprocessoEntities();
        try{
            listaAtividades = new ArrayList<Atividadesfaseprocesso>();
            for(int i = 0; i < aux.size(); i++){
                int b = (int)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("processo");
                int a = aux.get(i).getIdfase().getIdprocesso().getId();
                //int b = f.getIdprocesso().getId();

                if(a == b){
                    listaAtividades.add(aux.get(i));
                }
            }
        }catch(Exception e){
            System.out.println("Erro processo BEAN, método getListaAtividades: " + e.toString());
        }
        return listaAtividades;
    }

    public void setListaAtividades(List<Atividadesfaseprocesso> listaAtividades) {
        this.listaAtividades = listaAtividades;
    }

    public void adicionarAtividades(Fasesprocesso f){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fase", f);
    }
 
    public void salvarAtividades(){        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        AtividadesfaseprocessoJpaController daoAtvFaseProc = new AtividadesfaseprocessoJpaController(emf);
        AtividadesantecessorasfaseprocessoJpaController daoAtvAnt = new AtividadesantecessorasfaseprocessoJpaController(emf);
        Atividadesfaseprocesso a = new Atividadesfaseprocesso();
        Atividadesantecessorasfaseprocesso atvAnt = new Atividadesantecessorasfaseprocesso();
        try {
            Fasesprocesso f = (Fasesprocesso)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fase");
        
            a.setNomeatividade(nomeAtividade);
            a.setDescricaoatividade(descricaoAtividade);
            a.setIdfase(f);
            a.setAtividadessucessoras(atividadesSucessoras);
            daoAtvFaseProc.create(a);
            for(int i = 0; i < atividadesAntecessoras.length; i++){
                atvAnt.setIdatividade(a);
                atvAnt.setIdatividadesucessora(daoAtvFaseProc.findAtividadesfaseprocesso(atividadesAntecessoras[i]));
                daoAtvAnt.create(atvAnt);
            }
            nomeAtividade = "";
            descricaoAtividade = "";
            f = null;
            atividadesSucessoras = "";
            a = null;
        } catch (Exception e) {
            System.out.println("Erro processo BEAN, método: salvarAtividades() " + e.toString());
        }
        
    }
    
    public List<Atividadesfaseprocesso> listaAtividadeFase(){
        List<Atividadesfaseprocesso> listaAtvsFase = new ArrayList<Atividadesfaseprocesso>();
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
            Fasesprocesso f = (Fasesprocesso)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fase");
            AtividadesfaseprocessoJpaController daoAtvFaseProc = new AtividadesfaseprocessoJpaController(emf);
            List<Atividadesfaseprocesso> aux = daoAtvFaseProc.findAtividadesfaseprocessoEntities();


            for(int i = 0; i < aux.size(); i++){
                int a = f.getId();
                int b = aux.get(i).getIdfase().getId();
                
                if(a == b){
                    listaAtvsFase.add(aux.get(i));
                }
            }
        }catch(Exception e){
            System.out.println("Erro processoBEAN, método listaAtividadeFase: " + e.toString());
            return null;
        }
        return listaAtvsFase;
    }
    
    public void fecharDialog(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("fase");
    }
    
    public String encerrarSecao(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        return "../index.xhtml?faces-redirect=true";
    }
    
    public Usuario retornaUsuario(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return user;
    }
    
    
    /*******************EDITAR**********************/
    private Processo processo;
    private List<Fasesprocesso> listaFase;
    

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public List<Fasesprocesso> getListaFase() {
        return listaFase;
    }

    public void setListaFase(List<Fasesprocesso> listaFase) {
        this.listaFase = listaFase;
    }    
    
    
    
    public void alterar(Processo proc){     
        nomeProcesso = proc.getNomeprocesso();
        processo = proc;
    }
    
    public void avisoRemocao(Processo proc){
        processo = proc;
    }
    
    public String salvaAlteracao(Processo pro){
        if(pro.getDescricao().isEmpty() || pro.getNomeprocesso().isEmpty()){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Nome do Processo ou sua Descrição não pode ser nulo!");
            RequestContext.getCurrentInstance().showMessageInDialog(message); 
        }else{
            try {
                EntityManagerFactory emf = UtilDao.retornaEntity();
                ProcessoJpaController daoProc = new ProcessoJpaController(emf);
                daoProc.edit(pro);
                emf.close();
            } catch (Exception e) {
                System.out.println("Erro, classe ProcessoBEAN, método: remover " + e.toString());
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operação Não Permitida", "O Processo está veinculado à um ou mais Projetos");
                RequestContext.getCurrentInstance().showMessageInDialog(message);   
            }
        }
        return "/CADs/cadProcesso.xhtml";
    }
    
    public void remover(Processo proc){
        try {
            EntityManagerFactory emf = UtilDao.retornaEntity();
            ProcessoJpaController daoProc = new ProcessoJpaController(emf);
            daoProc.destroy(proc.getId());
            emf.close();
        } catch (Exception e) {
            System.out.println("Erro, classe ProcessoBEAN, método: remover " + e.toString());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operação Não Permitida", "O Processo está veinculado à um ou mais Projetos");
            RequestContext.getCurrentInstance().showMessageInDialog(message);   
        }
        //return "/CADs/cadProcesso.xhtml?faces-redirect=true";
    }
}


