import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBridgeAccountOnlineBank } from 'app/shared/model/bridge-account-online-bank.model';
import { BridgeAccountOnlineBankService } from './bridge-account-online-bank.service';

@Component({
    selector: 'jhi-bridge-account-online-bank-delete-dialog',
    templateUrl: './bridge-account-online-bank-delete-dialog.component.html'
})
export class BridgeAccountOnlineBankDeleteDialogComponent {
    bridgeAccountOnlineBank: IBridgeAccountOnlineBank;

    constructor(
        private bridgeAccountOnlineBankService: BridgeAccountOnlineBankService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bridgeAccountOnlineBankService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bridgeAccountOnlineBankListModification',
                content: 'Deleted an bridgeAccountOnlineBank'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bridge-account-online-bank-delete-popup',
    template: ''
})
export class BridgeAccountOnlineBankDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bridgeAccountOnlineBank }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BridgeAccountOnlineBankDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.bridgeAccountOnlineBank = bridgeAccountOnlineBank;
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
