package com.secondteam.rentauto.domain;

import com.secondteam.rentauto.domain.enumeration.CarType;
import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Reservecar.class)
public abstract class Reservecar_ {

	public static volatile SingularAttribute<Reservecar, ZonedDateTime> dateDropDown;
	public static volatile SingularAttribute<Reservecar, Integer> totalPrice;
	public static volatile SingularAttribute<Reservecar, String> name;
	public static volatile SingularAttribute<Reservecar, String> description;
	public static volatile SingularAttribute<Reservecar, Long> id;
	public static volatile SingularAttribute<Reservecar, CarType> type;
	public static volatile SingularAttribute<Reservecar, ZonedDateTime> datePickUP;
	public static volatile SingularAttribute<Reservecar, Customer> customer;

	public static final String DATE_DROP_DOWN = "dateDropDown";
	public static final String TOTAL_PRICE = "totalPrice";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String DATE_PICK_UP = "datePickUP";
	public static final String CUSTOMER = "customer";

}

