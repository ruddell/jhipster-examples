import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBridgeBank } from 'app/shared/model/bridge-bank.model';
import { BridgeBankService } from './bridge-bank.service';

@Component({
    selector: 'jhi-bridge-bank-delete-dialog',
    templateUrl: './bridge-bank-delete-dialog.component.html'
})
export class BridgeBankDeleteDialogComponent {
    bridgeBank: IBridgeBank;

    constructor(private bridgeBankService: BridgeBankService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bridgeBankService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bridgeBankListModification',
                content: 'Deleted an bridgeBank'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bridge-bank-delete-popup',
    template: ''
})
export class BridgeBankDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bridgeBank }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BridgeBankDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.bridgeBank = bridgeBank;
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
