/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean;

import br.util.ObjectAux;
import br.util.Util;
import br.util.UtilDao;
import data.crud.Atividade;
import data.crud.Atividademembroequipe;
import data.crud.Atividadesequipe;
import data.crud.Atividadesfaseprocesso;
import data.crud.Equipe;
import data.crud.Fasesprocesso;
import data.crud.Gerente;
import data.crud.Gerenteprocesso;
import data.crud.Gerenteprojeto;
import data.crud.Membrosequipe;
import data.crud.Processo;
import data.crud.Projeto;
import data.crud.Statusatividade;
import data.crud.Statusprojeto;
import data.crud.Usuario;
import data.dao.AtividadeJpaController;
import data.dao.AtividademembroequipeJpaController;
import data.dao.AtividadesequipeJpaController;
import data.dao.AtividadesfaseprocessoJpaController;
import data.dao.EquipeJpaController;
import data.dao.GerenteJpaController;
import data.dao.GerenteprocessoJpaController;
import data.dao.MembrosequipeJpaController;
import data.dao.ProcessoJpaController;
import data.dao.ProjetoJpaController;
import data.dao.StatusatividadeJpaController;
import data.dao.StatusprojetoJpaController;
import data.dao.UsuarioJpaController;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.primefaces.context.RequestContext;

/**
 *
 * @author padrao
 */
@ManagedBean
@RequestScoped
public class RelatorioAdmBEAN {

    private Projeto p;
    private List<Gerente> listaGer;
    private Collection<Fasesprocesso> listaFases;
    private List<Atividadesfaseprocesso> listaAtividades;
    private List<Atividade> listaAtividadesGerente;
    private List<Atividademembroequipe> listaAtvDes;
    private int idGerente;
    private int estado;
    private int ger;
    
    
    public RelatorioAdmBEAN() {
    }

    public Projeto getP() {
        return p;
    }

    public void setP(Projeto p) {
        this.p = p;
    }

    public List<Gerente> getListaGer() {
        return listaGer;
    }

    public void setListaGer(List<Gerente> listaGer) {
        this.listaGer = listaGer;
    }

    public Collection<Fasesprocesso> getListaFases() {
        return listaFases;
    }

    public List<Atividadesfaseprocesso> getListaAtividades() {
        return listaAtividades;
    }

    public List<Atividade> getListaAtividadesGerente() {
        return listaAtividadesGerente;
    }

    public List<Atividademembroequipe> getListaAtvDes() {
        return listaAtvDes;
    }

    public void setListaAtvDes(List<Atividademembroequipe> listaAtvDes) {
        this.listaAtvDes = listaAtvDes;
    }

    public void setIdGerente(int idGerente) {
        this.idGerente = idGerente;
    }    
    
