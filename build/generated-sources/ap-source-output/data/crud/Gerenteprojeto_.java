package data.crud;

import data.crud.Projeto;
import data.crud.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-25T22:02:13")
@StaticMetamodel(Gerenteprojeto.class)
public class Gerenteprojeto_ { 

    public static volatile SingularAttribute<Gerenteprojeto, Boolean> visualizado;
    public static volatile SingularAttribute<Gerenteprojeto, Integer> id;
    public static volatile SingularAttribute<Gerenteprojeto, Usuario> idgerente;
    public static volatile SingularAttribute<Gerenteprojeto, Projeto> idprojeto;

}