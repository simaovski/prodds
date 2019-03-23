package data.crud;

import data.crud.Fasesprocesso;
import data.crud.Projeto;
import data.crud.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-25T22:02:13")
@StaticMetamodel(Processo.class)
public class Processo_ { 

    public static volatile CollectionAttribute<Processo, Fasesprocesso> fasesprocessoCollection;
    public static volatile CollectionAttribute<Processo, Projeto> projetoCollection;
    public static volatile SingularAttribute<Processo, String> nomeprocesso;
    public static volatile SingularAttribute<Processo, Integer> id;
    public static volatile SingularAttribute<Processo, Integer> totalfases;
    public static volatile SingularAttribute<Processo, String> descricao;
    public static volatile SingularAttribute<Processo, Usuario> idusuario;

}