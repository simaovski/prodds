/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean.gerenciar;

import br.util.Util;
import br.util.UtilDao;
import data.crud.Atividade;
import data.crud.Atividadesantecessoras;
import data.crud.Atividadesfaseprocesso;
import data.crud.Fasesprocesso;
import data.crud.Projeto;
import data.crud.Usuario;
import data.dao.AtividadeJpaController;
import data.dao.AtividademembroequipeJpaController;
import data.dao.AtividadesfaseprocessoJpaController;
import data.dao.FasesprocessoJpaController;
import data.dao.ProjetoJpaController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author padrao
 */
@ManagedBean
@RequestScoped
public class gerAtividadeBEAN {

    private List<Atividade> lista;
    private Atividade atividade = new Atividade();
    private List<Fasesprocesso> fases;
    private Projeto p;
    private List<Atividade> listaAtividade;
    private int[] atvSel;
    private int[] atvSelGer;
    private int atvsel;
    private int idFase;
    private Collection<Atividadesantecessoras> antecessoras;
    
    
    public gerAtividadeBEAN() {
    }

    public List<Atividade> getLista() {
        return lista;
    }

    public void setLista(List<Atividade> lista) {
        this.lista = lista;
    }

    public Atividade getAtividade() {
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }

    public List<Fasesprocesso> getFases() {
        return fases;
    }

    public void setFases(List<Fasesprocesso> fases) {
        this.fases = fases;
    }

    public Projeto getP() {
        return p;
    }

    public void setP(Projeto p) {
        this.p = p;
    }

    public int[] getAtvSel() {
        return atvSel;
    }

    public void setAtvSel(int[] atvSel) {
        this.atvSel = atvSel;
    }

    public int[] getAtvSelGer() {
        return atvSelGer;
    }

    public void setAtvSelGer(int[] atvSelGer) {
        this.atvSelGer = atvSelGer;
    }

    public int getFaseSel() {
        return atvsel;
    }

    public void setFaseSel(int atvsel) {
        this.atvsel = atvsel;
    }

    public List<Atividade> getListaAtividade() {
        return listaAtividade;
    }

    public void setListaAtividade(List<Atividade> listaAtividade) {
        this.listaAtividade = listaAtividade;
    }

    public int getIdFase() {
        return idFase;
    }

    public void setIdFase(int idFase) {
        this.idFase = idFase;
    }
    
    
    
    @PostConstruct
    public void carregaTabela(){
        int idProjeto = (int)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("projetoGerente");
        Usuario user = retornaUsuario();
        EntityManagerFactory emf = UtilDao.retornaEntity();
        
        ProjetoJpaController daoProj = new ProjetoJpaController(emf);
        p = daoProj.findProjeto(idProjeto);
        
        AtividadeJpaController daoAtv = new AtividadeJpaController(emf);
        List<Atividade> listaAux = daoAtv.findAtividadeEntities();
        lista = new ArrayList<Atividade>();
        for(Atividade atv : listaAux){
            int a = atv.getIdgerente().getId();
            int b = user.getId();
            
            int c = atv.getIdfase().getIdprocesso().getId();
            int d = p.getIdprocesso().getId();
            if(a == b && c == d)
                lista.add(atv);
        }        
        emf.close();
    }   
    
    public List<Fasesprocesso> retornaFases(){
        EntityManagerFactory emf = UtilDao.retornaEntity();
        FasesprocessoJpaController daoFase = new FasesprocessoJpaController(emf);
        List<Fasesprocesso> listaAux = daoFase.findFasesprocessoEntities();
        List<Fasesprocesso> listaFases = new ArrayList<Fasesprocesso>();
        
        for(Fasesprocesso f : listaAux){
            int a = f.getIdprocesso().getId();
            int b = p.getIdprocesso().getId();
            if(a == b)
                listaFases.add(f);
        }
        emf.close();
        return listaFases;

    }
    
    public List<Atividadesfaseprocesso> retornaAtividadesAdm(Atividade atv){
        EntityManagerFactory emf = UtilDao.retornaEntity();
        AtividadesfaseprocessoJpaController daoAtv = new AtividadesfaseprocessoJpaController(emf);
        List<Atividadesfaseprocesso> listaAux = daoAtv.findAtividadesfaseprocessoEntities();
        List<Atividadesfaseprocesso> listaAtv = new ArrayList<Atividadesfaseprocesso>();
        
        for(Atividadesfaseprocesso at : listaAux){
            int a = at.getIdfase().getIdprocesso().getId();
            int b = atv.getIdfase().getIdprocesso().getId();
            if(a == b)
                listaAtv.add(at);
        }
        return listaAtv;
    }
    
    
    public List<Atividade> retornaAtividadesGer(Atividade atv){
        EntityManagerFactory emf = UtilDao.retornaEntity();
        AtividadeJpaController daoAtv = new AtividadeJpaController(emf);
        List<Atividade> listaAux = daoAtv.findAtividadeEntities();
        List<Atividade> listaAtv = new ArrayList<Atividade>();
        
        for(Atividade at : listaAux){
            int a = at.getIdfase().getIdprocesso().getId();
            int b = atv.getIdfase().getIdprocesso().getId();
            if(a == b)
                listaAtv.add(at);
        }
        emf.close();
        return listaAtv;
    }
    
    public void alterar(){
        EntityManagerFactory emf = UtilDao.retornaEntity();
        AtividadeJpaController daoAtv = new AtividadeJpaController(emf);
       
        atividade = daoAtv.findAtividade(atvsel);
        emf.close();
    }
    
    public String salvarAlteracao(){
        EntityManagerFactory emf = UtilDao.retornaEntity();        
        AtividadeJpaController daoAtv = new AtividadeJpaController(emf);
        FasesprocessoJpaController daoFase = new FasesprocessoJpaController(emf);
        try {
            //System.out.println(atividade.getAtividadesantecessorasCollection().size());
            
            atividade.setIdfase(daoFase.findFasesprocesso(idFase));
            atividade.setIdgerente(retornaUsuario());
            
            daoAtv.editar(atividade);
        } catch (Exception e) {
            System.out.println("Erro, classe gerAtividadeBEAN, método salvarAlteracao: " + e.toString());
            e.printStackTrace();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um erro ao efetuar o processamento");
            RequestContext.getCurrentInstance().showMessageInDialog(message);  
        }
        emf.close();
        return "/ALTs/altAtividade.xhtml?faces-config=true";
    }
    
    public void remover(){
        EntityManagerFactory emf = UtilDao.retornaEntity();
        AtividademembroequipeJpaController dao = new AtividademembroequipeJpaController(emf);
        if(dao.verificaRemocao(atividade, retornaUsuario(), p)){
            try {

                AtividadeJpaController daoAtv = new AtividadeJpaController(emf);
                daoAtv.destroy(atividade.getId());
            } catch (Exception e) {
                System.out.println("erro, classe gerAtividadeBEAN, método remover: " + e.toString());
            }
        }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Essa atividade não pode ser removido pois está veinculada a colaboradores e/ou artefatos");
            RequestContext.getCurrentInstance().showMessageInDialog(message);     
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
    
    public Usuario retornaUsuario(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return user;
    }
    
    public String encerrarSecao(){
        //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        return "../index.xhtml?faces-redirect=true";
    }
  
}
