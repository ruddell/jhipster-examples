import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinaltestSharedModule } from 'app/shared';
import {
    BridgeCategoryComponent,
    BridgeCategoryDetailComponent,
    BridgeCategoryUpdateComponent,
    BridgeCategoryDeletePopupComponent,
    BridgeCategoryDeleteDialogComponent,
    bridgeCategoryRoute,
    bridgeCategoryPopupRoute
} from './';

const ENTITY_STATES = [...bridgeCategoryRoute, ...bridgeCategoryPopupRoute];

@NgModule({
    imports: [FinaltestSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BridgeCategoryComponent,
        BridgeCategoryDetailComponent,
        BridgeCategoryUpdateComponent,
        BridgeCategoryDeleteDialogComponent,
        BridgeCategoryDeletePopupComponent
    ],
    entryComponents: [
        BridgeCategoryComponent,
        BridgeCategoryUpdateComponent,
        BridgeCategoryDeleteDialogComponent,
        BridgeCategoryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinaltestBridgeCategoryModule {}
