package com.secondteam.rentauto.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Customer.class)
public abstract class Customer_ {

	public static volatile SingularAttribute<Customer, String> phone;
	public static volatile SingularAttribute<Customer, String> surname;
	public static volatile SingularAttribute<Customer, String> name;
	public static volatile SingularAttribute<Customer, String> adress;
	public static volatile SetAttribute<Customer, Insuarence> insuarences;
	public static volatile SingularAttribute<Customer, Long> id;
	public static volatile SetAttribute<Customer, Penalty> penaltys;
	public static volatile SingularAttribute<Customer, Integer> years;
	public static volatile SingularAttribute<Customer, String> email;
	public static volatile SetAttribute<Customer, Reservecar> reservecars;

	public static final String PHONE = "phone";
	public static final String SURNAME = "surname";
	public static final String NAME = "name";
	public static final String ADRESS = "adress";
	public static final String INSUARENCES = "insuarences";
	public static final String ID = "id";
	public static final String PENALTYS = "penaltys";
	public static final String YEARS = "years";
	public static final String EMAIL = "email";
	public static final String RESERVECARS = "reservecars";

}

