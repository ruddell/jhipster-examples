import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MonoFooModule } from './foo/foo.module';
import { MonoBarModule } from './bar/bar.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        MonoFooModule,
        MonoBarModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MonoEntityModule {}
