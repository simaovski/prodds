package data.crud;

import data.crud.Equipe;
import data.crud.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-25T22:02:13")
@StaticMetamodel(Membrosequipe.class)
public class Membrosequipe_ { 

    public static volatile SingularAttribute<Membrosequipe, Equipe> idequipe;
    public static volatile SingularAttribute<Membrosequipe, Usuario> idmembro;
    public static volatile SingularAttribute<Membrosequipe, Integer> id;
    public static volatile SingularAttribute<Membrosequipe, Usuario> idgerente;

}