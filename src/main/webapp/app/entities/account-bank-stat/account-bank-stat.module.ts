import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinaltestSharedModule } from 'app/shared';
import {
    AccountBankStatComponent,
    AccountBankStatDetailComponent,
    AccountBankStatUpdateComponent,
    AccountBankStatDeletePopupComponent,
    AccountBankStatDeleteDialogComponent,
    accountBankStatRoute,
    accountBankStatPopupRoute
} from './';

const ENTITY_STATES = [...accountBankStatRoute, ...accountBankStatPopupRoute];

@NgModule({
    imports: [FinaltestSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AccountBankStatComponent,
        AccountBankStatDetailComponent,
        AccountBankStatUpdateComponent,
        AccountBankStatDeleteDialogComponent,
        AccountBankStatDeletePopupComponent
    ],
    entryComponents: [
        AccountBankStatComponent,
        AccountBankStatUpdateComponent,
        AccountBankStatDeleteDialogComponent,
        AccountBankStatDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinaltestAccountBankStatModule {}
