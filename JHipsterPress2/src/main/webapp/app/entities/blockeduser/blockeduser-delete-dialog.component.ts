import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBlockeduser } from 'app/shared/model/blockeduser.model';
import { BlockeduserService } from './blockeduser.service';

@Component({
    selector: 'jhi-blockeduser-delete-dialog',
    templateUrl: './blockeduser-delete-dialog.component.html'
})
export class BlockeduserDeleteDialogComponent {
    blockeduser: IBlockeduser;

    constructor(
        private blockeduserService: BlockeduserService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.blockeduserService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'blockeduserListModification',
                content: 'Deleted an blockeduser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-blockeduser-delete-popup',
    template: ''
})
export class BlockeduserDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ blockeduser }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BlockeduserDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.blockeduser = blockeduser;
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
