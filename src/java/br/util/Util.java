/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.util;

import data.crud.Gerente;
import data.crud.Usuario;
import data.dao.GerenteJpaController;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author padrao
 */
public class Util {
    public boolean permissao(){
        
        try {
            Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            if(user.getFuncaousuario() == 2){
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
                UtilDao daoUtil = new UtilDao(emf);
                
                if(daoUtil.verificaGerente(user.getId()) == true){//é gerente
                    return true;
                }
                return false;
            } 
        }catch (Exception e) {
             System.out.println("Erro: utilPermissao, método Permissao: " + e.toString());
        }
        return false;
        
    }
    
    public boolean permissaoMem(){
        
        try {
            Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            if(user.getFuncaousuario() == 2){
                //System.out.println("User");
                return true;
            } 
        }catch (Exception e) {
             System.out.println("Erro: " + e.toString());
        }
        System.out.println("Not user");
        return false;
        
    }
    
    public boolean permissaoAdm(){
        
        try {
            Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            if(user.getFuncaousuario() == 1){
                return true;
            }
        }catch (Exception e) {
             System.out.println("Erro: " + e.toString());
        }
        return false;
        
    }
    
    public boolean permissaoGer(){
        try {
            Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
            GerenteJpaController daoGer = new GerenteJpaController(emf);
            List<Gerente> listaGer = daoGer.findGerenteEntities();
            
            for(int i = 0; i < listaGer.size(); i++){
                int a = listaGer.get(i).getIdusuariogerente().getId();
                int b = user.getId();
                System.out.println("a: " + a + " b: " + b);
                if(a == b){
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Erro util, método permissaoGer> " + e.toString());
        }
        return false;
    }
    
    public String direcionaUrl(){
        try {
            Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");           
            
            if(user.getFuncaousuario() == 2){// gerente ou membro
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
                UtilDao daoUtil = new UtilDao(emf);
                
                if(daoUtil.verificaGerente(user.getId()) == true){
                    return "../pgPessoalGer.xhtml?faces-redirect=true";
                }
                return "../pgPessoalMem.xhtml?faces-redirect=true";
            }else if(user.getFuncaousuario() == 1){//Pg pessoal admin
                return "../pgPessoalAdm.xhtml?faces-redirect=true";
            } 
            
        }catch (Exception e) {
             System.out.println("Erro: classe Util, método direcionaUrl: " + e.toString());
        }
        return "../index.xhtml?faces-redirect=true";
    }
   
   public boolean logado(){
       try {
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");           
        if(user.getFuncaousuario() == 2 || user.getFuncaousuario() == 1)
           return true;
        } catch (Exception e) {
           System.out.println("Erro, Classe util, método logado: " + e.toString());
        }
        return false;
   }
}
