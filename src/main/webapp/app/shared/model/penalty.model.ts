import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IPenalty {
  id?: number;
  datePenalty?: Moment;
  price?: number;
  description?: string;
  customer?: ICustomer;
}

export class Penalty implements IPenalty {
  constructor(
    public id?: number,
    public datePenalty?: Moment,
    public price?: number,
    public description?: string,
    public customer?: ICustomer
  ) {}
}
