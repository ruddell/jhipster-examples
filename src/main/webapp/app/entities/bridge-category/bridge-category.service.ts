import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBridgeCategory } from 'app/shared/model/bridge-category.model';

type EntityResponseType = HttpResponse<IBridgeCategory>;
type EntityArrayResponseType = HttpResponse<IBridgeCategory[]>;

@Injectable({ providedIn: 'root' })
export class BridgeCategoryService {
    private resourceUrl = SERVER_API_URL + 'api/bridge-categories';

    constructor(private http: HttpClient) {}

    create(bridgeCategory: IBridgeCategory): Observable<EntityResponseType> {
        return this.http.post<IBridgeCategory>(this.resourceUrl, bridgeCategory, { observe: 'response' });
    }

    update(bridgeCategory: IBridgeCategory): Observable<EntityResponseType> {
        return this.http.put<IBridgeCategory>(this.resourceUrl, bridgeCategory, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBridgeCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBridgeCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
