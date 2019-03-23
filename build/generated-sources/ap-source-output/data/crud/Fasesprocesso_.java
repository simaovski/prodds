package data.crud;

import data.crud.Atividade;
import data.crud.Atividadesfaseprocesso;
import data.crud.Faseprojeto;
import data.crud.Processo;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-25T22:02:13")
@StaticMetamodel(Fasesprocesso.class)
public class Fasesprocesso_ { 

    public static volatile SingularAttribute<Fasesprocesso, String> descricaofase;
    public static volatile SingularAttribute<Fasesprocesso, String> nomefase;
    public static volatile CollectionAttribute<Fasesprocesso, Atividadesfaseprocesso> atividadesfaseprocessoCollection;
    public static volatile CollectionAttribute<Fasesprocesso, Faseprojeto> faseprojetoCollection;
    public static volatile CollectionAttribute<Fasesprocesso, Atividade> atividadeCollection;
    public static volatile SingularAttribute<Fasesprocesso, Processo> idprocesso;
    public static volatile SingularAttribute<Fasesprocesso, Integer> id;

}