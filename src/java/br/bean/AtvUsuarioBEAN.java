/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean;

import data.crud.Atividade;
import data.crud.Atividademembroequipe;
import data.crud.Atividadesfaseprocesso;
import data.crud.Equipe;
import data.crud.Membrosequipe;
import data.crud.Projeto;
import data.crud.Usuario;
import data.dao.AtividadeJpaController;
import data.dao.AtividademembroequipeJpaController;
import data.dao.AtividadesfaseprocessoJpaController;
import data.dao.EquipeJpaController;
import data.dao.MembrosequipeJpaController;
import data.dao.ProjetoJpaController;
import data.dao.UsuarioJpaController;
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
public class AtvUsuarioBEAN {
    
    private String atividade;
    private int[] ativsSelecionadasProcesso;
    private List<Atividade> listaAtividade;
    private List<Atividadesfaseprocesso> listaAtvProcesso;
    private List<Membrosequipe> listaEquipe;
    private List<Object> lista;
    private Equipe equipe;
    private int membro;
    private List<Atividademembroequipe> atvsMembros;
    
    public AtvUsuarioBEAN() {
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atvsSelecionada) {
        atividade = atvsSelecionada;
    }

    public int[] getAtivsSelecionadasProcesso() {
        return ativsSelecionadasProcesso;
    }

    public void setAtivsSelecionadasProcesso(int[] ativsSelecionadasProcesso) {
        this.ativsSelecionadasProcesso = ativsSelecionadasProcesso;
    }

    public List<Atividade> getListaAtividade() {
        return listaAtividade;
    }

    public void setListaAtividade(List<Atividade> listaAtividade) {
        this.listaAtividade = listaAtividade;
    }

    public List<Atividadesfaseprocesso> getListaAtvProcesso() {
        return listaAtvProcesso;
    }

    public void setListaAtvProcesso(List<Atividadesfaseprocesso> listaAtvProcesso) {
        this.listaAtvProcesso = listaAtvProcesso;
    }

    public List<Membrosequipe> getListaEquipe() {
        return listaEquipe;
    }

    public void setListaEquipe(List<Membrosequipe> listaEquipe) {
        this.listaEquipe = listaEquipe;
    }

