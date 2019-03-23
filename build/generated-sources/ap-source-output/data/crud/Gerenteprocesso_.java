package data.crud;

import data.crud.Projeto;
import data.crud.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-25T22:02:13")
@StaticMetamodel(Gerenteprocesso.class)
public class Gerenteprocesso_ { 

    public static volatile SingularAttribute<Gerenteprocesso, Boolean> visualizado;
    public static volatile SingularAttribute<Gerenteprocesso, Integer> id;
    public static volatile SingularAttribute<Gerenteprocesso, Usuario> idgerente;
    public static volatile SingularAttribute<Gerenteprocesso, Projeto> idprojeto;

}