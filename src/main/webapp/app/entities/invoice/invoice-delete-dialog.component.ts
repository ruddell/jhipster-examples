import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from './invoice.service';

@Component({
  selector: 'jhi-invoice-delete-dialog',
  templateUrl: './invoice-delete-dialog.component.html'
})
export class InvoiceDeleteDialogComponent {
  invoice: IInvoice;

  constructor(protected invoiceService: InvoiceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.invoiceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'invoiceListModification',
        content: 'Deleted an invoice'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-invoice-delete-popup',
  template: ''
})
export class InvoiceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ invoice }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InvoiceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.invoice = invoice;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/invoice', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/invoice', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
