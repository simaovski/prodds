package data.crud;

import data.crud.Atividade;
import data.crud.Atividademembroequipe;
import data.crud.Atividadesequipegerente;
import data.crud.Equipe;
import data.crud.Gerente;
import data.crud.Gerenteprocesso;
import data.crud.Gerenteprojeto;
import data.crud.Localizacao;
import data.crud.Logusuario;
import data.crud.Membrosequipe;
import data.crud.Notificacao;
import data.crud.Notificacaogerente;
import data.crud.Processo;
import data.crud.Projeto;
import data.crud.Statusprojeto;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-25T22:02:13")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile CollectionAttribute<Usuario, Notificacaogerente> notificacaogerenteCollection;
    public static volatile CollectionAttribute<Usuario, Projeto> projetoCollection;
    public static volatile CollectionAttribute<Usuario, Gerente> gerenteCollection1;
    public static volatile CollectionAttribute<Usuario, Gerente> gerenteCollection;
    public static volatile CollectionAttribute<Usuario, Equipe> equipeCollection;
    public static volatile SingularAttribute<Usuario, Integer> funcaousuario;
    public static volatile CollectionAttribute<Usuario, Localizacao> localizacaoCollection;
    public static volatile CollectionAttribute<Usuario, Notificacaogerente> notificacaogerenteCollection1;
    public static volatile CollectionAttribute<Usuario, Logusuario> logusuarioCollection;
    public static volatile SingularAttribute<Usuario, String> senha;
    public static volatile CollectionAttribute<Usuario, Atividadesequipegerente> atividadesequipegerenteCollection;
    public static volatile CollectionAttribute<Usuario, Gerenteprocesso> gerenteprocessoCollection;
    public static volatile CollectionAttribute<Usuario, Processo> processoCollection;
    public static volatile CollectionAttribute<Usuario, Atividademembroequipe> atividademembroequipeCollection;
    public static volatile CollectionAttribute<Usuario, Gerenteprojeto> gerenteprojetoCollection;
    public static volatile CollectionAttribute<Usuario, Membrosequipe> membrosequipeCollection1;
    public static volatile CollectionAttribute<Usuario, Notificacao> notificacaoCollection;
    public static volatile CollectionAttribute<Usuario, Atividade> atividadeCollection;
    public static volatile SingularAttribute<Usuario, Integer> id;
    public static volatile CollectionAttribute<Usuario, Membrosequipe> membrosequipeCollection;
    public static volatile SingularAttribute<Usuario, String> emailusuario;
    public static volatile CollectionAttribute<Usuario, Statusprojeto> statusprojetoCollection;
    public static volatile SingularAttribute<Usuario, String> nomeusuario;
    public static volatile CollectionAttribute<Usuario, Atividademembroequipe> atividademembroequipeCollection1;

}