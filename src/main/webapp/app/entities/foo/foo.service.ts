import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFoo } from 'app/shared/model/foo.model';

type EntityResponseType = HttpResponse<IFoo>;
type EntityArrayResponseType = HttpResponse<IFoo[]>;

@Injectable({ providedIn: 'root' })
export class FooService {
  public resourceUrl = SERVER_API_URL + 'api/foos';

  constructor(protected http: HttpClient) {}

  create(foo: IFoo): Observable<EntityResponseType> {
    return this.http.post<IFoo>(this.resourceUrl, foo, { observe: 'response' });
  }

  update(foo: IFoo): Observable<EntityResponseType> {
    return this.http.put<IFoo>(this.resourceUrl, foo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFoo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFoo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