    public List<Object> getLista() {
        return lista;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public int getMembro() {
        return membro;
    }

    public void setMembro(int membro) {
        this.membro = membro;
    }

    public List<Atividademembroequipe> getAtvsMembros() {
        return atvsMembros;
    }

    public void setAtvsMembros(List<Atividademembroequipe> atvsMembros) {
        this.atvsMembros = atvsMembros;
    }

    
    @PostConstruct
    public void carregaTabela(){
        int idProjeto = (int)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("projetoGerente");
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        Projeto p = new ProjetoJpaController(emf).findProjeto(idProjeto);
        AtividadeJpaController daoAtv = new AtividadeJpaController(emf);
        AtividadesfaseprocessoJpaController daoAtvProc = new AtividadesfaseprocessoJpaController(emf);
        List<Atividade> auxAtv = daoAtv.findAtividadeEntities();
        List<Atividadesfaseprocesso> auxProc = daoAtvProc.findAtividadesfaseprocessoEntities();
        listaAtividade = new ArrayList<Atividade>();
        listaAtvProcesso = new ArrayList<Atividadesfaseprocesso>();
        lista = new ArrayList<Object>();
        
        //carrega lista de atividades cadastrada pelo Gerentes nesse projeto
        for(Atividade atv : auxAtv){
            int a = atv.getIdfase().getIdprocesso().getId();
            int b = p.getIdprocesso().getId();
            
            int c = atv.getIdgerente().getId();
            int d = user.getId();
            
            if(a == b || c == d){
                listaAtividade.add(atv);
                lista.add(atv);
            }
        }
        
        //carrega lista de atividades do processo cadastrado pelo ADM
        for(Atividadesfaseprocesso atv : auxProc){
            int a = atv.getIdfase().getIdprocesso().getId();
            int b = p.getIdprocesso().getId();
            
            if(a == b){
                listaAtvProcesso.add(atv);
                lista.add(atv);
            }
        }
        
        //carregar usuários da equipe
        MembrosequipeJpaController daoMem = new MembrosequipeJpaController(emf);
        List<Membrosequipe> auxMem = daoMem.findMembrosequipeEntities();
        listaEquipe = new ArrayList<Membrosequipe>();
        
        for(Membrosequipe mem : auxMem){
            int a = mem.getIdequipe().getIdprojeto().getId();
            int b = idProjeto;
            
            int c = mem.getIdgerente().getId();
            int d = user.getId();
            if(a == b && c == d){
                equipe = mem.getIdequipe();
                listaEquipe.add(mem);
            }
        }
        
        //Atividades de membros
        AtividademembroequipeJpaController daoAtvMem = new AtividademembroequipeJpaController(emf);
        List<Atividademembroequipe> listaAtvMem = daoAtvMem.findAtividademembroequipeEntities();
        atvsMembros = new ArrayList<Atividademembroequipe>();
        if(equipe != null)
        for(Atividademembroequipe mem : listaAtvMem){
            int a = mem.getIdequipe().getId();
            int b = equipe.getId();
            if(a == b){
                atvsMembros.add(mem);
            }
        }
    }
    
    public void salvar(){
        int idProjeto = (int)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("projetoGerente");
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        AtividademembroequipeJpaController daoAtv = new AtividademembroequipeJpaController(emf);
        Atividademembroequipe atv = new Atividademembroequipe();
        int vai = 0;
        try {
            int funcao = Integer.parseInt(atividade.substring(0, 1));
            int usuario = Integer.parseInt(atividade.substring(2, atividade.indexOf('|')));
            int ativ = Integer.parseInt(atividade.substring(atividade.indexOf('|')+1, atividade.length())); 
            
            List<Atividademembroequipe> listAux = daoAtv.findAtividademembroequipeEntities();
            for(Atividademembroequipe atvMembro : listAux){
                Usuario u = new UsuarioJpaController(emf).findUsuario(membro);
                
                int a = atvMembro.getIdfuncao();
                int b = atvMembro.getIdusuariocadatv().getId();
                int c = atvMembro.getIdatividade();
                int d = atvMembro.getIdequipe().getId();
                
                int e = atvMembro.getIdmembro().getId();
                int f = equipe.getId();
                if(a == funcao && b == usuario && c == ativ && d == f && e == membro){
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", "Esta atividade já foi cadastrada para este usuário!");
                    RequestContext.getCurrentInstance().showMessageInDialog(message); 
                    vai = 1;
                    break;
                }
            }
            if(vai == 0){
                atv.setIdatividade(ativ);
                atv.setIdequipe(equipe);
                atv.setIdfuncao(funcao);
                atv.setIdmembro(new UsuarioJpaController(emf).findUsuario(membro));
                if(funcao == 0){
                    atv.setIdusuariocadatv(new UsuarioJpaController(emf).findUsuario(usuario));
                }else{
                    atv.setIdusuariocadatv(user);
                }
                atv.setStatus(false);

                daoAtv.create(atv);
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Atividade cadastrada com sucesso para este usuário!");
                RequestContext.getCurrentInstance().showMessageInDialog(message); 
            }
        } catch (Exception e) {
            System.out.println("Erro classe atvUsuarioBEAN, método salvar: " + e.toString());
        }
    }
    
    public void fecharDialog(){
        System.out.println("Entrei!");
    }
    
    public String retornaAtividade(Atividademembroequipe mem){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        try {
            if(mem.getIdfuncao() == 0){//Atividade cadastrada por um administrador
                AtividadesfaseprocessoJpaController daoAtv = new AtividadesfaseprocessoJpaController(emf);
                //System.out.println("olha: " + equipe.getId() + " - " + mem.getIdequipe().getId());
                return daoAtv.findAtividadesfaseprocesso(mem.getIdatividade()).getNomeatividade();
            }else{
                AtividadeJpaController daoAtv = new AtividadeJpaController(emf);
                return daoAtv.findAtividade(mem.getIdatividade()).getNomeatividade();
            }
        } catch (Exception e) {
            System.out.println("Erro, classe atvUsuarioBEAN, método retornaAtividade: " + e.toString());
            return "";
        }
    }
    
    public String retornaStatus(Atividademembroequipe mem){
        if(mem.getStatus() == false){
            return "em desenvolvimento";
        }
        return "concluído";
    }
    
    public void cliquei(){
        System.out.println("!!!!");
    }
    
    public String encerrarSecao(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        return "../index.xhtml?faces-redirect=true";
    }
}
