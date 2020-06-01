import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';
import { InsuarenceType } from 'app/shared/model/enumerations/insuarence-type.model';

export interface IInsuarence {
  id?: number;
  dateApply?: Moment;
  dateEnd?: Moment;
  cost?: number;
  type?: InsuarenceType;
  customer?: ICustomer;
}

export class Insuarence implements IInsuarence {
  constructor(
    public id?: number,
    public dateApply?: Moment,
    public dateEnd?: Moment,
    public cost?: number,
    public type?: InsuarenceType,
    public customer?: ICustomer
  ) {}
}
