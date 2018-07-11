import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Party } from 'app/shared/model/party.model';
import { PartyService } from './party.service';
import { PartyComponent } from './party.component';
import { PartyDetailComponent } from './party-detail.component';
import { PartyUpdateComponent } from './party-update.component';
import { PartyDeletePopupComponent } from './party-delete-dialog.component';
import { IParty } from 'app/shared/model/party.model';

@Injectable({ providedIn: 'root' })
export class PartyResolve implements Resolve<IParty> {
    constructor(private service: PartyService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((party: HttpResponse<Party>) => party.body);
        }
        return Observable.of(new Party());
    }
}

export const partyRoute: Routes = [
    {
        path: 'party',
        component: PartyComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jHipsterPress2App.party.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'party/:id/view',
        component: PartyDetailComponent,
        resolve: {
            party: PartyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.party.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'party/new',
        component: PartyUpdateComponent,
        resolve: {
            party: PartyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.party.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'party/:id/edit',
        component: PartyUpdateComponent,
        resolve: {
            party: PartyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.party.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const partyPopupRoute: Routes = [
    {
        path: 'party/:id/delete',
        component: PartyDeletePopupComponent,
        resolve: {
            party: PartyResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jHipsterPress2App.party.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
