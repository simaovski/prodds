package data.crud;

import data.crud.Atividade;
import data.crud.Equipe;
import data.crud.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-25T22:02:13")
@StaticMetamodel(Atividadesequipegerente.class)
public class Atividadesequipegerente_ { 

    public static volatile SingularAttribute<Atividadesequipegerente, Equipe> idequipe;
    public static volatile SingularAttribute<Atividadesequipegerente, Atividade> idatividade;
    public static volatile SingularAttribute<Atividadesequipegerente, Integer> id;
    public static volatile SingularAttribute<Atividadesequipegerente, Usuario> idusuariogerente;

}