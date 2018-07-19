import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { AccountBankStat } from 'app/shared/model/account-bank-stat.model';
import { AccountBankStatService } from './account-bank-stat.service';
import { AccountBankStatComponent } from './account-bank-stat.component';
import { AccountBankStatDetailComponent } from './account-bank-stat-detail.component';
import { AccountBankStatUpdateComponent } from './account-bank-stat-update.component';
import { AccountBankStatDeletePopupComponent } from './account-bank-stat-delete-dialog.component';
import { IAccountBankStat } from 'app/shared/model/account-bank-stat.model';

@Injectable({ providedIn: 'root' })
export class AccountBankStatResolve implements Resolve<IAccountBankStat> {
    constructor(private service: AccountBankStatService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((accountBankStat: HttpResponse<AccountBankStat>) => accountBankStat.body);
        }
        return Observable.of(new AccountBankStat());
    }
}

export const accountBankStatRoute: Routes = [
    {
        path: 'account-bank-stat',
        component: AccountBankStatComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.accountBankStat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'account-bank-stat/:id/view',
        component: AccountBankStatDetailComponent,
        resolve: {
            accountBankStat: AccountBankStatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.accountBankStat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'account-bank-stat/new',
        component: AccountBankStatUpdateComponent,
        resolve: {
            accountBankStat: AccountBankStatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.accountBankStat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'account-bank-stat/:id/edit',
        component: AccountBankStatUpdateComponent,
        resolve: {
            accountBankStat: AccountBankStatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.accountBankStat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const accountBankStatPopupRoute: Routes = [
    {
        path: 'account-bank-stat/:id/delete',
        component: AccountBankStatDeletePopupComponent,
        resolve: {
            accountBankStat: AccountBankStatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.accountBankStat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
