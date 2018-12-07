import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TimeTask } from 'app/shared/model/time-task.model';
import { TimeTaskService } from './time-task.service';
import { TimeTaskComponent } from './time-task.component';
import { TimeTaskDetailComponent } from './time-task-detail.component';
import { TimeTaskUpdateComponent } from './time-task-update.component';
import { TimeTaskDeletePopupComponent } from './time-task-delete-dialog.component';
import { ITimeTask } from 'app/shared/model/time-task.model';

@Injectable({ providedIn: 'root' })
export class TimeTaskResolve implements Resolve<ITimeTask> {
    constructor(private service: TimeTaskService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((timeTask: HttpResponse<TimeTask>) => timeTask.body));
        }
        return of(new TimeTask());
    }
}

export const timeTaskRoute: Routes = [
    {
        path: 'time-task',
        component: TimeTaskComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TimeTasks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'time-task/:id/view',
        component: TimeTaskDetailComponent,
        resolve: {
            timeTask: TimeTaskResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TimeTasks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'time-task/new',
        component: TimeTaskUpdateComponent,
        resolve: {
            timeTask: TimeTaskResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TimeTasks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'time-task/:id/edit',
        component: TimeTaskUpdateComponent,
        resolve: {
            timeTask: TimeTaskResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TimeTasks'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const timeTaskPopupRoute: Routes = [
    {
        path: 'time-task/:id/delete',
        component: TimeTaskDeletePopupComponent,
        resolve: {
            timeTask: TimeTaskResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TimeTasks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
