package com.secondteam.rentauto.domain;

import com.secondteam.rentauto.domain.enumeration.Ratestars;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Rate.class)
public abstract class Rate_ {

	public static volatile SingularAttribute<Rate, Ratestars> raiting;
	public static volatile SingularAttribute<Rate, Autopark> autopark;
	public static volatile SingularAttribute<Rate, Long> id;

	public static final String RAITING = "raiting";
	public static final String AUTOPARK = "autopark";
	public static final String ID = "id";

}

