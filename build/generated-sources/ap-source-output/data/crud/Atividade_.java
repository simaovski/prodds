package data.crud;

import data.crud.Atividadesantecessoras;
import data.crud.Atividadesequipegerente;
import data.crud.Fasesprocesso;
import data.crud.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-25T22:02:13")
@StaticMetamodel(Atividade.class)
public class Atividade_ { 

    public static volatile CollectionAttribute<Atividade, Atividadesequipegerente> atividadesequipegerenteCollection;
    public static volatile CollectionAttribute<Atividade, Atividadesantecessoras> atividadesantecessorasCollection;
    public static volatile SingularAttribute<Atividade, String> atividadessucessoras;
    public static volatile SingularAttribute<Atividade, Fasesprocesso> idfase;
    public static volatile CollectionAttribute<Atividade, Atividadesantecessoras> atividadesantecessorasCollection1;
    public static volatile SingularAttribute<Atividade, String> nomeatividade;
    public static volatile SingularAttribute<Atividade, Integer> id;
    public static volatile SingularAttribute<Atividade, Usuario> idgerente;
    public static volatile SingularAttribute<Atividade, String> descricao;

}