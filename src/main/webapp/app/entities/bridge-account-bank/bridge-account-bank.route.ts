import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { BridgeAccountBank } from 'app/shared/model/bridge-account-bank.model';
import { BridgeAccountBankService } from './bridge-account-bank.service';
import { BridgeAccountBankComponent } from './bridge-account-bank.component';
import { BridgeAccountBankDetailComponent } from './bridge-account-bank-detail.component';
import { BridgeAccountBankUpdateComponent } from './bridge-account-bank-update.component';
import { BridgeAccountBankDeletePopupComponent } from './bridge-account-bank-delete-dialog.component';
import { IBridgeAccountBank } from 'app/shared/model/bridge-account-bank.model';

@Injectable({ providedIn: 'root' })
export class BridgeAccountBankResolve implements Resolve<IBridgeAccountBank> {
    constructor(private service: BridgeAccountBankService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((bridgeAccountBank: HttpResponse<BridgeAccountBank>) => bridgeAccountBank.body));
        }
        return of(new BridgeAccountBank());
    }
}

export const bridgeAccountBankRoute: Routes = [
    {
        path: 'bridge-account-bank',
        component: BridgeAccountBankComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeAccountBank.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bridge-account-bank/:id/view',
        component: BridgeAccountBankDetailComponent,
        resolve: {
            bridgeAccountBank: BridgeAccountBankResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeAccountBank.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bridge-account-bank/new',
        component: BridgeAccountBankUpdateComponent,
        resolve: {
            bridgeAccountBank: BridgeAccountBankResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeAccountBank.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bridge-account-bank/:id/edit',
        component: BridgeAccountBankUpdateComponent,
        resolve: {
            bridgeAccountBank: BridgeAccountBankResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeAccountBank.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bridgeAccountBankPopupRoute: Routes = [
    {
        path: 'bridge-account-bank/:id/delete',
        component: BridgeAccountBankDeletePopupComponent,
        resolve: {
            bridgeAccountBank: BridgeAccountBankResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeAccountBank.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
