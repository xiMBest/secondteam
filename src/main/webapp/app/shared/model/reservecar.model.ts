import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';
import { CarType } from 'app/shared/model/enumerations/car-type.model';

export interface IReservecar {
  id?: number;
  name?: string;
  type?: CarType;
  totalPrice?: number;
  datePickUP?: Moment;
  dateDropDown?: Moment;
  description?: string;
  customer?: ICustomer;
}

export class Reservecar implements IReservecar {
  constructor(
    public id?: number,
    public name?: string,
    public type?: CarType,
    public totalPrice?: number,
    public datePickUP?: Moment,
    public dateDropDown?: Moment,
    public description?: string,
    public customer?: ICustomer
  ) {}
}
