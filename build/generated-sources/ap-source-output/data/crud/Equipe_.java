package data.crud;

import data.crud.Atividademembroequipe;
import data.crud.Atividadesequipe;
import data.crud.Atividadesequipegerente;
import data.crud.Membrosequipe;
import data.crud.Notificacaogerente;
import data.crud.Projeto;
import data.crud.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-25T22:02:13")
@StaticMetamodel(Equipe.class)
public class Equipe_ { 

    public static volatile CollectionAttribute<Equipe, Notificacaogerente> notificacaogerenteCollection;
    public static volatile CollectionAttribute<Equipe, Atividadesequipe> atividadesequipeCollection;
    public static volatile CollectionAttribute<Equipe, Atividadesequipegerente> atividadesequipegerenteCollection;
    public static volatile SingularAttribute<Equipe, String> nomeequipe;
    public static volatile CollectionAttribute<Equipe, Atividademembroequipe> atividademembroequipeCollection;
    public static volatile SingularAttribute<Equipe, Integer> id;
    public static volatile SingularAttribute<Equipe, Usuario> idgerente;
    public static volatile CollectionAttribute<Equipe, Membrosequipe> membrosequipeCollection;
    public static volatile SingularAttribute<Equipe, String> descricao;
    public static volatile SingularAttribute<Equipe, Projeto> idprojeto;

}