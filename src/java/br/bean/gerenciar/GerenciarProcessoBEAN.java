/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean.gerenciar;

import br.util.UtilDao;
import data.crud.Fasesprocesso;
import data.crud.Processo;
import data.dao.FasesprocessoJpaController;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author padrao
 */
@ManagedBean
@RequestScoped
public class GerenciarProcessoBEAN {

    /**
     * Creates a new instance of GerenciarProcessoBEAN
     */
    public GerenciarProcessoBEAN() {
    }
    
    private List<Fasesprocesso> listaFases;
    private String nomeFase;
    private String descricaoFase;

    public List<Fasesprocesso> getListaFases() {
        return listaFases;
    }

    public void setListaFases(List<Fasesprocesso> listaFases) {
        this.listaFases = listaFases;
    }

    public String getNomeFase() {
        return nomeFase;
    }

    public void setNomeFase(String nomeFase) {
        this.nomeFase = nomeFase;
    }

    public String getDescricaoFase() {
        return descricaoFase;
    }

    public void setDescricaoFase(String descricaoFase) {
        this.descricaoFase = descricaoFase;
    }
    
    
    
    public List<Fasesprocesso> carregaTabela(Processo p){
        listaFases = new ArrayList<Fasesprocesso>();
        EntityManagerFactory emf = UtilDao.retornaEntity();
        FasesprocessoJpaController daoFase = new FasesprocessoJpaController(emf);
        List<Fasesprocesso> aux = daoFase.findFasesprocessoEntities();
        for(Fasesprocesso f : aux){
            int a = p.getId();
            int b = f.getIdprocesso().getId();
            if(a == b){
               listaFases.add(f);
            }
            
        }        
        emf.close();
        return listaFases;
    }
    
    public void selecionarFase(Fasesprocesso fase){
        nomeFase = fase.getNomefase();
        descricaoFase = fase.getDescricaofase();
    }
    
}
