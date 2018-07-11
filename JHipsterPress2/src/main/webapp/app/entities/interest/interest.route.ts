import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Interest } from 'app/shared/model/interest.model';
import { InterestService } from './interest.service';
import { InterestComponent } from './interest.component';
import { InterestDetailComponent } from './interest-detail.component';
import { InterestUpdateComponent } from './interest-update.component';
import { InterestDeletePopupComponent } from './interest-delete-dialog.component';
import { IInterest } from 'app/shared/model/interest.model';

@Injectable({ providedIn: 'root' })
export class InterestResolve implements Resolve<IInterest> {
    constructor(private service: InterestService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((interest: HttpResponse<Interest>) => interest.body);
        }
        return Observable.of(new Interest());
    }
}

export const interestRoute: Routes = [
    {
        path: 'interest',
        component: InterestComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jHipsterPress2App.interest.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'interest/:id/view',
        component: InterestDetailComponent,
        resolve: {
            interest: InterestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.interest.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'interest/new',
        component: InterestUpdateComponent,
        resolve: {
            interest: InterestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.interest.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'interest/:id/edit',
        component: InterestUpdateComponent,
        resolve: {
            interest: InterestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.interest.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const interestPopupRoute: Routes = [
    {
        path: 'interest/:id/delete',
        component: InterestDeletePopupComponent,
        resolve: {
            interest: InterestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.interest.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
