import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Foo } from 'app/shared/model/foo.model';
import { FooService } from './foo.service';
import { FooComponent } from './foo.component';
import { FooDetailComponent } from './foo-detail.component';
import { FooUpdateComponent } from './foo-update.component';
import { FooDeletePopupComponent } from './foo-delete-dialog.component';
import { IFoo } from 'app/shared/model/foo.model';

@Injectable({ providedIn: 'root' })
export class FooResolve implements Resolve<IFoo> {
  constructor(private service: FooService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFoo> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Foo>) => response.ok),
        map((foo: HttpResponse<Foo>) => foo.body)
      );
    }
    return of(new Foo());
  }
}

export const fooRoute: Routes = [
  {
    path: '',
    component: FooComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'monoApp.foo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FooDetailComponent,
    resolve: {
      foo: FooResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'monoApp.foo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FooUpdateComponent,
    resolve: {
      foo: FooResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'monoApp.foo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FooUpdateComponent,
    resolve: {
      foo: FooResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'monoApp.foo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const fooPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FooDeletePopupComponent,
    resolve: {
      foo: FooResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'monoApp.foo.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
