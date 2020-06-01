import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPenalty } from 'app/shared/model/penalty.model';

type EntityResponseType = HttpResponse<IPenalty>;
type EntityArrayResponseType = HttpResponse<IPenalty[]>;

@Injectable({ providedIn: 'root' })
export class PenaltyService {
  public resourceUrl = SERVER_API_URL + 'api/penalties';

  constructor(protected http: HttpClient) {}

  create(penalty: IPenalty): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(penalty);
    return this.http
      .post<IPenalty>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(penalty: IPenalty): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(penalty);
    return this.http
      .put<IPenalty>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPenalty>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPenalty[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(penalty: IPenalty): IPenalty {
    const copy: IPenalty = Object.assign({}, penalty, {
      datePenalty: penalty.datePenalty && penalty.datePenalty.isValid() ? penalty.datePenalty.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.datePenalty = res.body.datePenalty ? moment(res.body.datePenalty) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((penalty: IPenalty) => {
        penalty.datePenalty = penalty.datePenalty ? moment(penalty.datePenalty) : undefined;
      });
    }
    return res;
  }
}
