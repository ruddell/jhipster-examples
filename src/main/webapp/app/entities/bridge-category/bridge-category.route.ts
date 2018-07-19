import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { BridgeCategory } from 'app/shared/model/bridge-category.model';
import { BridgeCategoryService } from './bridge-category.service';
import { BridgeCategoryComponent } from './bridge-category.component';
import { BridgeCategoryDetailComponent } from './bridge-category-detail.component';
import { BridgeCategoryUpdateComponent } from './bridge-category-update.component';
import { BridgeCategoryDeletePopupComponent } from './bridge-category-delete-dialog.component';
import { IBridgeCategory } from 'app/shared/model/bridge-category.model';

@Injectable({ providedIn: 'root' })
export class BridgeCategoryResolve implements Resolve<IBridgeCategory> {
    constructor(private service: BridgeCategoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((bridgeCategory: HttpResponse<BridgeCategory>) => bridgeCategory.body);
        }
        return Observable.of(new BridgeCategory());
    }
}

export const bridgeCategoryRoute: Routes = [
    {
        path: 'bridge-category',
        component: BridgeCategoryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bridge-category/:id/view',
        component: BridgeCategoryDetailComponent,
        resolve: {
            bridgeCategory: BridgeCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bridge-category/new',
        component: BridgeCategoryUpdateComponent,
        resolve: {
            bridgeCategory: BridgeCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bridge-category/:id/edit',
        component: BridgeCategoryUpdateComponent,
        resolve: {
            bridgeCategory: BridgeCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bridgeCategoryPopupRoute: Routes = [
    {
        path: 'bridge-category/:id/delete',
        component: BridgeCategoryDeletePopupComponent,
        resolve: {
            bridgeCategory: BridgeCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finaltestApp.bridgeCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
