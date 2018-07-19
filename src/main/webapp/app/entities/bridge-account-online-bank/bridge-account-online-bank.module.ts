import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinaltestSharedModule } from 'app/shared';
import {
    BridgeAccountOnlineBankComponent,
    BridgeAccountOnlineBankDetailComponent,
    BridgeAccountOnlineBankUpdateComponent,
    BridgeAccountOnlineBankDeletePopupComponent,
    BridgeAccountOnlineBankDeleteDialogComponent,
    bridgeAccountOnlineBankRoute,
    bridgeAccountOnlineBankPopupRoute
} from './';

const ENTITY_STATES = [...bridgeAccountOnlineBankRoute, ...bridgeAccountOnlineBankPopupRoute];

@NgModule({
    imports: [FinaltestSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BridgeAccountOnlineBankComponent,
        BridgeAccountOnlineBankDetailComponent,
        BridgeAccountOnlineBankUpdateComponent,
        BridgeAccountOnlineBankDeleteDialogComponent,
        BridgeAccountOnlineBankDeletePopupComponent
    ],
    entryComponents: [
        BridgeAccountOnlineBankComponent,
        BridgeAccountOnlineBankUpdateComponent,
        BridgeAccountOnlineBankDeleteDialogComponent,
        BridgeAccountOnlineBankDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinaltestBridgeAccountOnlineBankModule {}
