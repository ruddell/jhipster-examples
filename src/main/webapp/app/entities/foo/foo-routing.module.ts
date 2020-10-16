import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FooComponent } from './foo.component';
import { FooDetailComponent } from './foo-detail.component';
import { FooUpdateComponent } from './foo-update.component';
import { FooRoutingResolveService } from './foo-routing-resolve.service';
import { FooModule } from './foo.module';

const fooRoute: Routes = [
  {
    path: '',
    component: FooComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ajskdlfjaskldfasApp.foo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FooDetailComponent,
    resolve: {
      foo: FooRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ajskdlfjaskldfasApp.foo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FooUpdateComponent,
    resolve: {
      foo: FooRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ajskdlfjaskldfasApp.foo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FooUpdateComponent,
    resolve: {
      foo: FooRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ajskdlfjaskldfasApp.foo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fooRoute), FooModule],
  exports: [FooModule],
})
export class FooRoutingModule {}
