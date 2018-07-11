import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Notification } from 'app/shared/model/notification.model';
import { NotificationService } from './notification.service';
import { NotificationComponent } from './notification.component';
import { NotificationDetailComponent } from './notification-detail.component';
import { NotificationUpdateComponent } from './notification-update.component';
import { NotificationDeletePopupComponent } from './notification-delete-dialog.component';
import { INotification } from 'app/shared/model/notification.model';

@Injectable({ providedIn: 'root' })
export class NotificationResolve implements Resolve<INotification> {
    constructor(private service: NotificationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((notification: HttpResponse<Notification>) => notification.body);
        }
        return Observable.of(new Notification());
    }
}

export const notificationRoute: Routes = [
    {
        path: 'notification',
        component: NotificationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jHipsterPress2App.notification.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'notification/:id/view',
        component: NotificationDetailComponent,
        resolve: {
            notification: NotificationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.notification.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'notification/new',
        component: NotificationUpdateComponent,
        resolve: {
            notification: NotificationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.notification.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'notification/:id/edit',
        component: NotificationUpdateComponent,
        resolve: {
            notification: NotificationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.notification.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const notificationPopupRoute: Routes = [
    {
        path: 'notification/:id/delete',
        component: NotificationDeletePopupComponent,
        resolve: {
            notification: NotificationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.notification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
