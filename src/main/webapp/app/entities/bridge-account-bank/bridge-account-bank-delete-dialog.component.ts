import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBridgeAccountBank } from 'app/shared/model/bridge-account-bank.model';
import { BridgeAccountBankService } from './bridge-account-bank.service';

@Component({
    selector: 'jhi-bridge-account-bank-delete-dialog',
    templateUrl: './bridge-account-bank-delete-dialog.component.html'
})
export class BridgeAccountBankDeleteDialogComponent {
    bridgeAccountBank: IBridgeAccountBank;

    constructor(
        private bridgeAccountBankService: BridgeAccountBankService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bridgeAccountBankService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bridgeAccountBankListModification',
                content: 'Deleted an bridgeAccountBank'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bridge-account-bank-delete-popup',
    template: ''
})
export class BridgeAccountBankDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bridgeAccountBank }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BridgeAccountBankDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.bridgeAccountBank = bridgeAccountBank;
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
