/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { TimeTaskService } from 'app/entities/time-task/time-task.service';
import { ITimeTask, TimeTask } from 'app/shared/model/time-task.model';

describe('Service Tests', () => {
    describe('TimeTask Service', () => {
        let injector: TestBed;
        let service: TimeTaskService;
        let httpMock: HttpTestingController;
        let elemDefault: ITimeTask;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(TimeTaskService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new TimeTask('ID', currentDate, currentDate, 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        start: currentDate.format(DATE_FORMAT),
                        stop: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find('123')
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a TimeTask', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 'ID',
                        start: currentDate.format(DATE_FORMAT),
                        stop: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        start: currentDate,
                        stop: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new TimeTask(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a TimeTask', async () => {
                const returnedFromService = Object.assign(
                    {
                        start: currentDate.format(DATE_FORMAT),
                        stop: currentDate.format(DATE_FORMAT),
                        note: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        start: currentDate,
                        stop: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of TimeTask', async () => {
                const returnedFromService = Object.assign(
                    {
                        start: currentDate.format(DATE_FORMAT),
                        stop: currentDate.format(DATE_FORMAT),
                        note: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        start: currentDate,
                        stop: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a TimeTask', async () => {
                const rxPromise = service.delete('123').subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
