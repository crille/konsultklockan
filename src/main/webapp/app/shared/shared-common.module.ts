import { NgModule } from '@angular/core';

import { KonsultklockanSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [KonsultklockanSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [KonsultklockanSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class KonsultklockanSharedCommonModule {}
