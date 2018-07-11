import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JHipsterPress2SharedModule } from 'app/shared';
import {
    CelebComponent,
    CelebDetailComponent,
    CelebUpdateComponent,
    CelebDeletePopupComponent,
    CelebDeleteDialogComponent,
    celebRoute,
    celebPopupRoute
} from './';

const ENTITY_STATES = [...celebRoute, ...celebPopupRoute];

@NgModule({
    imports: [JHipsterPress2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [CelebComponent, CelebDetailComponent, CelebUpdateComponent, CelebDeleteDialogComponent, CelebDeletePopupComponent],
    entryComponents: [CelebComponent, CelebUpdateComponent, CelebDeleteDialogComponent, CelebDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JHipsterPress2CelebModule {}
