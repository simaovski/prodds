/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean;

import br.util.Util;
import data.crud.Usuario;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author padrao
 */
@ManagedBean
@RequestScoped
public class cadGerenteProjBEAN {

    /**
     * Creates a new instance of cadGerenteProjBEAN
     */
    public cadGerenteProjBEAN() {
    }
    
    public boolean permissao(){
        Util u = new Util();
        return u.permissaoAdm();
    }
    
    public String encerrarSecao(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("projetoAdm");
        return "index.xhtml?faces-redirect=true";
    }
    
    public Usuario retornaUsuario(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return user;
    }
    
    @PostConstruct
    public void carregaTabela(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        
    }
    
}
