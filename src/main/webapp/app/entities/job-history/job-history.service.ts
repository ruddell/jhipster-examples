import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IJobHistory } from 'app/shared/model/job-history.model';

type EntityResponseType = HttpResponse<IJobHistory>;
type EntityArrayResponseType = HttpResponse<IJobHistory[]>;

@Injectable({ providedIn: 'root' })
export class JobHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/job-histories';

  constructor(protected http: HttpClient) {}

  create(jobHistory: IJobHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobHistory);
    return this.http
      .post<IJobHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(jobHistory: IJobHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobHistory);
    return this.http
      .put<IJobHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IJobHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IJobHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(jobHistory: IJobHistory): IJobHistory {
    const copy: IJobHistory = Object.assign({}, jobHistory, {
      startDate: jobHistory.startDate != null && jobHistory.startDate.isValid() ? jobHistory.startDate.toJSON() : null,
      endDate: jobHistory.endDate != null && jobHistory.endDate.isValid() ? jobHistory.endDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
      res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((jobHistory: IJobHistory) => {
        jobHistory.startDate = jobHistory.startDate != null ? moment(jobHistory.startDate) : null;
        jobHistory.endDate = jobHistory.endDate != null ? moment(jobHistory.endDate) : null;
      });
    }
    return res;
  }
}
