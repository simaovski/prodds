package data.crud;

import data.crud.Atividademembroequipe;
import data.crud.Projeto;
import data.crud.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-25T22:02:13")
@StaticMetamodel(Logusuario.class)
public class Logusuario_ { 

    public static volatile SingularAttribute<Logusuario, String> datainicio;
    public static volatile SingularAttribute<Logusuario, Atividademembroequipe> atividade;
    public static volatile SingularAttribute<Logusuario, String> horafim;
    public static volatile SingularAttribute<Logusuario, String> datafim;
    public static volatile SingularAttribute<Logusuario, Integer> id;
    public static volatile SingularAttribute<Logusuario, String> horainicio;
    public static volatile SingularAttribute<Logusuario, Projeto> idprojeto;
    public static volatile SingularAttribute<Logusuario, Usuario> idusuario;

}