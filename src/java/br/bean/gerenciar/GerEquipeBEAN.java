/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean.gerenciar;

import br.util.Util;
import br.util.UtilDao;
import data.crud.Atividade;
import data.crud.Atividadesequipe;
import data.crud.Atividadesequipegerente;
import data.crud.Atividadesfaseprocesso;
import data.crud.Equipe;
import data.crud.Localizacao;
import data.crud.Projeto;
import data.crud.Usuario;
import data.dao.AtividadeJpaController;
import data.dao.AtividadesequipeJpaController;
import data.dao.AtividadesequipegerenteJpaController;
import data.dao.AtividadesfaseprocessoJpaController;
import data.dao.EquipeJpaController;
import data.dao.LocalizacaoJpaController;
import data.dao.ProjetoJpaController;
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
public class GerEquipeBEAN {

    private Projeto proj;
    private Equipe eqp;
    private Localizacao loc;
    private List<Atividadesequipe> listaSel;
    private List<Atividadesequipegerente> listaSelGer;
    private List<Atividadesfaseprocesso> lista;
    private List<Atividade> listaGer;
    private int[] atvSel;
    private int[] atvSelGer;
    
    
    public GerEquipeBEAN() {
    }

    public Projeto getProj() {
        return proj;
    }

    public void setProj(Projeto proj) {
        this.proj = proj;
    }

    public Equipe getEqp() {
        return eqp;
    }

    public void setEqp(Equipe eqp) {
        this.eqp = eqp;
    }

    public Localizacao getLoc() {
        return loc;
    }

    public void setLoc(Localizacao loc) {
        this.loc = loc;
    }

    public List<Atividadesequipe> getListaSel() {
        return listaSel;
    }

    public void setListaSel(List<Atividadesequipe> listaSel) {
        this.listaSel = listaSel;
    }

    public List<Atividadesequipegerente> getListaSelGer() {
        return listaSelGer;
    }

    public void setListaSelGer(List<Atividadesequipegerente> listaSelGer) {
        this.listaSelGer = listaSelGer;
    }

    public List<Atividadesfaseprocesso> getLista() {
        return lista;
    }

    public void setLista(List<Atividadesfaseprocesso> lista) {
        this.lista = lista;
    }

    public List<Atividade> getListaGer() {
        return listaGer;
    }

    public void setListaGer(List<Atividade> listaGer) {
        this.listaGer = listaGer;
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
    
    @PostConstruct
    public void carregaInfo(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        int idProjeto = (int)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("projetoGerente");
        EntityManagerFactory emf = UtilDao.retornaEntity();
        ProjetoJpaController daoProj = new ProjetoJpaController(emf);
        LocalizacaoJpaController daoEqp = new LocalizacaoJpaController(emf);
        AtividadesequipeJpaController daoAtv = new AtividadesequipeJpaController(emf);
        AtividadesequipegerenteJpaController daoAtvGer = new AtividadesequipegerenteJpaController(emf);
        AtividadesfaseprocessoJpaController daoAtvCadAdm = new AtividadesfaseprocessoJpaController(emf);
        AtividadeJpaController daoAtvCadGer = new AtividadeJpaController(emf);
        
        proj = daoProj.findProjeto(idProjeto);
        
        listaSel = new ArrayList<Atividadesequipe>();
        listaSelGer = new ArrayList<Atividadesequipegerente>();
        for(Equipe e : proj.getEquipeCollection()){
            int a = e.getIdgerente().getId();
            int b = user.getId();

            if(a == b){
                eqp = e;
                loc = daoEqp.retornaLocEqp(e);
                listaSel =  daoAtv.retornaAtvEqp(e);
                listaSelGer = daoAtvGer.retornaAtvEqp(e);
            }
        }
        
        lista = daoAtvCadAdm.retornaAtividadePorAdm(proj.getIdadm(), proj.getIdprocesso());
        listaGer = daoAtvCadGer.retornaAtividadePorGerente(user, proj);
        
        atvSel = new int[lista.size()];
        for(int i = 0; i < listaSel.size(); i++){
            if(lista.contains(listaSel.get(i).getIdatividade())){
                int a = listaSel.get(i).getIdatividade().getId();
                atvSel[i] = a;
            }
        }
        
        
        atvSelGer = new int[listaGer.size()];
        for(int i = 0; i < listaSelGer.size(); i++){
            if(listaGer.contains(listaSelGer.get(i).getIdatividade())){
                int a = listaSelGer.get(i).getIdatividade().getId();
                atvSelGer[i] = a;
            }
        }
        emf.close();
    }
    
    public boolean verificarEquipe(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        EquipeJpaController daoEquipe = new EquipeJpaController(emf);
        List<Equipe> eqp1 = daoEquipe.findEquipeEntities();
        for(Equipe e : eqp1){
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
    
    public Usuario retornaUsuario(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return user;
    }
    
    public void alterar(){
        EntityManagerFactory emf = UtilDao.retornaEntity();
        EquipeJpaController daoEqp = new EquipeJpaController(emf);
        
        try {
            if(daoEqp.verificaNomeEquipe(eqp, proj) == true){
                daoEqp.edit(eqp);

                LocalizacaoJpaController daoLoc = new LocalizacaoJpaController(emf);
                if(loc.getId() == null){
                    loc.setIdgerente(retornaUsuario());
                    loc.setIdequipe(eqp.getId());
                    daoLoc.create(loc);
                }else
                    daoLoc.edit(loc);

                AtividadesequipeJpaController daoAtv = new AtividadesequipeJpaController(emf);
                daoAtv.salvarOuRemover(eqp, atvSel);

                AtividadesequipegerenteJpaController daoAtvGer = new AtividadesequipegerenteJpaController(emf);
                daoAtvGer.salvarOuRemover(eqp, atvSelGer);
                
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Alterações realizadas com sucesso");
                RequestContext.getCurrentInstance().showMessageInDialog(message); 
            }else{
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Já existe uma equipe com este nome");
                RequestContext.getCurrentInstance().showMessageInDialog(message);  
            }
        } catch (Exception e) {
            System.out.println("Erro, classe gerEquipeBean, método alterar: " + e.toString());
        }
        emf.close();
    }
    
    public String remover(){
        EntityManagerFactory emf = UtilDao.retornaEntity();
        EquipeJpaController daoEqp = new EquipeJpaController(emf);
        try {
            System.out.println("Equipe: " + eqp.getId());
            daoEqp.destroy(eqp.getId());
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "A equipe esta veinculado à Atividades/ Colaboradores/ Artefatos");
            RequestContext.getCurrentInstance().showMessageInDialog(message); 
            System.out.println("Erro, classe gerEquipeBEAN, método remover: " + e.toString());
        }
        emf.close();
        return "../pgPessoalGer.xhtml?faces-redirect=true";
    }
    
}
