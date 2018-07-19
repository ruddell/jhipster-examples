import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { BridgeAccountOnlineBank } from 'app/shared/model/bridge-account-online-bank.model';
import { BridgeAccountOnlineBankService } from './bridge-account-online-bank.service';
import { BridgeAccountOnlineBankComponent } from './bridge-account-online-bank.component';
import { BridgeAccountOnlineBankDetailComponent } from './bridge-account-online-bank-detail.component';
import { BridgeAccountOnlineBankUpdateComponent } from './bridge-account-online-bank-update.component';
import { BridgeAccountOnlineBankDeletePopupComponent } from './bridge-account-online-bank-delete-dialog.component';
import { IBridgeAccountOnlineBank } from 'app/shared/model/bridge-account-online-bank.model';

@Injectable({ providedIn: 'root' })
export class BridgeAccountOnlineBankResolve implements Resolve<IBridgeAccountOnlineBank> {
    constructor(private service: BridgeAccountOnlineBankService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .find(id)
                .map((bridgeAccountOnlineBank: HttpResponse<BridgeAccountOnlineBank>) => bridgeAccountOnlineBank.body);
        }
        return Observable.of(new BridgeAccountOnlineBank());
    }
}

export const bridgeAccountOnlineBankRoute: Routes = [
    {
        path: 'bridge-account-online-bank',
        component: BridgeAccountOnlineBankComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeAccountOnlineBank.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bridge-account-online-bank/:id/view',
        component: BridgeAccountOnlineBankDetailComponent,
        resolve: {
            bridgeAccountOnlineBank: BridgeAccountOnlineBankResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeAccountOnlineBank.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bridge-account-online-bank/new',
        component: BridgeAccountOnlineBankUpdateComponent,
        resolve: {
            bridgeAccountOnlineBank: BridgeAccountOnlineBankResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeAccountOnlineBank.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bridge-account-online-bank/:id/edit',
        component: BridgeAccountOnlineBankUpdateComponent,
        resolve: {
            bridgeAccountOnlineBank: BridgeAccountOnlineBankResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeAccountOnlineBank.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bridgeAccountOnlineBankPopupRoute: Routes = [
    {
        path: 'bridge-account-online-bank/:id/delete',
        component: BridgeAccountOnlineBankDeletePopupComponent,
        resolve: {
            bridgeAccountOnlineBank: BridgeAccountOnlineBankResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeAccountOnlineBank.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
