import { NgModule } from '@angular/core';

import { Mini4SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [Mini4SharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [Mini4SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class Mini4SharedCommonModule {}
