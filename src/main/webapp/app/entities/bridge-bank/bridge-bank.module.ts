import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinaltestSharedModule } from 'app/shared';
import {
    BridgeBankComponent,
    BridgeBankDetailComponent,
    BridgeBankUpdateComponent,
    BridgeBankDeletePopupComponent,
    BridgeBankDeleteDialogComponent,
    bridgeBankRoute,
    bridgeBankPopupRoute
} from './';

const ENTITY_STATES = [...bridgeBankRoute, ...bridgeBankPopupRoute];

@NgModule({
    imports: [FinaltestSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BridgeBankComponent,
        BridgeBankDetailComponent,
        BridgeBankUpdateComponent,
        BridgeBankDeleteDialogComponent,
        BridgeBankDeletePopupComponent
    ],
    entryComponents: [BridgeBankComponent, BridgeBankUpdateComponent, BridgeBankDeleteDialogComponent, BridgeBankDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinaltestBridgeBankModule {}
