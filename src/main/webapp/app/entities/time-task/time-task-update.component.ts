import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { ITimeTask } from 'app/shared/model/time-task.model';
import { TimeTaskService } from './time-task.service';
import { IUser, UserService } from 'app/core';
import { IProject } from 'app/shared/model/project.model';
import { ProjectService } from 'app/entities/project';

@Component({
    selector: 'jhi-time-task-update',
    templateUrl: './time-task-update.component.html'
})
export class TimeTaskUpdateComponent implements OnInit {
    timeTask: ITimeTask;
    isSaving: boolean;

    users: IUser[];

    projects: IProject[];
    startDp: any;
    stopDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private timeTaskService: TimeTaskService,
        private userService: UserService,
        private projectService: ProjectService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ timeTask }) => {
            this.timeTask = timeTask;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.projectService.query().subscribe(
            (res: HttpResponse<IProject[]>) => {
                this.projects = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.timeTask.id !== undefined) {
            this.subscribeToSaveResponse(this.timeTaskService.update(this.timeTask));
        } else {
            this.subscribeToSaveResponse(this.timeTaskService.create(this.timeTask));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITimeTask>>) {
        result.subscribe((res: HttpResponse<ITimeTask>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackProjectById(index: number, item: IProject) {
        return item.id;
    }
}
