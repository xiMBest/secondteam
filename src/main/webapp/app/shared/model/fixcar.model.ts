import { Moment } from 'moment';
import { IAutopark } from 'app/shared/model/autopark.model';

export interface IFixcar {
  id?: number;
  price?: number;
  dateFixing?: Moment;
  description?: string;
  autoparks?: IAutopark[];
}

export class Fixcar implements IFixcar {
  constructor(
    public id?: number,
    public price?: number,
    public dateFixing?: Moment,
    public description?: string,
    public autoparks?: IAutopark[]
  ) {}
}
