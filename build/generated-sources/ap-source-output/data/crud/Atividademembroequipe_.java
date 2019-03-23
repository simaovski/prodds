package data.crud;

import data.crud.Artefato;
import data.crud.Equipe;
import data.crud.Logusuario;
import data.crud.Statusatividade;
import data.crud.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-25T22:02:13")
@StaticMetamodel(Atividademembroequipe.class)
public class Atividademembroequipe_ { 

    public static volatile SingularAttribute<Atividademembroequipe, Integer> idfuncao;
    public static volatile CollectionAttribute<Atividademembroequipe, Logusuario> logusuarioCollection;
    public static volatile SingularAttribute<Atividademembroequipe, Equipe> idequipe;
    public static volatile CollectionAttribute<Atividademembroequipe, Statusatividade> statusatividadeCollection;
    public static volatile SingularAttribute<Atividademembroequipe, Integer> idatividade;
    public static volatile SingularAttribute<Atividademembroequipe, Usuario> idmembro;
    public static volatile SingularAttribute<Atividademembroequipe, Usuario> idusuariocadatv;
    public static volatile SingularAttribute<Atividademembroequipe, Integer> id;
    public static volatile CollectionAttribute<Atividademembroequipe, Artefato> artefatoCollection;
    public static volatile SingularAttribute<Atividademembroequipe, Boolean> status;

}