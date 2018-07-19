import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { BridgeBank } from 'app/shared/model/bridge-bank.model';
import { BridgeBankService } from './bridge-bank.service';
import { BridgeBankComponent } from './bridge-bank.component';
import { BridgeBankDetailComponent } from './bridge-bank-detail.component';
import { BridgeBankUpdateComponent } from './bridge-bank-update.component';
import { BridgeBankDeletePopupComponent } from './bridge-bank-delete-dialog.component';
import { IBridgeBank } from 'app/shared/model/bridge-bank.model';

@Injectable({ providedIn: 'root' })
export class BridgeBankResolve implements Resolve<IBridgeBank> {
    constructor(private service: BridgeBankService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((bridgeBank: HttpResponse<BridgeBank>) => bridgeBank.body);
        }
        return Observable.of(new BridgeBank());
    }
}

export const bridgeBankRoute: Routes = [
    {
        path: 'bridge-bank',
        component: BridgeBankComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeBank.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bridge-bank/:id/view',
        component: BridgeBankDetailComponent,
        resolve: {
            bridgeBank: BridgeBankResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeBank.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bridge-bank/new',
        component: BridgeBankUpdateComponent,
        resolve: {
            bridgeBank: BridgeBankResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeBank.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bridge-bank/:id/edit',
        component: BridgeBankUpdateComponent,
        resolve: {
            bridgeBank: BridgeBankResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeBank.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bridgeBankPopupRoute: Routes = [
    {
        path: 'bridge-bank/:id/delete',
        component: BridgeBankDeletePopupComponent,
        resolve: {
            bridgeBank: BridgeBankResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeBank.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
