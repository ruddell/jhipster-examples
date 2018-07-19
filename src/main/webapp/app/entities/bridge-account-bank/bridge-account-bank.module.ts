import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinaltestSharedModule } from 'app/shared';
import {
    BridgeAccountBankComponent,
    BridgeAccountBankDetailComponent,
    BridgeAccountBankUpdateComponent,
    BridgeAccountBankDeletePopupComponent,
    BridgeAccountBankDeleteDialogComponent,
    bridgeAccountBankRoute,
    bridgeAccountBankPopupRoute
} from './';

const ENTITY_STATES = [...bridgeAccountBankRoute, ...bridgeAccountBankPopupRoute];

@NgModule({
    imports: [FinaltestSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BridgeAccountBankComponent,
        BridgeAccountBankDetailComponent,
        BridgeAccountBankUpdateComponent,
        BridgeAccountBankDeleteDialogComponent,
        BridgeAccountBankDeletePopupComponent
    ],
    entryComponents: [
        BridgeAccountBankComponent,
        BridgeAccountBankUpdateComponent,
        BridgeAccountBankDeleteDialogComponent,
        BridgeAccountBankDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinaltestBridgeAccountBankModule {}
