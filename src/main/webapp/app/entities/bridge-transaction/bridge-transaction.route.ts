import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { BridgeTransaction } from 'app/shared/model/bridge-transaction.model';
import { BridgeTransactionService } from './bridge-transaction.service';
import { BridgeTransactionComponent } from './bridge-transaction.component';
import { BridgeTransactionDetailComponent } from './bridge-transaction-detail.component';
import { BridgeTransactionUpdateComponent } from './bridge-transaction-update.component';
import { BridgeTransactionDeletePopupComponent } from './bridge-transaction-delete-dialog.component';
import { IBridgeTransaction } from 'app/shared/model/bridge-transaction.model';

@Injectable({ providedIn: 'root' })
export class BridgeTransactionResolve implements Resolve<IBridgeTransaction> {
    constructor(private service: BridgeTransactionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((bridgeTransaction: HttpResponse<BridgeTransaction>) => bridgeTransaction.body));
        }
        return of(new BridgeTransaction());
    }
}

export const bridgeTransactionRoute: Routes = [
    {
        path: 'bridge-transaction',
        component: BridgeTransactionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeTransaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bridge-transaction/:id/view',
        component: BridgeTransactionDetailComponent,
        resolve: {
            bridgeTransaction: BridgeTransactionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeTransaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bridge-transaction/new',
        component: BridgeTransactionUpdateComponent,
        resolve: {
            bridgeTransaction: BridgeTransactionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeTransaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bridge-transaction/:id/edit',
        component: BridgeTransactionUpdateComponent,
        resolve: {
            bridgeTransaction: BridgeTransactionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeTransaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bridgeTransactionPopupRoute: Routes = [
    {
        path: 'bridge-transaction/:id/delete',
        component: BridgeTransactionDeletePopupComponent,
        resolve: {
            bridgeTransaction: BridgeTransactionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeTransaction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
