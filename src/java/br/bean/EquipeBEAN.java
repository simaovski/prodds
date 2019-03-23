/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean;

import br.util.Util;
import br.util.UtilDao;
import data.crud.Atividade;
import data.crud.Atividadesequipe;
import data.crud.Atividadesequipegerente;
import data.crud.Atividadesfaseprocesso;
import data.crud.Equipe;
import data.crud.Fasesprocesso;
import data.crud.Localizacao;
import data.crud.Membrosequipe;
import data.crud.Projeto;
import data.crud.Statusprojeto;
import data.crud.Usuario;
import data.dao.AtividadeJpaController;
import data.dao.AtividadesequipeJpaController;
import data.dao.AtividadesequipegerenteJpaController;
import data.dao.AtividadesfaseprocessoJpaController;
import data.dao.EquipeJpaController;
import data.dao.LocalizacaoJpaController;
import data.dao.MembrosequipeJpaController;
import data.dao.ProjetoJpaController;
import data.dao.UsuarioJpaController;
import java.util.ArrayList;
import java.util.Collection;
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
public class EquipeBEAN {

    private String nomeEquipe;
    private String atividades;
    private String descricaoEquipe;
    private String pais;
    private String estado;
    private String cidade;
    private int[] atividadesSelecionadas;
    private List<Atividadesfaseprocesso> listaAtividades;
    private List<Atividade> listaAtividadesGer;
    private Projeto proj;
    private List<Usuario> listaMem;
    private int[] atividadesGerSelecionadas;
    
    
    public EquipeBEAN() {
    }

    public String getNomeEquipe() {
        return nomeEquipe;
    }

    public void setNomeEquipe(String nomeEquipe) {
        this.nomeEquipe = nomeEquipe;
    }

    public String getAtividades() {
        return atividades;
    }

