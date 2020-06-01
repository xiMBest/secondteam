package com.secondteam.rentauto.domain;

import com.secondteam.rentauto.domain.enumeration.CarType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Autopark.class)
public abstract class Autopark_ {

	public static volatile SingularAttribute<Autopark, String> color;
	public static volatile SetAttribute<Autopark, Fixcar> fixcars;
	public static volatile SingularAttribute<Autopark, Integer> prePrice;
	public static volatile SetAttribute<Autopark, Rate> rates;
	public static volatile SingularAttribute<Autopark, Integer> pledge;
	public static volatile SingularAttribute<Autopark, String> model;
	public static volatile SingularAttribute<Autopark, Long> id;
	public static volatile SingularAttribute<Autopark, CarType> type;
	public static volatile SingularAttribute<Autopark, String> mark;
	public static volatile SingularAttribute<Autopark, Boolean> statusAvaileble;

	public static final String COLOR = "color";
	public static final String FIXCARS = "fixcars";
	public static final String PRE_PRICE = "prePrice";
	public static final String RATES = "rates";
	public static final String PLEDGE = "pledge";
	public static final String MODEL = "model";
	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String MARK = "mark";
	public static final String STATUS_AVAILEBLE = "statusAvaileble";

}

