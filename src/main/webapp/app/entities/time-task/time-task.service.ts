import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITimeTask } from 'app/shared/model/time-task.model';

type EntityResponseType = HttpResponse<ITimeTask>;
type EntityArrayResponseType = HttpResponse<ITimeTask[]>;

@Injectable({ providedIn: 'root' })
export class TimeTaskService {
    public resourceUrl = SERVER_API_URL + 'api/time-tasks';

    constructor(private http: HttpClient) {}

    create(timeTask: ITimeTask): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(timeTask);
        return this.http
            .post<ITimeTask>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(timeTask: ITimeTask): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(timeTask);
        return this.http
            .put<ITimeTask>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http
            .get<ITimeTask>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITimeTask[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    // query(req?: any): Observable<EntityArrayResponseType> {
    //     const options = createRequestOption(req);
    //     return this.http.get<ITimeTask[]>(this.resourceUrl, { params: options, observe: 'response' });
    // }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(timeTask: ITimeTask): ITimeTask {
        const copy: ITimeTask = Object.assign({}, timeTask, {
            start: timeTask.start != null && timeTask.start.isValid() ? timeTask.start.format(DATE_FORMAT) : null,
            stop: timeTask.stop != null && timeTask.stop.isValid() ? timeTask.stop.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.start = res.body.start != null ? moment(res.body.start) : null;
        res.body.stop = res.body.stop != null ? moment(res.body.stop) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((timeTask: ITimeTask) => {
            timeTask.start = timeTask.start != null ? moment(timeTask.start) : null;
            timeTask.stop = timeTask.stop != null ? moment(timeTask.stop) : null;
        });
        return res;
    }
}
