import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITimeTask } from 'app/shared/model/time-task.model';

@Component({
    selector: 'jhi-time-task-detail',
    templateUrl: './time-task-detail.component.html'
})
export class TimeTaskDetailComponent implements OnInit {
    timeTask: ITimeTask;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ timeTask }) => {
            this.timeTask = timeTask;
        });
    }

    previousState() {
        window.history.back();
    }
}
