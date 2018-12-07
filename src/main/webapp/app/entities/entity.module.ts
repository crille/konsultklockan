import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { KonsultklockanProjectModule } from './project/project.module';
import { KonsultklockanTimeTaskModule } from './time-task/time-task.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        KonsultklockanProjectModule,
        KonsultklockanTimeTaskModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KonsultklockanEntityModule {}
