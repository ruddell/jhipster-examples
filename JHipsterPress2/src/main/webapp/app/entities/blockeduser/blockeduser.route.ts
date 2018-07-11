import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Blockeduser } from 'app/shared/model/blockeduser.model';
import { BlockeduserService } from './blockeduser.service';
import { BlockeduserComponent } from './blockeduser.component';
import { BlockeduserDetailComponent } from './blockeduser-detail.component';
import { BlockeduserUpdateComponent } from './blockeduser-update.component';
import { BlockeduserDeletePopupComponent } from './blockeduser-delete-dialog.component';
import { IBlockeduser } from 'app/shared/model/blockeduser.model';

@Injectable({ providedIn: 'root' })
export class BlockeduserResolve implements Resolve<IBlockeduser> {
    constructor(private service: BlockeduserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((blockeduser: HttpResponse<Blockeduser>) => blockeduser.body);
        }
        return Observable.of(new Blockeduser());
    }
}

export const blockeduserRoute: Routes = [
    {
        path: 'blockeduser',
        component: BlockeduserComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jHipsterPress2App.blockeduser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'blockeduser/:id/view',
        component: BlockeduserDetailComponent,
        resolve: {
            blockeduser: BlockeduserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.blockeduser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'blockeduser/new',
        component: BlockeduserUpdateComponent,
        resolve: {
            blockeduser: BlockeduserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.blockeduser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'blockeduser/:id/edit',
        component: BlockeduserUpdateComponent,
        resolve: {
            blockeduser: BlockeduserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.blockeduser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const blockeduserPopupRoute: Routes = [
    {
        path: 'blockeduser/:id/delete',
        component: BlockeduserDeletePopupComponent,
        resolve: {
            blockeduser: BlockeduserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.blockeduser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
