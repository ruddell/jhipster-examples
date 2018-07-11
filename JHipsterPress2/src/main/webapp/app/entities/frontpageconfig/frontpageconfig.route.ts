import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Frontpageconfig } from 'app/shared/model/frontpageconfig.model';
import { FrontpageconfigService } from './frontpageconfig.service';
import { FrontpageconfigComponent } from './frontpageconfig.component';
import { FrontpageconfigDetailComponent } from './frontpageconfig-detail.component';
import { FrontpageconfigUpdateComponent } from './frontpageconfig-update.component';
import { FrontpageconfigDeletePopupComponent } from './frontpageconfig-delete-dialog.component';
import { IFrontpageconfig } from 'app/shared/model/frontpageconfig.model';

@Injectable({ providedIn: 'root' })
export class FrontpageconfigResolve implements Resolve<IFrontpageconfig> {
    constructor(private service: FrontpageconfigService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((frontpageconfig: HttpResponse<Frontpageconfig>) => frontpageconfig.body);
        }
        return Observable.of(new Frontpageconfig());
    }
}

export const frontpageconfigRoute: Routes = [
    {
        path: 'frontpageconfig',
        component: FrontpageconfigComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jHipsterPress2App.frontpageconfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'frontpageconfig/:id/view',
        component: FrontpageconfigDetailComponent,
        resolve: {
            frontpageconfig: FrontpageconfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.frontpageconfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'frontpageconfig/new',
        component: FrontpageconfigUpdateComponent,
        resolve: {
            frontpageconfig: FrontpageconfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.frontpageconfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'frontpageconfig/:id/edit',
        component: FrontpageconfigUpdateComponent,
        resolve: {
            frontpageconfig: FrontpageconfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.frontpageconfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const frontpageconfigPopupRoute: Routes = [
    {
        path: 'frontpageconfig/:id/delete',
        component: FrontpageconfigDeletePopupComponent,
        resolve: {
            frontpageconfig: FrontpageconfigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.frontpageconfig.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
