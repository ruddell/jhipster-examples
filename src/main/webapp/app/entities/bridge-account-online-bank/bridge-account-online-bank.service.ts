import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBridgeAccountOnlineBank } from 'app/shared/model/bridge-account-online-bank.model';

type EntityResponseType = HttpResponse<IBridgeAccountOnlineBank>;
type EntityArrayResponseType = HttpResponse<IBridgeAccountOnlineBank[]>;

@Injectable({ providedIn: 'root' })
export class BridgeAccountOnlineBankService {
    private resourceUrl = SERVER_API_URL + 'api/bridge-account-online-banks';

    constructor(private http: HttpClient) {}

    create(bridgeAccountOnlineBank: IBridgeAccountOnlineBank): Observable<EntityResponseType> {
        return this.http.post<IBridgeAccountOnlineBank>(this.resourceUrl, bridgeAccountOnlineBank, { observe: 'response' });
    }

    update(bridgeAccountOnlineBank: IBridgeAccountOnlineBank): Observable<EntityResponseType> {
        return this.http.put<IBridgeAccountOnlineBank>(this.resourceUrl, bridgeAccountOnlineBank, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBridgeAccountOnlineBank>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBridgeAccountOnlineBank[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
