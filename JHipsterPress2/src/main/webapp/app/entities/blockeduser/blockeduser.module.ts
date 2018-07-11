import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JHipsterPress2SharedModule } from 'app/shared';
import {
    BlockeduserComponent,
    BlockeduserDetailComponent,
    BlockeduserUpdateComponent,
    BlockeduserDeletePopupComponent,
    BlockeduserDeleteDialogComponent,
    blockeduserRoute,
    blockeduserPopupRoute
} from './';

const ENTITY_STATES = [...blockeduserRoute, ...blockeduserPopupRoute];

@NgModule({
    imports: [JHipsterPress2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BlockeduserComponent,
        BlockeduserDetailComponent,
        BlockeduserUpdateComponent,
        BlockeduserDeleteDialogComponent,
        BlockeduserDeletePopupComponent
    ],
    entryComponents: [BlockeduserComponent, BlockeduserUpdateComponent, BlockeduserDeleteDialogComponent, BlockeduserDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JHipsterPress2BlockeduserModule {}
