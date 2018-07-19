import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccountBankStat } from 'app/shared/model/account-bank-stat.model';
import { AccountBankStatService } from './account-bank-stat.service';

@Component({
    selector: 'jhi-account-bank-stat-delete-dialog',
    templateUrl: './account-bank-stat-delete-dialog.component.html'
})
export class AccountBankStatDeleteDialogComponent {
    accountBankStat: IAccountBankStat;

    constructor(
        private accountBankStatService: AccountBankStatService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.accountBankStatService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'accountBankStatListModification',
                content: 'Deleted an accountBankStat'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-account-bank-stat-delete-popup',
    template: ''
})
export class AccountBankStatDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ accountBankStat }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AccountBankStatDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.accountBankStat = accountBankStat;
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
