/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.bean;

import br.util.Util;
import data.crud.Artefato;
import data.crud.Atividade;
import data.crud.Atividademembroequipe;
import data.crud.Atividadesfaseprocesso;
import data.crud.Equipe;
import data.crud.Faseprojeto;
import data.crud.Gerenteprocesso;
import data.crud.Logusuario;
import data.crud.Projeto;
import data.crud.Usuario;
import data.dao.ArtefatoJpaController;
import data.dao.AtividadeJpaController;
import data.dao.AtividademembroequipeJpaController;
import data.dao.AtividadesfaseprocessoJpaController;
import data.dao.FaseprojetoJpaController;
import data.dao.GerenteprocessoJpaController;
import data.dao.LogusuarioJpaController;
import data.dao.ProjetoJpaController;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author padrao
 */
@ManagedBean
@RequestScoped
public class RegistroLogBEAN {
    
    private List<Logusuario> listaLog;
    private List<Atividademembroequipe> listaAtv;
    private Projeto p;
    private Atividademembroequipe atividade;
    private String nomeAtividade;
    private String hora;
    private boolean btn;
    
    public RegistroLogBEAN(){
    
    }

    public List<Logusuario> getListaLog() {
        return listaLog;
    }

    public List<Atividademembroequipe> getListaAtv() {
        return listaAtv;
    }

    public void setListaAtv(List<Atividademembroequipe> listaAtv) {
        this.listaAtv = listaAtv;
    }
    
    public void setListaLog(List<Logusuario> listaLog) {
        this.listaLog = listaLog;
    }

    public Projeto getP() {
        return p;
    }

    public void setP(Projeto p) {
        this.p = p;
    }

    public Atividademembroequipe getAtividade() {
        return atividade;
    }

    public void setAtividade(Atividademembroequipe atividade) {
        this.atividade = atividade;
    }

    public String getNomeAtividade() {
        return nomeAtividade;
    }

    public void setNomeAtividade(String nomeAtividade) {
        this.nomeAtividade = nomeAtividade;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public boolean isBtn() {
        return btn;
    }

    public void setBtn(boolean btn) {
        this.btn = btn;
    }
     private String number;
 
    public String getNumber() {
        return number;
    }
 
    public void increment() {
        SimpleDateFormat d =  new SimpleDateFormat("HH:mm:ss");    
        
        number = d.format(new Date()); 
    }
    
    
    @PostConstruct
    public void carregaTabela(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        Equipe eqp = (Equipe)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("log");
        int idProjeto = (int)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("projetoGerente");
        //System.out.println("Projeto: " + idProjeto);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        p = new ProjetoJpaController(emf).findProjeto(idProjeto);
        //Carregar table
        AtividademembroequipeJpaController daoAtv = new AtividademembroequipeJpaController(emf);
        List<Atividademembroequipe> auxAtv = daoAtv.findAtividademembroequipeEntities();
        listaAtv = new ArrayList<Atividademembroequipe>();
        
        FaseprojetoJpaController daoFase = new FaseprojetoJpaController(emf);
        List<Faseprojeto> aux = daoFase.findFaseprojetoEntities();
        int fase = 0;
        for(Faseprojeto f : aux){
            int a = f.getIdprojeto().getId();
            int b = p.getId();
            if(a == b)
                fase = f.getIdfase().getId();
        }
        
        
        for(Atividademembroequipe atv : auxAtv){
            int a = user.getId();
            int b = atv.getIdmembro().getId();
            
            int c = p.getId();
            int d = atv.getIdequipe().getIdprojeto().getId();
           
            int e = eqp.getId();
            int f = atv.getIdequipe().getId();
            
            if(a == b && c == d && e == f && atv.getStatus() == false){
                if(verificaAtividade(atv, fase) == true)
                    listaAtv.add(atv);
            }
        }
        
        //Carregar log's
        LogusuarioJpaController daoLog = new LogusuarioJpaController(emf);
        List<Logusuario> auxLog = daoLog.findLogusuarioEntities();
        listaLog = new ArrayList<Logusuario>();
        
        for(Logusuario l : auxLog){
            int a = user.getId();
            int b = l.getIdusuario().getId();
            
            int c = p.getId();
            int d = l.getIdprojeto().getId();
            
            int e = eqp.getId();
            int f = l.getAtividade().getIdequipe().getId();
            if(a == b && c == d && e == f){
                listaLog.add(l);
            }
        }
        
        btn = false;
    }
    
    public boolean verificaAtividade(Atividademembroequipe atv, int f){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        if(atv.getIdfuncao() == 0){
            AtividadesfaseprocessoJpaController dao = new AtividadesfaseprocessoJpaController(emf);
            Atividadesfaseprocesso a = dao.findAtividadesfaseprocesso(atv.getIdatividade());
            int b = a.getIdfase().getId();
            int c = f;
            if(b == c)
                return true;
            return false;
        }else{
            AtividadeJpaController dao = new AtividadeJpaController(emf);
            Atividade a = dao.findAtividade(atv.getIdatividade());
            int b = a.getIdfase().getId();
            int c = f;
            if(b == c)
                return true;
            return false;
        }
    }
    
    public boolean permissao(){
        Util u = new Util();
        return u.permissaoMem();
    }
    
    public int permissaoAtividades(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        GerenteprocessoJpaController daoGer = new GerenteprocessoJpaController(emf);
        List<Gerenteprocesso> aux = daoGer.findGerenteprocessoEntities();
        for(Gerenteprocesso g : aux){
            int a = g.getIdprojeto().getId();
            int b = p.getId();
            System.out.println(a + " - " + b);
            if(a == b)
                return 0;
        }
        return 1;
    }
    
    public String encerrarSecao(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        return "../index.xhtml?faces-redirect=true";
    }
    
    public Usuario retornaUsuario(){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return user;
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
    
    public String retornaAtividadePorId(int id){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        AtividademembroequipeJpaController daoAtv = new AtividademembroequipeJpaController(emf);
        Atividademembroequipe atv = daoAtv.findAtividademembroequipe(id);
        
        if(atv.getIdfuncao() == 0){
            AtividadesfaseprocessoJpaController dao = new AtividadesfaseprocessoJpaController(emf);
            return dao.findAtividadesfaseprocesso(atv.getIdatividade()).getNomeatividade();
        }else{
            AtividadeJpaController dao = new AtividadeJpaController(emf);
            return dao.findAtividade(atv.getIdatividade()).getNomeatividade();
        }
    }
    
    public String retornaDescricao(Atividademembroequipe atv){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        if(atv.getIdfuncao() == 0){
            AtividadesfaseprocessoJpaController dao = new AtividadesfaseprocessoJpaController(emf);
            return dao.findAtividadesfaseprocesso(atv.getIdatividade()).getDescricaoatividade();
        }else{
            AtividadeJpaController dao = new AtividadeJpaController(emf);
            return dao.findAtividade(atv.getIdatividade()).getDescricao();
        }
    }
    
    public void registrarLog(Atividademembroequipe log){
        Usuario user = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        atividade = log;
        if(log.getIdfuncao() == 0){
            AtividadesfaseprocessoJpaController daoAtv = new AtividadesfaseprocessoJpaController(emf);
            nomeAtividade = daoAtv.findAtividadesfaseprocesso(log.getIdatividade()).getNomeatividade();
        }else{
            AtividadeJpaController daoAtv = new AtividadeJpaController(emf);
            nomeAtividade = daoAtv.findAtividade(log.getIdatividade()).getNomeatividade();
        }
        
        LogusuarioJpaController daoLog = new LogusuarioJpaController(emf);
        Logusuario l = new Logusuario();
        
        Date dataInicio = new Date();
        
        l.setDatafim(dataInicio.toLocaleString().substring(0, 10));
        l.setDatainicio(dataInicio.toLocaleString().substring(0, 10));
         
        SimpleDateFormat d =  new SimpleDateFormat("HH:mm:ss");    
        l.setHorainicio(d.format(new Date()));
        l.setHorafim(d.format(new Date()));
        hora = d.format(new Date());
        
        l.setAtividade(log);
        l.setIdprojeto(p);
        l.setIdusuario(user);
        
        try {
            daoLog.create(l);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("logUsuario", l);
        } catch (Exception e) {
            System.out.println("Erro registroLogBEAN, método registrarLog: " + e.toString());
        }
    }
    
    public boolean btnFim(){
        System.out.println("Aqui");
        if(btn == true){
            btn = false;
        }
        btn = true;
        return btn;
    }
    
    public String registrarFim(){
        Logusuario l = (Logusuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("logUsuario");
       
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
        LogusuarioJpaController daoLog = new LogusuarioJpaController(emf);
                
        Date dataInicio = new Date();
        l.setDatafim(dataInicio.toLocaleString().substring(0, 10));
        SimpleDateFormat d =  new SimpleDateFormat("HH:mm:ss");    
        l.setHorafim(d.format(new Date()));
        try {
            daoLog.edit(l);
        } catch (Exception e) {
           System.out.println("Erro <registroLogBEAN>, método registrarFim: " + e.toString());
        }
        return "/CADs/registroLog.xhtml?faces-redirect=true";
    }
    
    private UploadedFile file;
    private int idAtvArtefato;
 
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public int getIdAtvArtefato() {
        return idAtvArtefato;
    }

    public void setIdAtvArtefato(int idAtvArtefato) {
        this.idAtvArtefato = idAtvArtefato;
    }
    
    
    
    public void upload() {
        if(file.getFileName().isEmpty()){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Selecione o artefato");
            RequestContext.getCurrentInstance().showMessageInDialog(message); 
        }else{
            System.out.println("Tamanho: " + file.getSize());
            byte[] array = new byte[(int)file.getSize()];
            //InputStream stream = new In
            try {
                //converte o objeto file em array de bytes
                InputStream is = file.getInputstream();
                byte[] bytes = new byte[(int)file.getSize()];
                int offset = 0;
                int numRead = 0;
                while (offset < bytes.length
                       && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                    offset += numRead;
                }
                
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("TCCBackupPU");
                //AtividademembroJpaController daoAtvMem = new AtividademembroJpaController(emf);
                AtividademembroequipeJpaController daoAtv = new AtividademembroequipeJpaController(emf);
                ArtefatoJpaController daoArquivo = new ArtefatoJpaController(emf);
                Artefato a = new Artefato();
                
                a.setArquivo(bytes);
                a.setNome(file.getFileName());
                //a.setIdatividademembro(daoAtvMem.findAtividademembro(idAtividadeSelecionada));
                a.setIdatividade(daoAtv.findAtividademembroequipe(idAtvArtefato));
                Date dataInicio = new Date();
                a.setDatacriacao(dataInicio.toLocaleString().substring(0, 10));                
                daoArquivo.create(a);
                
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Artefato enviado com sucesso");
                RequestContext.getCurrentInstance().showMessageInDialog(message);        
            
            } catch (Exception e) {
                System.out.println("Erro <RegistroLogBEAN>: " + e.toString());
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha", "Ocorreu uma falha ao enviar o artefaot");
                RequestContext.getCurrentInstance().showMessageInDialog(message);        
            
            }
        }
    }
}
