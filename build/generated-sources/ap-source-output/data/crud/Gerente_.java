package data.crud;

import data.crud.Projeto;
import data.crud.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-25T22:02:13")
@StaticMetamodel(Gerente.class)
public class Gerente_ { 

    public static volatile SingularAttribute<Gerente, Projeto> projeto;
    public static volatile SingularAttribute<Gerente, Integer> id;
    public static volatile SingularAttribute<Gerente, Usuario> idusuarioadm;
    public static volatile SingularAttribute<Gerente, Integer> idprojeto;
    public static volatile SingularAttribute<Gerente, Usuario> idusuariogerente;

}