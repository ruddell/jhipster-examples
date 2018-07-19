import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinaltestSharedModule } from 'app/shared';
import { FinaltestAdminModule } from 'app/admin/admin.module';
import {
    BridgeUserComponent,
    BridgeUserDetailComponent,
    BridgeUserUpdateComponent,
    BridgeUserDeletePopupComponent,
    BridgeUserDeleteDialogComponent,
    bridgeUserRoute,
    bridgeUserPopupRoute
} from './';

const ENTITY_STATES = [...bridgeUserRoute, ...bridgeUserPopupRoute];

@NgModule({
    imports: [FinaltestSharedModule, FinaltestAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BridgeUserComponent,
        BridgeUserDetailComponent,
        BridgeUserUpdateComponent,
        BridgeUserDeleteDialogComponent,
        BridgeUserDeletePopupComponent
    ],
    entryComponents: [BridgeUserComponent, BridgeUserUpdateComponent, BridgeUserDeleteDialogComponent, BridgeUserDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinaltestBridgeUserModule {}
