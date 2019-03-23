/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean;


import data.dao.UsuarioJpaController;
import data.crud.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.primefaces.context.RequestContext;

/**
 *
 * @author padrao
 */
@ManagedBean
@RequestScoped
public class UsuarioBEAN {

    private int id;
    private String nomeUsuario;
    private String emailUsuario;
    private String senhaUsuario;
    private int idFuncao;
    private List<SelectItem> equipes;
    private List<SelectItem> idEquipes;
    private List<SelectItem> projetos;
    private List<SelectItem> idProjetos;
    private String selection;
    private String msg;
    
    private List<SelectItem> combo;
    private List<SelectItem> idCombo;

    public List<SelectItem> getCombo() {
        return combo;
    }

    public void setCombo(List<SelectItem> combo) {
        this.combo = combo;
    }

    public List<SelectItem> getIdCombo() {
        return idCombo;
    }

    public void setIdCombo(List<SelectItem> idCombo) {
        this.idCombo = idCombo;
    }
    
    public UsuarioBEAN() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {   
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public int getIdFuncao() {
        return idFuncao;
    }

    public void setIdFuncao(int idFuncao) {
        this.idFuncao = idFuncao;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senha) {
        this.senhaUsuario = senha;
    }

    public String getMsg() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro de e-mail", "E-mail já cadastrado");
         
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    
    
    public String salvar(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        UsuarioJpaController daoUsuario = new UsuarioJpaController(emf);
        
        if(daoUsuario.verificaEmail(emailUsuario) == true){
            Usuario u = new Usuario();

            u.setNomeusuario(nomeUsuario);
            u.setEmailusuario(emailUsuario);
            u.setFuncaousuario(idFuncao);
            u.setSenha(senhaUsuario);

            daoUsuario.create(u);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", daoUsuario.findUsuario(u.getId()));
            if(idFuncao == 1){
                return "pgPessoalAdm.xhtml?faces-redirect=true";
            }

            return "pgPessoalMem.xhtml?faces-redirect=true"; 
        }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro de e-mail", "E-mail já cadastrado");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return "";
        }
    }
        
    public void limpar(){
        nomeUsuario = "";
        senhaUsuario = "";
        emailUsuario = "";
    }
    
 
    public String getSelection() {
        return selection;
    }
    public void setSelection(String selection) {
        this.selection = selection;
    }

    public List<SelectItem> getEquipes() {
        return equipes;
    }

    public void setEquipes(List<SelectItem> equipes) {
        this.equipes = equipes;
    }

    public List<SelectItem> getIdEquipes() {
        return idEquipes;
    }

    public void setIdEquipes(List<SelectItem> idEquipes) {
        this.idEquipes = idEquipes;
    }

    public List<SelectItem> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<SelectItem> projetos) {
        this.projetos = projetos;
    }

    public List<SelectItem> getIdProjetos() {
        return idProjetos;
    }

    public void setIdProjetos(List<SelectItem> idProjetos) {
        this.idProjetos = idProjetos;
    }

    
    
    
    
}
