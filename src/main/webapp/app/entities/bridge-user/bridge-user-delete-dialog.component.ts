import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBridgeUser } from 'app/shared/model/bridge-user.model';
import { BridgeUserService } from './bridge-user.service';

@Component({
    selector: 'jhi-bridge-user-delete-dialog',
    templateUrl: './bridge-user-delete-dialog.component.html'
})
export class BridgeUserDeleteDialogComponent {
    bridgeUser: IBridgeUser;

    constructor(private bridgeUserService: BridgeUserService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bridgeUserService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bridgeUserListModification',
                content: 'Deleted an bridgeUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bridge-user-delete-popup',
    template: ''
})
export class BridgeUserDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bridgeUser }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BridgeUserDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.bridgeUser = bridgeUser;
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
