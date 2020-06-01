import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReservecar } from 'app/shared/model/reservecar.model';

type EntityResponseType = HttpResponse<IReservecar>;
type EntityArrayResponseType = HttpResponse<IReservecar[]>;

@Injectable({ providedIn: 'root' })
export class ReservecarService {
  public resourceUrl = SERVER_API_URL + 'api/reservecars';

  constructor(protected http: HttpClient) {}

  create(reservecar: IReservecar): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reservecar);
    return this.http
      .post<IReservecar>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(reservecar: IReservecar): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reservecar);
    return this.http
      .put<IReservecar>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReservecar>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReservecar[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(reservecar: IReservecar): IReservecar {
    const copy: IReservecar = Object.assign({}, reservecar, {
      datePickUP: reservecar.datePickUP && reservecar.datePickUP.isValid() ? reservecar.datePickUP.toJSON() : undefined,
      dateDropDown: reservecar.dateDropDown && reservecar.dateDropDown.isValid() ? reservecar.dateDropDown.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.datePickUP = res.body.datePickUP ? moment(res.body.datePickUP) : undefined;
      res.body.dateDropDown = res.body.dateDropDown ? moment(res.body.dateDropDown) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((reservecar: IReservecar) => {
        reservecar.datePickUP = reservecar.datePickUP ? moment(reservecar.datePickUP) : undefined;
        reservecar.dateDropDown = reservecar.dateDropDown ? moment(reservecar.dateDropDown) : undefined;
      });
    }
    return res;
  }
}
