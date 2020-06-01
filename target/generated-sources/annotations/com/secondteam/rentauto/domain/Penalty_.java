package com.secondteam.rentauto.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Penalty.class)
public abstract class Penalty_ {

	public static volatile SingularAttribute<Penalty, ZonedDateTime> datePenalty;
	public static volatile SingularAttribute<Penalty, Integer> price;
	public static volatile SingularAttribute<Penalty, String> description;
	public static volatile SingularAttribute<Penalty, Long> id;
	public static volatile SingularAttribute<Penalty, Customer> customer;

	public static final String DATE_PENALTY = "datePenalty";
	public static final String PRICE = "price";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String CUSTOMER = "customer";

}

