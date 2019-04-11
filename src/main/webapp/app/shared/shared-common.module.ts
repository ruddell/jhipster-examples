import { NgModule } from '@angular/core';

import { WebservicetwoSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [WebservicetwoSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [WebservicetwoSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class WebservicetwoSharedCommonModule {}
