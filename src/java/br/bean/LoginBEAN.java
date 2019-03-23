/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean;

import data.dao.UsuarioJpaController;
import data.crud.Usuario;
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
public class LoginBEAN {

    private String nomeUsuario;
    private String senhaUsuario;
    private int idUser;
    
    public LoginBEAN() {
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    
    
    
    public String salvar(){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        UsuarioJpaController daoUsuario = new UsuarioJpaController(emf);  
        
        idUser = daoUsuario.autenticarUsuario(nomeUsuario, senhaUsuario);
        //System.out.println(idUser);
        if(idUser > 0){
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", daoUsuario.findUsuario(idUser));
            Usuario u = daoUsuario.findUsuario(idUser);
            if(u.getFuncaousuario() == 1){
                return "pgPessoalAdm.xhtml?faces-redirect=true";
            }
            
            return "pgPessoalMem.xhtml?faces-redirect=true";
        }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Senha e/ou e-mail inválido(s)");
            RequestContext.getCurrentInstance().showMessageInDialog(message);        
            return "";
        }
    }
    
}
