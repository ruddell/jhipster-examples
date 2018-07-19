import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { BridgeUser } from 'app/shared/model/bridge-user.model';
import { BridgeUserService } from './bridge-user.service';
import { BridgeUserComponent } from './bridge-user.component';
import { BridgeUserDetailComponent } from './bridge-user-detail.component';
import { BridgeUserUpdateComponent } from './bridge-user-update.component';
import { BridgeUserDeletePopupComponent } from './bridge-user-delete-dialog.component';
import { IBridgeUser } from 'app/shared/model/bridge-user.model';

@Injectable({ providedIn: 'root' })
export class BridgeUserResolve implements Resolve<IBridgeUser> {
    constructor(private service: BridgeUserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((bridgeUser: HttpResponse<BridgeUser>) => bridgeUser.body);
        }
        return Observable.of(new BridgeUser());
    }
}

export const bridgeUserRoute: Routes = [
    {
        path: 'bridge-user',
        component: BridgeUserComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bridge-user/:id/view',
        component: BridgeUserDetailComponent,
        resolve: {
            bridgeUser: BridgeUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bridge-user/new',
        component: BridgeUserUpdateComponent,
        resolve: {
            bridgeUser: BridgeUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bridge-user/:id/edit',
        component: BridgeUserUpdateComponent,
        resolve: {
            bridgeUser: BridgeUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bridgeUserPopupRoute: Routes = [
    {
        path: 'bridge-user/:id/delete',
        component: BridgeUserDeletePopupComponent,
        resolve: {
            bridgeUser: BridgeUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
