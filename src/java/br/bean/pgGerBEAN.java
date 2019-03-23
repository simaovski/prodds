/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean;

import br.util.Util;
import data.crud.Artefato;
import data.crud.Atividademembroequipe;
import data.crud.Atividadesfaseprocesso;
import data.crud.Equipe;
import data.crud.Projeto;
import data.crud.Usuario;
import data.dao.ArtefatoJpaController;
import data.dao.AtividadeJpaController;
import data.dao.AtividadesfaseprocessoJpaController;
import data.dao.EquipeJpaController;
import data.dao.LogusuarioJpaController;
import data.dao.ProjetoJpaController;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author padrao
 */
@ManagedBean
@RequestScoped
public class pgGerBEAN {

    private List<Equipe> listaEquipe;
    private List<Artefato> listaArtefatos;
    
    public pgGerBEAN() {
    }

    public List<Equipe> getListaEquipe() {
        return listaEquipe;
    }

    public void setListaEquipe(List<Equipe> listaEquipe) {
        this.listaEquipe = listaEquipe;
    }
    
    private Projeto p;

    @PostConstruct
    public void carregaInfo(){
        int idProjeto = (int)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("projetoGerente");
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        ProjetoJpaController daoProj = new ProjetoJpaController(emf);
        p = daoProj.findProjeto(idProjeto);
        
        EquipeJpaController daoEqp = new EquipeJpaController(emf);
        List<Equipe> aux = daoEqp.findEquipeEntities();
        listaEquipe = new ArrayList<Equipe>();
        
        for(Equipe e : aux){
            int a = e.getIdprojeto().getId();
            int b = idProjeto;
            
            if(a == b){
                listaEquipe.add(e);
            }
        }
        
        ArtefatoJpaController daoArt = new ArtefatoJpaController(emf);
        List<Artefato> auxArt = daoArt.findArtefatoEntities();
        listaArtefatos = new ArrayList<Artefato>();
        
        for(Artefato art : auxArt){
            int a = art.getIdatividade().getIdequipe().getIdprojeto().getId();
            
            if(a == idProjeto){
                listaArtefatos.add(art);
            }
        }
        
    }
    
    public Projeto getP() {
        return p;
    }

    public void setP(Projeto p) {
        this.p = p;
    }

    public List<Artefato> getListaArtefatos() {
        return listaArtefatos;
    }

    public void setListaArtefatos(List<Artefato> listaArtefatos) {
        this.listaArtefatos = listaArtefatos;
    }
       
    
    public String solicitarGerencia(){
        return "CADs/solicitarGerencia.xhtml?faces-redirect=true";
    }
    
    public String encerrarSecao(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        return "index.xhtml?faces-redirect=true";
    }
    
    public boolean permissao(){
        Util u = new Util();
        return u.permissaoMem();
    }
    
    public Usuario retornaUsuario(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return user;
    }
    
    public String nomeAtividade(Artefato art){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        Atividademembroequipe atv = art.getIdatividade();
        if(atv.getIdfuncao() == 0){
            AtividadesfaseprocessoJpaController dao = new AtividadesfaseprocessoJpaController(emf);
            return dao.findAtividadesfaseprocesso(atv.getIdatividade()).getNomeatividade();
        }
        AtividadeJpaController dao = new AtividadeJpaController(emf);
        return dao.findAtividade(atv.getIdatividade()).getNomeatividade();
    }
    
    public File download(Artefato art){
        File f = null;
        try {
            //converte o array de bytes em file
            f = new File("C:\\Users\\padrao\\Documents\\UTFPR\\TCC\\Downloads\\" + art.getNome());
            System.out.println(f.getAbsolutePath());
            byte [] bytes = art.getArquivo();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bytes);
            return f;
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.toString());
        }
            return null;
    }
}
