import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Topic } from 'app/shared/model/topic.model';
import { TopicService } from './topic.service';
import { TopicComponent } from './topic.component';
import { TopicDetailComponent } from './topic-detail.component';
import { TopicUpdateComponent } from './topic-update.component';
import { TopicDeletePopupComponent } from './topic-delete-dialog.component';
import { ITopic } from 'app/shared/model/topic.model';

@Injectable({ providedIn: 'root' })
export class TopicResolve implements Resolve<ITopic> {
    constructor(private service: TopicService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((topic: HttpResponse<Topic>) => topic.body);
        }
        return Observable.of(new Topic());
    }
}

export const topicRoute: Routes = [
    {
        path: 'topic',
        component: TopicComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jHipsterPress2App.topic.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'topic/:id/view',
        component: TopicDetailComponent,
        resolve: {
            topic: TopicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.topic.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'topic/new',
        component: TopicUpdateComponent,
        resolve: {
            topic: TopicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.topic.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'topic/:id/edit',
        component: TopicUpdateComponent,
        resolve: {
            topic: TopicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.topic.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const topicPopupRoute: Routes = [
    {
        path: 'topic/:id/delete',
        component: TopicDeletePopupComponent,
        resolve: {
            topic: TopicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.topic.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
