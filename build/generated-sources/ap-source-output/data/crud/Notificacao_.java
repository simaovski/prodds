package data.crud;

import data.crud.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-25T22:02:13")
@StaticMetamodel(Notificacao.class)
public class Notificacao_ { 

    public static volatile SingularAttribute<Notificacao, Boolean> visualizado;
    public static volatile SingularAttribute<Notificacao, Integer> id;
    public static volatile SingularAttribute<Notificacao, Boolean> solicitagerencia;
    public static volatile SingularAttribute<Notificacao, Integer> idprojeto;
    public static volatile SingularAttribute<Notificacao, Usuario> idusuario;

}