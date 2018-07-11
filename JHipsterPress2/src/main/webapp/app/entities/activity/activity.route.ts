import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Activity } from 'app/shared/model/activity.model';
import { ActivityService } from './activity.service';
import { ActivityComponent } from './activity.component';
import { ActivityDetailComponent } from './activity-detail.component';
import { ActivityUpdateComponent } from './activity-update.component';
import { ActivityDeletePopupComponent } from './activity-delete-dialog.component';
import { IActivity } from 'app/shared/model/activity.model';

@Injectable({ providedIn: 'root' })
export class ActivityResolve implements Resolve<IActivity> {
    constructor(private service: ActivityService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((activity: HttpResponse<Activity>) => activity.body);
        }
        return Observable.of(new Activity());
    }
}

export const activityRoute: Routes = [
    {
        path: 'activity',
        component: ActivityComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jHipsterPress2App.activity.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'activity/:id/view',
        component: ActivityDetailComponent,
        resolve: {
            activity: ActivityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.activity.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'activity/new',
        component: ActivityUpdateComponent,
        resolve: {
            activity: ActivityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.activity.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'activity/:id/edit',
        component: ActivityUpdateComponent,
        resolve: {
            activity: ActivityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.activity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const activityPopupRoute: Routes = [
    {
        path: 'activity/:id/delete',
        component: ActivityDeletePopupComponent,
        resolve: {
            activity: ActivityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.activity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
