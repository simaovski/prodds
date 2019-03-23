package data.crud;

import data.crud.Equipe;
import data.crud.Projeto;
import data.crud.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-25T22:02:13")
@StaticMetamodel(Notificacaogerente.class)
public class Notificacaogerente_ { 

    public static volatile SingularAttribute<Notificacaogerente, Equipe> idequipe;
    public static volatile SingularAttribute<Notificacaogerente, Integer> id;
    public static volatile SingularAttribute<Notificacaogerente, Usuario> idgerente;
    public static volatile SingularAttribute<Notificacaogerente, Projeto> idprojeto;
    public static volatile SingularAttribute<Notificacaogerente, Usuario> idusuario;

}