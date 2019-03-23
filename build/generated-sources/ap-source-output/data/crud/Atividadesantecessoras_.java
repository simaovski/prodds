package data.crud;

import data.crud.Atividade;
import data.crud.Atividadesfaseprocesso;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-25T22:02:13")
@StaticMetamodel(Atividadesantecessoras.class)
public class Atividadesantecessoras_ { 

    public static volatile SingularAttribute<Atividadesantecessoras, Atividadesfaseprocesso> idatividadeprocesso;
    public static volatile SingularAttribute<Atividadesantecessoras, Atividadesfaseprocesso> idatividadeantecessoraprocesso;
    public static volatile SingularAttribute<Atividadesantecessoras, Atividade> idatividadeantecessora;
    public static volatile SingularAttribute<Atividadesantecessoras, Atividade> idatividade;
    public static volatile SingularAttribute<Atividadesantecessoras, Integer> id;

}