    public void setAtividades(String atividades) {
        this.atividades = atividades;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getDescricaoEquipe() {
        return descricaoEquipe;
    }

    public void setDescricaoEquipe(String descricaoEquipe) {
        this.descricaoEquipe = descricaoEquipe;
    }

    public int[] getAtividadesSelecionadas() {
        return atividadesSelecionadas;
    }

    public void setAtividadesSelecionadas(int[] atividadesSelecionadas) {
        this.atividadesSelecionadas = atividadesSelecionadas;
    }

    public List<Atividadesfaseprocesso> getListaAtividades() {
        return listaAtividades;
    }

    public void setListaAtividades(List<Atividadesfaseprocesso> listaAtividades) {
        this.listaAtividades = listaAtividades;
    }

    public Projeto getProj() {
        return proj;
    }

    public void setProj(Projeto p) {
        this.proj = p;
    }

    public List<Usuario> getListaMem() {
        return listaMem;
    }

    public void setListaMem(List<Usuario> listaMem) {
        this.listaMem = listaMem;
    }

    public List<Atividade> getListaAtividadesGer() {
        return listaAtividadesGer;
    }

    public void setListaAtividadesGer(List<Atividade> listaAtividadesGer) {
        this.listaAtividadesGer = listaAtividadesGer;
    }

    public int[] getAtividadesGerSelecionadas() {
        return atividadesGerSelecionadas;
    }

    public void setAtividadesGerSelecionadas(int[] atividadesGerSelecionadas) {
        this.atividadesGerSelecionadas = atividadesGerSelecionadas;
    }
    
    

    @PostConstruct
    public void carregaAtividades(){
       Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
       int idProjeto = (int)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("projetoGerente");
         
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
       AtividadesfaseprocessoJpaController daoAtv = new AtividadesfaseprocessoJpaController(emf);
       List<Atividadesfaseprocesso> listaAtv = daoAtv.findAtividadesfaseprocessoEntities();
       ProjetoJpaController daoProj = new ProjetoJpaController(emf);
       Projeto p = daoProj.findProjeto(idProjeto);
       proj = p;
       listaAtividades = new ArrayList<Atividadesfaseprocesso>();
       
       for(int i = 0; i < listaAtv.size(); i++){
           int a = listaAtv.get(i).getIdfase().getIdprocesso().getId();
           int b = p.getIdprocesso().getId();
           
           if(a == b){
               listaAtividades.add(listaAtv.get(i));
           }
       }
       
       UsuarioJpaController daoUser = new UsuarioJpaController(emf);
       List<Usuario> auxUser = daoUser.findUsuarioEntities();
       listaMem = new ArrayList<Usuario>();
       
       for(Usuario u : auxUser){
           int a = u.getFuncaousuario();
           if(a == 2){
               listaMem.add(u);
           }
       }
       
       Collection<Fasesprocesso> teste  = p.getIdprocesso().getFasesprocessoCollection();
       listaAtividadesGer = new ArrayList<Atividade>();
       for(Fasesprocesso f: teste){
           Collection<Atividade> aux = f.getAtividadeCollection();
           for(Atividade a : aux){
               int b = a.getIdgerente().getId();
               int c = user.getId();
               if(b == c)
                   listaAtividadesGer.add(a);
           }
       }
       
       
    }   
    
    public boolean verificarEquipe(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        EquipeJpaController daoEquipe = new EquipeJpaController(emf);
        List<Equipe> eqp = daoEquipe.findEquipeEntities();
        for(Equipe e : eqp){
            int a = e.getIdprojeto().getId();
            int b = proj.getId();
            
            int c = e.getIdgerente().getId();
            int d = user.getId();
            
            if(a == b && c == d){
                return false;
            }
        }
        return true;
    }
    
    public void salvar(){
        
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        EntityManagerFactory emf = UtilDao.retornaEntity();
        EquipeJpaController daoEquipe = new EquipeJpaController(emf);
        
        if(daoEquipe.verificaNomeEquipe(user, nomeEquipe) == true){
            Equipe e = new Equipe();
            
            e.setDescricao(descricaoEquipe);
            e.setIdgerente(user);
            e.setIdprojeto(proj);
            e.setNomeequipe(nomeEquipe);
            
            try {
                daoEquipe.create(e);
                
                LocalizacaoJpaController daoLoc = new LocalizacaoJpaController(emf);
                Localizacao l = new Localizacao();

                l.setCidade(cidade);
                l.setEstado(estado);
                l.setPais(pais);
                l.setIdgerente(user);
                l.setIdequipe(e.getId());
                
                daoLoc.create(l);
                
                
                AtividadesfaseprocessoJpaController daoAtv = new AtividadesfaseprocessoJpaController(emf);
                AtividadesequipeJpaController daoAtvEqp = new AtividadesequipeJpaController(emf);
                Atividadesequipe atvEqp = new Atividadesequipe();
                
                for(int i = 0; i < atividadesSelecionadas.length; i++){
                    atvEqp.setIdatividade(daoAtv.findAtividadesfaseprocesso(atividadesSelecionadas[i]));
                    atvEqp.setIdequipe(e);
                    
                    daoAtvEqp.create(atvEqp);
                }
                
                AtividadesequipegerenteJpaController daoAtvGerAux = new AtividadesequipegerenteJpaController(emf);
                AtividadeJpaController daoAtvGer = new AtividadeJpaController(emf);
                Atividadesequipegerente atvEqpGer = new Atividadesequipegerente();
                for(int i = 0; i < atividadesGerSelecionadas.length; i++){
                    atvEqpGer.setIdatividade(daoAtvGer.findAtividade(atividadesGerSelecionadas[i]));
                    atvEqpGer.setIdequipe(e);
                    atvEqpGer.setIdusuariogerente(user);
                    
                    daoAtvGerAux.create(atvEqpGer);
                }
            } catch (Exception ex) {
                System.out.println("Erro equipeBean, método salvar: " + ex.toString());
            }
        }else{
            System.out.println("Nao pode");
        }
        emf.close();
    }
    
    public boolean permissao(){
        Util u = new Util();
        return u.permissaoGer();
        
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
    
    public void fecharDialog(){
        //System.out.println("Fechei");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("equipe");
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Equipe cadastrada com sucesso");
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
    
    /*****************************************************************/
    /*
    Parte do código responsável por popular o combo box de atividades
    */
    
 
}
