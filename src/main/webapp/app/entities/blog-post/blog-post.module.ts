import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared';
import { JhipsterAdminModule } from 'app/admin/admin.module';
import {
    BlogPostComponent,
    BlogPostDetailComponent,
    BlogPostUpdateComponent,
    BlogPostDeletePopupComponent,
    BlogPostDeleteDialogComponent,
    blogPostRoute,
    blogPostPopupRoute
} from './';

const ENTITY_STATES = [...blogPostRoute, ...blogPostPopupRoute];

@NgModule({
    imports: [JhipsterSharedModule, JhipsterAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BlogPostComponent,
        BlogPostDetailComponent,
        BlogPostUpdateComponent,
        BlogPostDeleteDialogComponent,
        BlogPostDeletePopupComponent
    ],
    entryComponents: [BlogPostComponent, BlogPostUpdateComponent, BlogPostDeleteDialogComponent, BlogPostDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterBlogPostModule {}
