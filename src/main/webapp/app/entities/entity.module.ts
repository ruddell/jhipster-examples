import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { Mini4BlogModule } from './blog/blog.module';
import { Mini4PostModule } from './post/post.module';
import { Mini4CommentModule } from './comment/comment.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        Mini4BlogModule,
        Mini4PostModule,
        Mini4CommentModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Mini4EntityModule {}
