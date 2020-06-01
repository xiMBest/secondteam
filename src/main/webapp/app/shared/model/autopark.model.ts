import { IRate } from 'app/shared/model/rate.model';
import { IFixcar } from 'app/shared/model/fixcar.model';
import { CarType } from 'app/shared/model/enumerations/car-type.model';

export interface IAutopark {
  id?: number;
  mark?: string;
  model?: string;
  prePrice?: number;
  color?: string;
  type?: CarType;
  pledge?: number;
  statusAvaileble?: boolean;
  rates?: IRate[];
  fixcars?: IFixcar[];
}

export class Autopark implements IAutopark {
  constructor(
    public id?: number,
    public mark?: string,
    public model?: string,
    public prePrice?: number,
    public color?: string,
    public type?: CarType,
    public pledge?: number,
    public statusAvaileble?: boolean,
    public rates?: IRate[],
    public fixcars?: IFixcar[]
  ) {
    this.statusAvaileble = this.statusAvaileble || false;
  }
}
