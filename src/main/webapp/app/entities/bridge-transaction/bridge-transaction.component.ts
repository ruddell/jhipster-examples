import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBridgeTransaction } from 'app/shared/model/bridge-transaction.model';
import { Principal } from 'app/core';
import { BridgeTransactionService } from './bridge-transaction.service';

@Component({
    selector: 'jhi-bridge-transaction',
    templateUrl: './bridge-transaction.component.html'
})
export class BridgeTransactionComponent implements OnInit, OnDestroy {
    bridgeTransactions: IBridgeTransaction[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bridgeTransactionService: BridgeTransactionService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.bridgeTransactionService.query().subscribe(
            (res: HttpResponse<IBridgeTransaction[]>) => {
                this.bridgeTransactions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBridgeTransactions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBridgeTransaction) {
        return item.id;
    }

    registerChangeInBridgeTransactions() {
        this.eventSubscriber = this.eventManager.subscribe('bridgeTransactionListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
