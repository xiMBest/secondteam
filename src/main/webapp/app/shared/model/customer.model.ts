import { IReservecar } from 'app/shared/model/reservecar.model';
import { IPenalty } from 'app/shared/model/penalty.model';
import { IInsuarence } from 'app/shared/model/insuarence.model';

export interface ICustomer {
  id?: number;
  name?: string;
  surname?: string;
  years?: number;
  email?: string;
  phone?: string;
  adress?: string;
  reservecars?: IReservecar[];
  penaltys?: IPenalty[];
  insuarences?: IInsuarence[];
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public name?: string,
    public surname?: string,
    public years?: number,
    public email?: string,
    public phone?: string,
    public adress?: string,
    public reservecars?: IReservecar[],
    public penaltys?: IPenalty[],
    public insuarences?: IInsuarence[]
  ) {}
}
