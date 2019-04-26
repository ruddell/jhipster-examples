import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Invoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from './invoice.service';
import { InvoiceComponent } from './invoice.component';
import { InvoiceDetailComponent } from './invoice-detail.component';
import { InvoiceUpdateComponent } from './invoice-update.component';
import { InvoiceDeletePopupComponent } from './invoice-delete-dialog.component';
import { IInvoice } from 'app/shared/model/invoice.model';

@Injectable({ providedIn: 'root' })
export class InvoiceResolve implements Resolve<IInvoice> {
  constructor(private service: InvoiceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInvoice> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Invoice>) => response.ok),
        map((invoice: HttpResponse<Invoice>) => invoice.body)
      );
    }
    return of(new Invoice());
  }
}

export const invoiceRoute: Routes = [
  {
    path: '',
    component: InvoiceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'monoApp.invoice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InvoiceDetailComponent,
    resolve: {
      invoice: InvoiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'monoApp.invoice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InvoiceUpdateComponent,
    resolve: {
      invoice: InvoiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'monoApp.invoice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InvoiceUpdateComponent,
    resolve: {
      invoice: InvoiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'monoApp.invoice.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const invoicePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InvoiceDeletePopupComponent,
    resolve: {
      invoice: InvoiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'monoApp.invoice.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
