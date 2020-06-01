import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAutopark } from 'app/shared/model/autopark.model';

type EntityResponseType = HttpResponse<IAutopark>;
type EntityArrayResponseType = HttpResponse<IAutopark[]>;

@Injectable({ providedIn: 'root' })
export class AutoparkService {
  public resourceUrl = SERVER_API_URL + 'api/autoparks';

  constructor(protected http: HttpClient) {}

  create(autopark: IAutopark): Observable<EntityResponseType> {
    return this.http.post<IAutopark>(this.resourceUrl, autopark, { observe: 'response' });
  }

  update(autopark: IAutopark): Observable<EntityResponseType> {
    return this.http.put<IAutopark>(this.resourceUrl, autopark, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAutopark>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAutopark[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
