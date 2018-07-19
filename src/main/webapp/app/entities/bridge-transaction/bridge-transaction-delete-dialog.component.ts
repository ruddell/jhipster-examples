import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBridgeTransaction } from 'app/shared/model/bridge-transaction.model';
import { BridgeTransactionService } from './bridge-transaction.service';

@Component({
    selector: 'jhi-bridge-transaction-delete-dialog',
    templateUrl: './bridge-transaction-delete-dialog.component.html'
})
export class BridgeTransactionDeleteDialogComponent {
    bridgeTransaction: IBridgeTransaction;

    constructor(
        private bridgeTransactionService: BridgeTransactionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bridgeTransactionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bridgeTransactionListModification',
                content: 'Deleted an bridgeTransaction'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bridge-transaction-delete-popup',
    template: ''
})
export class BridgeTransactionDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bridgeTransaction }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BridgeTransactionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.bridgeTransaction = bridgeTransaction;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
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
