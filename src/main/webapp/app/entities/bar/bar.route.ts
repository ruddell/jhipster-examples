import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Bar } from 'app/shared/model/bar.model';
import { BarService } from './bar.service';
import { BarComponent } from './bar.component';
import { BarDetailComponent } from './bar-detail.component';
import { BarUpdateComponent } from './bar-update.component';
import { BarDeletePopupComponent } from './bar-delete-dialog.component';
import { IBar } from 'app/shared/model/bar.model';

@Injectable({ providedIn: 'root' })
export class BarResolve implements Resolve<IBar> {
    constructor(private service: BarService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((bar: HttpResponse<Bar>) => bar.body));
        }
        return of(new Bar());
    }
}

export const barRoute: Routes = [
    {
        path: 'bar',
        component: BarComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'monoApp.bar.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bar/:id/view',
        component: BarDetailComponent,
        resolve: {
            bar: BarResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'monoApp.bar.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bar/new',
        component: BarUpdateComponent,
        resolve: {
            bar: BarResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'monoApp.bar.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bar/:id/edit',
        component: BarUpdateComponent,
        resolve: {
            bar: BarResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'monoApp.bar.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const barPopupRoute: Routes = [
    {
        path: 'bar/:id/delete',
        component: BarDeletePopupComponent,
        resolve: {
            bar: BarResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'monoApp.bar.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
