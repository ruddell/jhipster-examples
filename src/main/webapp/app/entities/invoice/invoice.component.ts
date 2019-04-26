import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IInvoice } from 'app/shared/model/invoice.model';
import { AccountService } from 'app/core';
import { InvoiceService } from './invoice.service';

@Component({
  selector: 'jhi-invoice',
  templateUrl: './invoice.component.html'
})
export class InvoiceComponent implements OnInit, OnDestroy {
  invoices: IInvoice[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected invoiceService: InvoiceService,
    protected jhiAlertService: JhiAlertService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.invoiceService
      .query()
      .pipe(
        filter((res: HttpResponse<IInvoice[]>) => res.ok),
        map((res: HttpResponse<IInvoice[]>) => res.body)
      )
      .subscribe(
        (res: IInvoice[]) => {
          this.invoices = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInInvoices();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInvoice) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInInvoices() {
    this.eventSubscriber = this.eventManager.subscribe('invoiceListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
