import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBridgeBank } from 'app/shared/model/bridge-bank.model';

type EntityResponseType = HttpResponse<IBridgeBank>;
type EntityArrayResponseType = HttpResponse<IBridgeBank[]>;

@Injectable({ providedIn: 'root' })
export class BridgeBankService {
    private resourceUrl = SERVER_API_URL + 'api/bridge-banks';

    constructor(private http: HttpClient) {}

    create(bridgeBank: IBridgeBank): Observable<EntityResponseType> {
        return this.http.post<IBridgeBank>(this.resourceUrl, bridgeBank, { observe: 'response' });
    }

    update(bridgeBank: IBridgeBank): Observable<EntityResponseType> {
        return this.http.put<IBridgeBank>(this.resourceUrl, bridgeBank, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBridgeBank>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBridgeBank[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