    public int getIdGerente() {
        return idGerente;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getGer() {
        return ger;
    }

    public void setGer(int ger) {
        this.ger = ger;
    }
    
    
    

    @PostConstruct
    public void carregaTabela(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        p = (Projeto)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("projetoAdm");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        GerenteJpaController daoGer = new GerenteJpaController(emf);
        List<Gerente> lista = daoGer.findGerenteEntities();
        listaGer = new ArrayList<Gerente>();
        for(Gerente g : lista){
            int a = g.getProjeto().getId();
            int b = p.getId();
            if(a == b){
                listaGer.add(g);
            }
        }
        
        AtividademembroequipeJpaController daoAtvDes = new AtividademembroequipeJpaController(emf);
        List<Atividademembroequipe> listaAuxDes = daoAtvDes.findAtividademembroequipeEntities();
        listaAtvDes = new ArrayList<Atividademembroequipe>();
        for(Atividademembroequipe atvDes : listaAuxDes){
            int a = atvDes.getIdequipe().getIdprojeto().getId();
            int b = p.getId();
            if(a  == b){
                listaAtvDes.add(atvDes);
            }
        }
        
        listaFases = p.getIdprocesso().getFasesprocessoCollection();
        listaAtividades = new ArrayList<Atividadesfaseprocesso>();
        listaAtividadesGerente = new ArrayList<Atividade>();
        //Carrega atividades cadastradas pelo ADM e pelos GERs
        for(Fasesprocesso f : listaFases){
            Collection<Atividadesfaseprocesso> aux = f.getAtividadesfaseprocessoCollection();
            Collection<Atividade> auxGer = f.getAtividadeCollection();
            for(Atividadesfaseprocesso a : aux)
                listaAtividades.add(a);
            for(Atividade a : auxGer)
                listaAtividadesGerente.add(a);
        }
        //Remove as atividades que são utilizadas
        for(Atividademembroequipe atvDes : listaAuxDes){
            if(atvDes.getIdfuncao() == 0){
                AtividadesfaseprocessoJpaController dao = new AtividadesfaseprocessoJpaController(emf);
                Atividadesfaseprocesso atv = dao.findAtividadesfaseprocesso(atvDes.getIdatividade());
                if(listaAtividades.contains(atv))
                    listaAtividades.remove(atv);
            }else{
                AtividadeJpaController dao = new AtividadeJpaController(emf);
                Atividade atv = dao.findAtividade(atvDes.getIdatividade());
                if(listaAtividadesGerente.contains(atv))
                    listaAtividadesGerente.remove(atv);
            }
        }
        
        Collection<Gerenteprocesso> gerP = p.getGerenteprocessoCollection();
        if(gerP.isEmpty()){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "O Projeto não pode ser iniciado, cadastre um Gerente de Processo");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
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
    
    public int diasTrabalhados(){
        try {
            String dia = p.getDatacriacao().substring(0, p.getDatacriacao().indexOf('/'));
            String mes = p.getDatacriacao().substring(p.getDatacriacao().indexOf('/')+1);
            String ano = mes.substring(p.getDatacriacao().indexOf('/'), mes.length());
            mes = mes.substring(0, p.getDatacriacao().indexOf('/')-1);
            GregorianCalendar DataInicial = new GregorianCalendar (Integer.parseInt(ano), Integer.parseInt(mes), Integer.parseInt(dia)); ; 
            GregorianCalendar DataFinal = new GregorianCalendar();
            //System.out.println("Dia: " + dia + " mes: " + mes + " dia: " + dia);
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
            StatusprojetoJpaController daoStatus = new StatusprojetoJpaController(emf);
            List<Statusprojeto> listaStatus = daoStatus.findStatusprojetoEntities();
            for(Statusprojeto l : listaStatus){
                int a = l.getIdprojeto();
                int b = p.getId();
                
                if(a == b){
                    if(l.getDatafinal() == null){
                        Date d = new Date();
                        DataFinal = new GregorianCalendar ((d.getYear()+1900), (d.getMonth()+1), d.getDate());
                        break;
                    }else{
                        String d = l.getDatafinal();
                        
                        System.out.println(d);
                        dia = d.substring(0, d.indexOf('/'));
                        System.out.println("Dia: " + dia);
                        mes = d.substring(d.indexOf('/')+1);
                        ano = mes.substring(d.indexOf('/'), mes.length());
                        mes = mes.substring(0, d.indexOf('/')-1);
                        
                        System.out.println("Mes: " + mes);
                        System.out.println("Ano: "+ ano);
                        DataFinal = new GregorianCalendar (Integer.parseInt(ano), Integer.parseInt(mes), Integer.parseInt(dia)); ; 
                        break;
                    }
                }
            }
            long millisElapsed = (DataFinal.getTimeInMillis() - DataInicial.getTimeInMillis());
            int dayElapsed = (int) (millisElapsed / (24 * 60 * 60 * 1000));
            return dayElapsed+1;
        } catch (Exception e) {
            System.out.println("Erro relatorioAdmBean, metodo diasTrabalhados: " + e.toString());
        }            
        return 0;
    }
    
    public String retornaDataFinal(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        StatusprojetoJpaController daoStatus = new  StatusprojetoJpaController(emf);
        List<Statusprojeto> lista = daoStatus.findStatusprojetoEntities();
        for(Statusprojeto s : lista){
            int a = s.getIdprojeto();
            int b = p.getId();
            if(a == b){
                if(s.getStatus() == false){
                    Date d = new Date();
                    return "-";
                    //return d.getDate() + "/" + (d.getMonth() + 1) + "/" + (d.getYear()+1900) + " (Hoje)";
                }
                return s.getDatafinal();
            }
        }
        return "";
    }
    
    public String totalGerentes(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        GerenteJpaController daoGer = new GerenteJpaController(emf);
        List<Gerente> lista = daoGer.findGerenteEntities();
        int cont = 0;
        for(Gerente g : lista){
            int a = g.getProjeto().getId();
            int b = p.getId();
            if(a == b){
                cont++;
            }
        }
        return cont + "";
    }
    
    public String retornaStatus(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        StatusprojetoJpaController daoStatus = new  StatusprojetoJpaController(emf);
        List<Statusprojeto> lista = daoStatus.findStatusprojetoEntities();
        for(Statusprojeto s : lista){
            int a = s.getIdprojeto();
            int b = p.getId();
            if(a == b){
                if(s.getStatus() == false){
                    return "Em Desenvolvimento";
                }
                return "Concluído";
            }
        }
        return "";
    }
    
    public String retornaEquipe(Gerente g){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        EquipeJpaController daoEqp = new EquipeJpaController(emf);
        List<Equipe> lista = daoEqp.findEquipeEntities();
        for(Equipe e : lista){
            int a = e.getIdprojeto().getId();
            int b = p.getId();
            
            int c = g.getIdusuariogerente().getId();
            int d = e.getIdgerente().getId();
            
            if(a == b && c == d){
                return e.getNomeequipe();
            }
        }
        return "O Gerente " + g.getIdusuariogerente().getNomeusuario() + " ainda não cadastrou sua equipe";
    }
    
    public String retornaDescricao(Gerente g){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        EquipeJpaController daoEqp = new EquipeJpaController(emf);
        List<Equipe> lista = daoEqp.findEquipeEntities();
        for(Equipe e : lista){
            int a = e.getIdprojeto().getId();
            int b = p.getId();
            
            int c = g.getIdusuariogerente().getId();
            int d = e.getIdgerente().getId();
            
            if(a == b && c == d){
                return e.getDescricao();
            }
        }
        return "-";
    }
    
    public String atividades(Gerente g){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        EquipeJpaController daoEqp = new EquipeJpaController(emf);
        List<Equipe> lista = daoEqp.findEquipeEntities();
        String atividades = "  ";
        Collection<Atividadesequipe> atividadesEqp;
        for(Equipe e : lista){
            int a = e.getIdprojeto().getId();
            int b = p.getId();
            
            int c = g.getIdusuariogerente().getId();
            int d = e.getIdgerente().getId();
            
            if(a == b && c == d){
                atividadesEqp = e.getAtividadesequipeCollection();
                for(Atividadesequipe atv : atividadesEqp){
                   atividades += atv.getIdatividade().getNomeatividade() + ", ";
                }
            }
        }
        return atividades.substring(0, atividades.length()-2);
    }
    
    public String retornaAtividade(Atividademembroequipe atv){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        if(atv.getIdfuncao() == 0){
            AtividadesfaseprocessoJpaController dao = new AtividadesfaseprocessoJpaController(emf);
            return dao.findAtividadesfaseprocesso(atv.getIdatividade()).getNomeatividade();
        }else{
            AtividadeJpaController dao = new AtividadeJpaController(emf);
            return dao.findAtividade(atv.getIdatividade()).getNomeatividade();
        }
    }
    
    public String retornaFase(Atividademembroequipe atv){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        if(atv.getIdfuncao() == 0){
            AtividadesfaseprocessoJpaController dao = new AtividadesfaseprocessoJpaController(emf);
            return dao.findAtividadesfaseprocesso(atv.getIdatividade()).getIdfase().getNomefase();
        }else{
            AtividadeJpaController dao = new AtividadeJpaController(emf);
            return dao.findAtividade(atv.getIdatividade()).getIdfase().getNomefase();
        }
    }
    
    public String retornaStatus(Atividademembroequipe atv){
        if(atv.getStatus() == false)
            return "Desenvolvimento";
        return "Concluído";
    }
    
    
    public int retornaEstado(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        StatusprojetoJpaController daoStatus = new StatusprojetoJpaController(emf);            
        return daoStatus.retornaStatus(p.getId());
    }
    
    public void editarStatus(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        StatusprojetoJpaController daoStatus = new StatusprojetoJpaController(emf);
        Statusprojeto sp = daoStatus.retornaStatusprojeto(p);
        try {
            System.out.println(estado);;
            //daoStatus.edit(sp);
            if(estado == 4){
                ProjetoJpaController daoProj = new ProjetoJpaController(emf);
                Projeto auxProj = p;
                
                auxProj.setNomeprojeto(p.getNomeprojeto() + " - v. ?");
                daoProj.create(p);
                
                Collection<Gerente> auxGer =  p.getGerenteCollection();
                GerenteJpaController daoGer = new GerenteJpaController(emf);
                for(Gerente g : auxGer){
                    daoGer.create(g);
                }
                
                Collection<Equipe> auxEqp = p.getEquipeCollection();
                EquipeJpaController daoEqp = new EquipeJpaController(emf);
                for(Equipe e : auxEqp){
                    daoEqp.create(e);
                    
                    MembrosequipeJpaController daoMem = new MembrosequipeJpaController(emf);
                    Collection<Membrosequipe> auxMem = e.getMembrosequipeCollection();
                    for(Membrosequipe m : auxMem)
                        daoMem.create(m);
                    
                    AtividademembroequipeJpaController daoAtv = new AtividademembroequipeJpaController(emf);
                    Collection<Atividademembroequipe> auxAtv = e.getAtividademembroequipeCollection();
                    for(Atividademembroequipe a : auxAtv)
                        daoAtv.create(a);
               
                    AtividadesequipeJpaController daoAtvEqp = new AtividadesequipeJpaController(emf);
                    Collection<Atividadesequipe> auxAtvEqp = e.getAtividadesequipeCollection();
                    for(Atividadesequipe a : auxAtvEqp)
                        daoAtvEqp.create(a);                 
                }
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "O Projeto foi reiniciado com o nome de: " + p.getNomeprojeto());
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }
        } catch (Exception e) {
            System.out.println("Erro, classe relatorioAdmBEAN, metodo editarStatus: " + e.toString() );
        }
        
    }
    
    public Collection<Gerente> retornaListaGerente(){
        return p.getGerenteCollection();
    }
    
    public int verificaGerenteProcesso(){
        
        Collection<Gerente> g = p.getGerenteCollection();
        if(g.isEmpty())
            return 0;
        Collection<Gerenteprocesso> ger = p.getGerenteprocessoCollection();
        if(ger.isEmpty())
            return 1;    
        return 2;
    }
    
    public Gerenteprocesso retornaGerenteProcesso(){
        Collection<Gerenteprocesso> aux = p.getGerenteprocessoCollection();
        for(Gerenteprocesso g : aux)
            return g;
        return null;
    }
    
    public String salvarGerPro(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        UsuarioJpaController daoUser = new UsuarioJpaController(emf);
        GerenteprocessoJpaController daoGerPro = new GerenteprocessoJpaController(emf);
        Gerenteprocesso g = new Gerenteprocesso();
        g.setIdgerente(daoUser.findUsuario(ger));
        g.setIdprojeto(p);
        g.setVisualizado(false);
        try {
            daoGerPro.create(g);
        } catch (Exception e) {
            System.out.println("Erro, classe relatorioAdmBEAN, método salvarGerPro: " + e.toString());
        }
        return "Relatorios/relatorioAdm.xhtml?faces-redirect=true";
    }
    
    public String alterar(){
        EntityManagerFactory emf = UtilDao.retornaEntity();
        ProjetoJpaController daoProj = new ProjetoJpaController(emf);
        try {
            daoProj.edit(p);
        } catch (Exception e) {
            System.out.println("Erro, classe relatorioAdmBean, método alterar: " + e.toString());
        }
        emf.close();
        return "Relatorios/relatorioAdm.xhtml?faces-redirect=true";
    }
    
    public void remover(){
        EntityManagerFactory emf = UtilDao.retornaEntity();
        ProjetoJpaController daoProj = new ProjetoJpaController(emf);
        try {
            daoProj.destroy(p.getId());
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O Projeto está veinculado à Gerentes e/ou Equipes e/ou Artefatos");
            RequestContext.getCurrentInstance().showMessageInDialog(message); 
            System.out.println("Erro, classe relatorioAdmBean, método remover: " + e.toString());
        }
        emf.close();
        //return "Relatorios/relatorioAdm.xhtml?faces-redirect=true";
    }    
}
