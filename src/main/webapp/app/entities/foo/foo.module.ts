import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MonoSharedModule } from 'app/shared';
import {
    FooComponent,
    FooDetailComponent,
    FooUpdateComponent,
    FooDeletePopupComponent,
    FooDeleteDialogComponent,
    fooRoute,
    fooPopupRoute
} from './';

const ENTITY_STATES = [...fooRoute, ...fooPopupRoute];

@NgModule({
    imports: [MonoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [FooComponent, FooDetailComponent, FooUpdateComponent, FooDeleteDialogComponent, FooDeletePopupComponent],
    entryComponents: [FooComponent, FooUpdateComponent, FooDeleteDialogComponent, FooDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MonoFooModule {}
