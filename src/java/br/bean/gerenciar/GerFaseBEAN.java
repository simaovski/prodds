/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean.gerenciar;

import br.util.Util;
import br.util.UtilDao;
import data.crud.Fasesprocesso;
import data.crud.Processo;
import data.crud.Usuario;
import data.dao.FasesprocessoJpaController;
import data.dao.ProcessoJpaController;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.persistence.EntityManagerFactory;
import org.eclipse.persistence.sessions.Project;
import org.primefaces.context.RequestContext;

/**
 *
 * @author padrao
 */
@ManagedBean
@RequestScoped
public class GerFaseBEAN {

    private List<SelectItem> lista = new ArrayList<SelectItem>();
    private Fasesprocesso fase = new Fasesprocesso();
    private Object faseId = new Object();
    private Object teste = new Object();
    public GerFaseBEAN() {
    }
    
    public List<SelectItem> getLista() {
        return lista;
    }

    public Fasesprocesso getFase() {
        return fase;
    }

    public void setFase(Fasesprocesso fase) {
        this.fase = fase;
    }

    public Object getFaseId() {
        return faseId;
    }

    public void setFaseId(Object faseId) {
        this.faseId = faseId;
    }

    

    public Object getTeste() {
        return teste;
    }

    public void setTeste(Object teste) {
        this.teste = teste;
    }
    
    
    
    
    @PostConstruct
    public void carregar(){
        Usuario user = retornaUsuario();
        EntityManagerFactory emf = UtilDao.retornaEntity();
        
        ProcessoJpaController daoProd = new ProcessoJpaController(emf);
        List<Processo> listaProc = daoProd.findProcessoEntities();
        for(Processo p : listaProc){
            int a = p.getIdusuario().getId();
            int b = user.getId();
            if(a == b){
                SelectItemGroup proj = new SelectItemGroup(p.getNomeprocesso());
                proj.setLabel(p.getNomeprocesso());
                proj.setDescription(p.getDescricao());
                proj.setValue(p);
                
                FasesprocessoJpaController daoFase = new FasesprocessoJpaController(emf);
                List<Fasesprocesso> aux = daoFase.findFasesprocessoEntities();
                List<SelectItem> fasesProcesso = new ArrayList<SelectItem>();
                for(Fasesprocesso f : aux){
                    int c = f.getIdprocesso().getId();
                    int d = p.getId();
                    if(c == d){
                        fasesProcesso.add(new SelectItem(f.getId(), f.getNomefase(), f.getDescricaofase()));
                    }
                }
                SelectItem vetorFases[] = new SelectItem[fasesProcesso.size()];
                for(int i = 0; i < vetorFases.length; i++)
                    vetorFases[i] = fasesProcesso.get(i);
                proj.setSelectItems(vetorFases);
                lista.add(proj);
            }
        }
        
        emf.close();
    }
    
    public void selecionar(){
        String id = (String) faseId;
        EntityManagerFactory emf = UtilDao.retornaEntity();
        FasesprocessoJpaController daoFase = new FasesprocessoJpaController(emf);
        fase = daoFase.findFasesprocesso(Integer.parseInt(id));
        emf.close();
    }
    
    public void alterar(){
        String id = (String) faseId;
        EntityManagerFactory emf = UtilDao.retornaEntity();
        FasesprocessoJpaController daoFase = new FasesprocessoJpaController(emf);
        try {
            daoFase.editar(fase);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "A Fase foi alterada com Sucesso");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } catch (Exception e) {
            System.out.println("Erro, classse GerFaseBEAN, método alterar: " + e.toString());
        }
        emf.close();
    }
    
    
    public boolean permissao(){
        Util u = new Util();
        return u.permissaoAdm();
    }
    
    public String encerrarSecao(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        return "index.xhtml?faces-redirect=true";
    }
    
    public Usuario retornaUsuario(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return user;
    }
}
