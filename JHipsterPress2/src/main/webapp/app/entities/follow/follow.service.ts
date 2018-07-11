import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFollow } from 'app/shared/model/follow.model';

type EntityResponseType = HttpResponse<IFollow>;
type EntityArrayResponseType = HttpResponse<IFollow[]>;

@Injectable({ providedIn: 'root' })
export class FollowService {
    private resourceUrl = SERVER_API_URL + 'api/follows';

    constructor(private http: HttpClient) {}

    create(follow: IFollow): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(follow);
        return this.http
            .post<IFollow>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(follow: IFollow): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(follow);
        return this.http
            .put<IFollow>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IFollow>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IFollow[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(follow: IFollow): IFollow {
        const copy: IFollow = Object.assign({}, follow, {
            creationDate: follow.creationDate != null && follow.creationDate.isValid() ? follow.creationDate.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.creationDate = res.body.creationDate != null ? moment(res.body.creationDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((follow: IFollow) => {
            follow.creationDate = follow.creationDate != null ? moment(follow.creationDate) : null;
        });
        return res;
    }
}
