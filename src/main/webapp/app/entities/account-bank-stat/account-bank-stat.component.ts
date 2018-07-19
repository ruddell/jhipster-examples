import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAccountBankStat } from 'app/shared/model/account-bank-stat.model';
import { Principal } from 'app/core';
import { AccountBankStatService } from './account-bank-stat.service';

@Component({
    selector: 'jhi-account-bank-stat',
    templateUrl: './account-bank-stat.component.html'
})
export class AccountBankStatComponent implements OnInit, OnDestroy {
    accountBankStats: IAccountBankStat[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private accountBankStatService: AccountBankStatService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.accountBankStatService.query().subscribe(
            (res: HttpResponse<IAccountBankStat[]>) => {
                this.accountBankStats = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAccountBankStats();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAccountBankStat) {
        return item.id;
    }

    registerChangeInAccountBankStats() {
        this.eventSubscriber = this.eventManager.subscribe('accountBankStatListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
