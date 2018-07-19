import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBridgeAccountBank } from 'app/shared/model/bridge-account-bank.model';

type EntityResponseType = HttpResponse<IBridgeAccountBank>;
type EntityArrayResponseType = HttpResponse<IBridgeAccountBank[]>;

@Injectable({ providedIn: 'root' })
export class BridgeAccountBankService {
    private resourceUrl = SERVER_API_URL + 'api/bridge-account-banks';

    constructor(private http: HttpClient) {}

    create(bridgeAccountBank: IBridgeAccountBank): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(bridgeAccountBank);
        return this.http
            .post<IBridgeAccountBank>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(bridgeAccountBank: IBridgeAccountBank): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(bridgeAccountBank);
        return this.http
            .put<IBridgeAccountBank>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IBridgeAccountBank>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBridgeAccountBank[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(bridgeAccountBank: IBridgeAccountBank): IBridgeAccountBank {
        const copy: IBridgeAccountBank = Object.assign({}, bridgeAccountBank, {
            updatedAt:
                bridgeAccountBank.updatedAt != null && bridgeAccountBank.updatedAt.isValid() ? bridgeAccountBank.updatedAt.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((bridgeAccountBank: IBridgeAccountBank) => {
            bridgeAccountBank.updatedAt = bridgeAccountBank.updatedAt != null ? moment(bridgeAccountBank.updatedAt) : null;
        });
        return res;
    }
}
