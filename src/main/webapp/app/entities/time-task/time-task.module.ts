import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KonsultklockanSharedModule } from 'app/shared';
import { KonsultklockanAdminModule } from 'app/admin/admin.module';
import {
    TimeTaskComponent,
    TimeTaskDetailComponent,
    TimeTaskUpdateComponent,
    TimeTaskDeletePopupComponent,
    TimeTaskDeleteDialogComponent,
    timeTaskRoute,
    timeTaskPopupRoute
} from './';

const ENTITY_STATES = [...timeTaskRoute, ...timeTaskPopupRoute];

@NgModule({
    imports: [KonsultklockanSharedModule, KonsultklockanAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TimeTaskComponent,
        TimeTaskDetailComponent,
        TimeTaskUpdateComponent,
        TimeTaskDeleteDialogComponent,
        TimeTaskDeletePopupComponent
    ],
    entryComponents: [TimeTaskComponent, TimeTaskUpdateComponent, TimeTaskDeleteDialogComponent, TimeTaskDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KonsultklockanTimeTaskModule {}
