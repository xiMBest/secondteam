import { IAutopark } from 'app/shared/model/autopark.model';
import { Ratestars } from 'app/shared/model/enumerations/ratestars.model';

export interface IRate {
  id?: number;
  raiting?: Ratestars;
  autopark?: IAutopark;
}

export class Rate implements IRate {
  constructor(public id?: number, public raiting?: Ratestars, public autopark?: IAutopark) {}
}
