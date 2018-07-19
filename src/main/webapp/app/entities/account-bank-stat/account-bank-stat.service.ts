import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAccountBankStat } from 'app/shared/model/account-bank-stat.model';

type EntityResponseType = HttpResponse<IAccountBankStat>;
type EntityArrayResponseType = HttpResponse<IAccountBankStat[]>;

@Injectable({ providedIn: 'root' })
export class AccountBankStatService {
    private resourceUrl = SERVER_API_URL + 'api/account-bank-stats';

    constructor(private http: HttpClient) {}

    create(accountBankStat: IAccountBankStat): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(accountBankStat);
        return this.http
            .post<IAccountBankStat>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(accountBankStat: IAccountBankStat): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(accountBankStat);
        return this.http
            .put<IAccountBankStat>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAccountBankStat>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAccountBankStat[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(accountBankStat: IAccountBankStat): IAccountBankStat {
        const copy: IAccountBankStat = Object.assign({}, accountBankStat, {
            lastRefresh:
                accountBankStat.lastRefresh != null && accountBankStat.lastRefresh.isValid() ? accountBankStat.lastRefresh.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.lastRefresh = res.body.lastRefresh != null ? moment(res.body.lastRefresh) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((accountBankStat: IAccountBankStat) => {
            accountBankStat.lastRefresh = accountBankStat.lastRefresh != null ? moment(accountBankStat.lastRefresh) : null;
        });
        return res;
    }
}
