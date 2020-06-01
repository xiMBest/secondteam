package com.secondteam.rentauto.domain;

import com.secondteam.rentauto.domain.enumeration.InsuarenceType;
import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Insuarence.class)
public abstract class Insuarence_ {

	public static volatile SingularAttribute<Insuarence, ZonedDateTime> dateApply;
	public static volatile SingularAttribute<Insuarence, Integer> cost;
	public static volatile SingularAttribute<Insuarence, Long> id;
	public static volatile SingularAttribute<Insuarence, ZonedDateTime> dateEnd;
	public static volatile SingularAttribute<Insuarence, InsuarenceType> type;
	public static volatile SingularAttribute<Insuarence, Customer> customer;

	public static final String DATE_APPLY = "dateApply";
	public static final String COST = "cost";
	public static final String ID = "id";
	public static final String DATE_END = "dateEnd";
	public static final String TYPE = "type";
	public static final String CUSTOMER = "customer";

}

