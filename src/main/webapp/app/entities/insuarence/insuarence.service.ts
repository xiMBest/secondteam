import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInsuarence } from 'app/shared/model/insuarence.model';

type EntityResponseType = HttpResponse<IInsuarence>;
type EntityArrayResponseType = HttpResponse<IInsuarence[]>;

@Injectable({ providedIn: 'root' })
export class InsuarenceService {
  public resourceUrl = SERVER_API_URL + 'api/insuarences';

  constructor(protected http: HttpClient) {}

  create(insuarence: IInsuarence): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(insuarence);
    return this.http
      .post<IInsuarence>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(insuarence: IInsuarence): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(insuarence);
    return this.http
      .put<IInsuarence>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInsuarence>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInsuarence[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(insuarence: IInsuarence): IInsuarence {
    const copy: IInsuarence = Object.assign({}, insuarence, {
      dateApply: insuarence.dateApply && insuarence.dateApply.isValid() ? insuarence.dateApply.toJSON() : undefined,
      dateEnd: insuarence.dateEnd && insuarence.dateEnd.isValid() ? insuarence.dateEnd.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateApply = res.body.dateApply ? moment(res.body.dateApply) : undefined;
      res.body.dateEnd = res.body.dateEnd ? moment(res.body.dateEnd) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((insuarence: IInsuarence) => {
        insuarence.dateApply = insuarence.dateApply ? moment(insuarence.dateApply) : undefined;
        insuarence.dateEnd = insuarence.dateEnd ? moment(insuarence.dateEnd) : undefined;
      });
    }
    return res;
  }
}
