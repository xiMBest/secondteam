package com.secondteam.rentauto.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Fixcar.class)
public abstract class Fixcar_ {

	public static volatile SingularAttribute<Fixcar, ZonedDateTime> dateFixing;
	public static volatile SingularAttribute<Fixcar, Integer> price;
	public static volatile SetAttribute<Fixcar, Autopark> autoparks;
	public static volatile SingularAttribute<Fixcar, String> description;
	public static volatile SingularAttribute<Fixcar, Long> id;

	public static final String DATE_FIXING = "dateFixing";
	public static final String PRICE = "price";
	public static final String AUTOPARKS = "autoparks";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";

}

