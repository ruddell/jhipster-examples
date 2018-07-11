import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Follow } from 'app/shared/model/follow.model';
import { FollowService } from './follow.service';
import { FollowComponent } from './follow.component';
import { FollowDetailComponent } from './follow-detail.component';
import { FollowUpdateComponent } from './follow-update.component';
import { FollowDeletePopupComponent } from './follow-delete-dialog.component';
import { IFollow } from 'app/shared/model/follow.model';

@Injectable({ providedIn: 'root' })
export class FollowResolve implements Resolve<IFollow> {
    constructor(private service: FollowService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((follow: HttpResponse<Follow>) => follow.body);
        }
        return Observable.of(new Follow());
    }
}

export const followRoute: Routes = [
    {
        path: 'follow',
        component: FollowComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jHipsterPress2App.follow.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'follow/:id/view',
        component: FollowDetailComponent,
        resolve: {
            follow: FollowResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.follow.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'follow/new',
        component: FollowUpdateComponent,
        resolve: {
            follow: FollowResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.follow.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'follow/:id/edit',
        component: FollowUpdateComponent,
        resolve: {
            follow: FollowResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.follow.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const followPopupRoute: Routes = [
    {
        path: 'follow/:id/delete',
        component: FollowDeletePopupComponent,
        resolve: {
            follow: FollowResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.follow.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
