import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JHipsterPress2SharedModule } from 'app/shared';
import {
    TopicComponent,
    TopicDetailComponent,
    TopicUpdateComponent,
    TopicDeletePopupComponent,
    TopicDeleteDialogComponent,
    topicRoute,
    topicPopupRoute
} from './';

const ENTITY_STATES = [...topicRoute, ...topicPopupRoute];

@NgModule({
    imports: [JHipsterPress2SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [TopicComponent, TopicDetailComponent, TopicUpdateComponent, TopicDeleteDialogComponent, TopicDeletePopupComponent],
    entryComponents: [TopicComponent, TopicUpdateComponent, TopicDeleteDialogComponent, TopicDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JHipsterPress2TopicModule {}
