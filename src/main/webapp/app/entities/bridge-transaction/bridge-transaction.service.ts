import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBridgeTransaction } from 'app/shared/model/bridge-transaction.model';

type EntityResponseType = HttpResponse<IBridgeTransaction>;
type EntityArrayResponseType = HttpResponse<IBridgeTransaction[]>;

@Injectable({ providedIn: 'root' })
export class BridgeTransactionService {
    private resourceUrl = SERVER_API_URL + 'api/bridge-transactions';

    constructor(private http: HttpClient) {}

    create(bridgeTransaction: IBridgeTransaction): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(bridgeTransaction);
        return this.http
            .post<IBridgeTransaction>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(bridgeTransaction: IBridgeTransaction): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(bridgeTransaction);
        return this.http
            .put<IBridgeTransaction>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IBridgeTransaction>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBridgeTransaction[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(bridgeTransaction: IBridgeTransaction): IBridgeTransaction {
        const copy: IBridgeTransaction = Object.assign({}, bridgeTransaction, {
            date: bridgeTransaction.date != null && bridgeTransaction.date.isValid() ? bridgeTransaction.date.format(DATE_FORMAT) : null,
            updatedAt:
                bridgeTransaction.updatedAt != null && bridgeTransaction.updatedAt.isValid() ? bridgeTransaction.updatedAt.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((bridgeTransaction: IBridgeTransaction) => {
            bridgeTransaction.date = bridgeTransaction.date != null ? moment(bridgeTransaction.date) : null;
            bridgeTransaction.updatedAt = bridgeTransaction.updatedAt != null ? moment(bridgeTransaction.updatedAt) : null;
        });
        return res;
    }
}
