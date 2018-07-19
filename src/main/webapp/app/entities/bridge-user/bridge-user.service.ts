import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBridgeUser } from 'app/shared/model/bridge-user.model';

type EntityResponseType = HttpResponse<IBridgeUser>;
type EntityArrayResponseType = HttpResponse<IBridgeUser[]>;

@Injectable({ providedIn: 'root' })
export class BridgeUserService {
    private resourceUrl = SERVER_API_URL + 'api/bridge-users';

    constructor(private http: HttpClient) {}

    create(bridgeUser: IBridgeUser): Observable<EntityResponseType> {
        return this.http.post<IBridgeUser>(this.resourceUrl, bridgeUser, { observe: 'response' });
    }

    update(bridgeUser: IBridgeUser): Observable<EntityResponseType> {
        return this.http.put<IBridgeUser>(this.resourceUrl, bridgeUser, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBridgeUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBridgeUser[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
