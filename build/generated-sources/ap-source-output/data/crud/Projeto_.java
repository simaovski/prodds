package data.crud;

import data.crud.Equipe;
import data.crud.Faseprojeto;
import data.crud.Gerente;
import data.crud.Gerenteprocesso;
import data.crud.Gerenteprojeto;
import data.crud.Logusuario;
import data.crud.Notificacaogerente;
import data.crud.Processo;
import data.crud.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-25T22:02:13")
@StaticMetamodel(Projeto.class)
public class Projeto_ { 

    public static volatile CollectionAttribute<Projeto, Notificacaogerente> notificacaogerenteCollection;
    public static volatile CollectionAttribute<Projeto, Gerente> gerenteCollection;
    public static volatile SingularAttribute<Projeto, String> datacriacao;
    public static volatile CollectionAttribute<Projeto, Faseprojeto> faseprojetoCollection;
    public static volatile CollectionAttribute<Projeto, Equipe> equipeCollection;
    public static volatile SingularAttribute<Projeto, Processo> idprocesso;
    public static volatile SingularAttribute<Projeto, String> nomeprojeto;
    public static volatile SingularAttribute<Projeto, String> descricao;
    public static volatile CollectionAttribute<Projeto, Logusuario> logusuarioCollection;
    public static volatile CollectionAttribute<Projeto, Gerenteprocesso> gerenteprocessoCollection;
    public static volatile CollectionAttribute<Projeto, Gerenteprojeto> gerenteprojetoCollection;
    public static volatile SingularAttribute<Projeto, Usuario> idadm;
    public static volatile SingularAttribute<Projeto, Integer> id;

}