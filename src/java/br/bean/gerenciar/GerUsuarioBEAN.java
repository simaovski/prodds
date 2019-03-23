/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean.gerenciar;

import br.util.Util;
import br.util.UtilDao;
import data.crud.Usuario;
import data.dao.UsuarioJpaController;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import org.primefaces.context.RequestContext;

/**
 *
 * @author padrao
 */
@ManagedBean
@RequestScoped
public class GerUsuarioBEAN {

    private Usuario usuario;
    private String nome;
    private String email;
    private String senha;
    private String newPass;
    
    public GerUsuarioBEAN() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
    
    public String encerrarSecao(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("projetoAdm");
        return "../index.xhtml?faces-redirect=true";
    }
    
    public Usuario retornaUsuario(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        nome = user.getNomeusuario();
        email = user.getEmailusuario();
        return user;
    }
    
    public boolean permissao(){
        Util u = new Util();
        return u.logado();
    }
    
    public String redirecionaUrl(){
        Util u = new Util();
        return u.direcionaUrl();
    }
    
    public void alterar(){
        EntityManagerFactory emf = UtilDao.retornaEntity();
        Usuario u = new Usuario();
        u.setId(retornaUsuario().getId());
        u.setNomeusuario(nome);
        u.setEmailusuario(retornaUsuario().getEmailusuario());
        u.setFuncaousuario(retornaUsuario().getFuncaousuario());
        u.setSenha(retornaUsuario().getSenha());
        if(!newPass.isEmpty())
            u.setSenha(newPass);
        String oldPass = retornaUsuario().getSenha();
        if(oldPass.equals(senha)){
            UsuarioJpaController daoUser = new UsuarioJpaController(emf);
            try {
                daoUser.edit(u);
                //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
                //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("projetoAdm");
               //return "../index.xhtml?faces-redirect=true";
            } catch (Exception e) {
                System.out.println("Erro, classe GerUsuarioBEAN, método alterar: " + e.toString());
            }        
        }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Senha inválida!");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }     
        emf.close();
        //return "/ALTs/altUsuario.xhtml?faces-redirect=true";
        
    }
    
}
