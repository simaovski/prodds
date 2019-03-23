package data.crud;

import data.crud.Atividadesantecessoras;
import data.crud.Atividadesantecessorasfaseprocesso;
import data.crud.Atividadesequipe;
import data.crud.Fasesprocesso;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-25T22:02:13")
@StaticMetamodel(Atividadesfaseprocesso.class)
public class Atividadesfaseprocesso_ { 

    public static volatile CollectionAttribute<Atividadesfaseprocesso, Atividadesequipe> atividadesequipeCollection;
    public static volatile SingularAttribute<Atividadesfaseprocesso, String> descricaoatividade;
    public static volatile CollectionAttribute<Atividadesfaseprocesso, Atividadesantecessorasfaseprocesso> atividadesantecessorasfaseprocessoCollection1;
    public static volatile CollectionAttribute<Atividadesfaseprocesso, Atividadesantecessoras> atividadesantecessorasCollection;
    public static volatile SingularAttribute<Atividadesfaseprocesso, String> atividadessucessoras;
    public static volatile SingularAttribute<Atividadesfaseprocesso, Fasesprocesso> idfase;
    public static volatile CollectionAttribute<Atividadesfaseprocesso, Atividadesantecessoras> atividadesantecessorasCollection1;
    public static volatile SingularAttribute<Atividadesfaseprocesso, String> nomeatividade;
    public static volatile SingularAttribute<Atividadesfaseprocesso, Integer> id;
    public static volatile CollectionAttribute<Atividadesfaseprocesso, Atividadesantecessorasfaseprocesso> atividadesantecessorasfaseprocessoCollection;

}