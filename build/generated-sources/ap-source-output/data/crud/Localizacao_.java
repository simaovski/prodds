package data.crud;

import data.crud.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-25T22:02:13")
@StaticMetamodel(Localizacao.class)
public class Localizacao_ { 

    public static volatile SingularAttribute<Localizacao, String> estado;
    public static volatile SingularAttribute<Localizacao, String> cidade;
    public static volatile SingularAttribute<Localizacao, Integer> idequipe;
    public static volatile SingularAttribute<Localizacao, Integer> id;
    public static volatile SingularAttribute<Localizacao, Usuario> idgerente;
    public static volatile SingularAttribute<Localizacao, String> pais;

}