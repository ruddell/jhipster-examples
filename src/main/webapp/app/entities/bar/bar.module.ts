import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MonoSharedModule } from 'app/shared';
import {
    BarComponent,
    BarDetailComponent,
    BarUpdateComponent,
    BarDeletePopupComponent,
    BarDeleteDialogComponent,
    barRoute,
    barPopupRoute
} from './';

const ENTITY_STATES = [...barRoute, ...barPopupRoute];

@NgModule({
    imports: [MonoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [BarComponent, BarDetailComponent, BarUpdateComponent, BarDeleteDialogComponent, BarDeletePopupComponent],
    entryComponents: [BarComponent, BarUpdateComponent, BarDeleteDialogComponent, BarDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MonoBarModule {}
