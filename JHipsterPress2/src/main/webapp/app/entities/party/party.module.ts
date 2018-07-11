import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JHipsterPress2SharedModule } from 'app/shared';
import { JHipsterPress2AdminModule } from 'app/admin/admin.module';
import {
    PartyComponent,
    PartyDetailComponent,
    PartyUpdateComponent,
    PartyDeletePopupComponent,
    PartyDeleteDialogComponent,
    partyRoute,
    partyPopupRoute
} from './';

const ENTITY_STATES = [...partyRoute, ...partyPopupRoute];

@NgModule({
    imports: [JHipsterPress2SharedModule, JHipsterPress2AdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [PartyComponent, PartyDetailComponent, PartyUpdateComponent, PartyDeleteDialogComponent, PartyDeletePopupComponent],
    entryComponents: [PartyComponent, PartyUpdateComponent, PartyDeleteDialogComponent, PartyDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JHipsterPress2PartyModule {}
