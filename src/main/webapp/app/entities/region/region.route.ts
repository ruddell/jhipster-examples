import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Region } from 'app/shared/model/region.model';
import { RegionService } from './region.service';
import { RegionComponent } from './region.component';
import { RegionDetailComponent } from './region-detail.component';
import { RegionUpdateComponent } from './region-update.component';
import { RegionDeletePopupComponent } from './region-delete-dialog.component';
import { IRegion } from 'app/shared/model/region.model';

@Injectable({ providedIn: 'root' })
export class RegionResolve implements Resolve<IRegion> {
  constructor(private service: RegionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRegion> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Region>) => response.ok),
        map((region: HttpResponse<Region>) => region.body)
      );
    }
    return of(new Region());
  }
}

export const regionRoute: Routes = [
  {
    path: '',
    component: RegionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'monoApp.region.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RegionDetailComponent,
    resolve: {
      region: RegionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'monoApp.region.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RegionUpdateComponent,
    resolve: {
      region: RegionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'monoApp.region.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RegionUpdateComponent,
    resolve: {
      region: RegionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'monoApp.region.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const regionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RegionDeletePopupComponent,
    resolve: {
      region: RegionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'monoApp.region.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
