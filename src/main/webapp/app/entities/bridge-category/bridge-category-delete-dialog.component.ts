import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBridgeCategory } from 'app/shared/model/bridge-category.model';
import { BridgeCategoryService } from './bridge-category.service';

@Component({
    selector: 'jhi-bridge-category-delete-dialog',
    templateUrl: './bridge-category-delete-dialog.component.html'
})
export class BridgeCategoryDeleteDialogComponent {
    bridgeCategory: IBridgeCategory;

    constructor(
        private bridgeCategoryService: BridgeCategoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bridgeCategoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bridgeCategoryListModification',
                content: 'Deleted an bridgeCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bridge-category-delete-popup',
    template: ''
})
export class BridgeCategoryDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bridgeCategory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BridgeCategoryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.bridgeCategory = bridgeCategory;
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
