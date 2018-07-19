import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FinaltestBridgeUserModule } from './bridge-user/bridge-user.module';
import { FinaltestBridgeBankModule } from './bridge-bank/bridge-bank.module';
import { FinaltestBridgeAccountBankModule } from './bridge-account-bank/bridge-account-bank.module';
import { FinaltestBridgeAccountOnlineBankModule } from './bridge-account-online-bank/bridge-account-online-bank.module';
import { FinaltestAccountBankStatModule } from './account-bank-stat/account-bank-stat.module';
import { FinaltestBridgeTransactionModule } from './bridge-transaction/bridge-transaction.module';
import { FinaltestBridgeCategoryModule } from './bridge-category/bridge-category.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        FinaltestBridgeUserModule,
        FinaltestBridgeBankModule,
        FinaltestBridgeAccountBankModule,
        FinaltestBridgeAccountOnlineBankModule,
        FinaltestAccountBankStatModule,
        FinaltestBridgeTransactionModule,
        FinaltestBridgeCategoryModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinaltestEntityModule {}
