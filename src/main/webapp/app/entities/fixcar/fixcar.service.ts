import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFixcar } from 'app/shared/model/fixcar.model';

type EntityResponseType = HttpResponse<IFixcar>;
type EntityArrayResponseType = HttpResponse<IFixcar[]>;

@Injectable({ providedIn: 'root' })
export class FixcarService {
  public resourceUrl = SERVER_API_URL + 'api/fixcars';

  constructor(protected http: HttpClient) {}

  create(fixcar: IFixcar): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fixcar);
    return this.http
      .post<IFixcar>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fixcar: IFixcar): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fixcar);
    return this.http
      .put<IFixcar>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFixcar>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFixcar[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(fixcar: IFixcar): IFixcar {
    const copy: IFixcar = Object.assign({}, fixcar, {
      dateFixing: fixcar.dateFixing && fixcar.dateFixing.isValid() ? fixcar.dateFixing.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateFixing = res.body.dateFixing ? moment(res.body.dateFixing) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fixcar: IFixcar) => {
        fixcar.dateFixing = fixcar.dateFixing ? moment(fixcar.dateFixing) : undefined;
      });
    }
    return res;
  }
}
