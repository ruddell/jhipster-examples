import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFoo, Foo } from 'app/shared/model/foo.model';
import { FooService } from './foo.service';
import { FooComponent } from './foo.component';
import { FooDetailComponent } from './foo-detail.component';
import { FooUpdateComponent } from './foo-update.component';

@Injectable({ providedIn: 'root' })
export class FooResolve implements Resolve<IFoo> {
  constructor(private service: FooService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFoo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((foo: HttpResponse<Foo>) => {
          if (foo.body) {
            return of(foo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Foo());
  }
}

export const fooRoute: Routes = [
  {
    path: '',
    component: FooComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'monoApp.foo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FooDetailComponent,
    resolve: {
      foo: FooResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'monoApp.foo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FooUpdateComponent,
    resolve: {
      foo: FooResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'monoApp.foo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FooUpdateComponent,
    resolve: {
      foo: FooResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'monoApp.foo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
