import {Component, OnInit} from '@angular/core';
import {TimeTaskService} from '../entities/time-task/time-task.service';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {ITimeTask, TimeTask} from '../shared/model/time-task.model';
import {JhiAlertService} from 'ng-jhipster';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})
export class HomeComponent implements OnInit {
    timeTasks: TimeTask[] = [];

    constructor(private timeTaskService: TimeTaskService,
                private jhiAlertService: JhiAlertService) {}

    loadAll() {
        console.log('loadAll');
        this.timeTaskService.query().subscribe(
            (res: HttpResponse<ITimeTask[]>) => {
                this.timeTasks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    ngOnInit() {
        this.loadAll();
    }
}
