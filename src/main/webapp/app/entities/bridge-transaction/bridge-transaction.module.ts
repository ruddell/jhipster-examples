import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinaltestSharedModule } from 'app/shared';
import {
    BridgeTransactionComponent,
    BridgeTransactionDetailComponent,
    BridgeTransactionUpdateComponent,
    BridgeTransactionDeletePopupComponent,
    BridgeTransactionDeleteDialogComponent,
    bridgeTransactionRoute,
    bridgeTransactionPopupRoute
} from './';

const ENTITY_STATES = [...bridgeTransactionRoute, ...bridgeTransactionPopupRoute];

@NgModule({
    imports: [FinaltestSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BridgeTransactionComponent,
        BridgeTransactionDetailComponent,
        BridgeTransactionUpdateComponent,
        BridgeTransactionDeleteDialogComponent,
        BridgeTransactionDeletePopupComponent
    ],
    entryComponents: [
        BridgeTransactionComponent,
        BridgeTransactionUpdateComponent,
        BridgeTransactionDeleteDialogComponent,
        BridgeTransactionDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinaltestBridgeTransactionModule {}
