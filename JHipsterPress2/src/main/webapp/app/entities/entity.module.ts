import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JHipsterPress2ProfileModule } from './profile/profile.module';
import { JHipsterPress2PartyModule } from './party/party.module';
import { JHipsterPress2FollowModule } from './follow/follow.module';
import { JHipsterPress2BlockeduserModule } from './blockeduser/blockeduser.module';
import { JHipsterPress2BlogModule } from './blog/blog.module';
import { JHipsterPress2PostModule } from './post/post.module';
import { JHipsterPress2CommentModule } from './comment/comment.module';
import { JHipsterPress2MessageModule } from './message/message.module';
import { JHipsterPress2NotificationModule } from './notification/notification.module';
import { JHipsterPress2AlbumModule } from './album/album.module';
import { JHipsterPress2PhotoModule } from './photo/photo.module';
import { JHipsterPress2TopicModule } from './topic/topic.module';
import { JHipsterPress2TagModule } from './tag/tag.module';
import { JHipsterPress2InterestModule } from './interest/interest.module';
import { JHipsterPress2ActivityModule } from './activity/activity.module';
import { JHipsterPress2CelebModule } from './celeb/celeb.module';
import { JHipsterPress2UrllinkModule } from './urllink/urllink.module';
import { JHipsterPress2FrontpageconfigModule } from './frontpageconfig/frontpageconfig.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JHipsterPress2ProfileModule,
        JHipsterPress2PartyModule,
        JHipsterPress2FollowModule,
        JHipsterPress2BlockeduserModule,
        JHipsterPress2BlogModule,
        JHipsterPress2PostModule,
        JHipsterPress2CommentModule,
        JHipsterPress2MessageModule,
        JHipsterPress2NotificationModule,
        JHipsterPress2AlbumModule,
        JHipsterPress2PhotoModule,
        JHipsterPress2TopicModule,
        JHipsterPress2TagModule,
        JHipsterPress2InterestModule,
        JHipsterPress2ActivityModule,
        JHipsterPress2CelebModule,
        JHipsterPress2UrllinkModule,
        JHipsterPress2FrontpageconfigModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JHipsterPress2EntityModule {}
