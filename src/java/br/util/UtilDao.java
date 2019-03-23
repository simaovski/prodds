/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.util;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.text.Document;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author padrao
 */
public class UtilDao implements Serializable{
    
    public UtilDao(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public boolean verificaGerente(Integer id) {
//        GerenteJpaController daoGerente = new GerenteJpaController(emf);
//        try {
//            List<Gerente> g = daoGerente.findGerenteEntities();
//            for(int i = 0; i < g.size(); i++){
//                int a = g.get(i).getIdusuariogerente().getId();
//                if(a == id){
//                    return true;
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Erro <UtilDAO>: " + e.toString());
//        }
        return false;
    }
    
    public static EntityManagerFactory retornaEntity(){
        return Persistence.createEntityManagerFactory("TCCBackupPU");
    }
}
