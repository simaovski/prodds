package data.crud;

import data.crud.Fasesprocesso;
import data.crud.Projeto;
import data.crud.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-25T22:02:13")
@StaticMetamodel(Faseprojeto.class)
public class Faseprojeto_ { 

    public static volatile SingularAttribute<Faseprojeto, Usuario> idusuariogerenteprocesso;
    public static volatile SingularAttribute<Faseprojeto, Fasesprocesso> idfase;
    public static volatile SingularAttribute<Faseprojeto, Integer> id;
    public static volatile SingularAttribute<Faseprojeto, Projeto> idprojeto;

}