import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBridgeAccountBank } from 'app/shared/model/bridge-account-bank.model';
import { Principal } from 'app/core';
import { BridgeAccountBankService } from './bridge-account-bank.service';

@Component({
    selector: 'jhi-bridge-account-bank',
    templateUrl: './bridge-account-bank.component.html'
})
export class BridgeAccountBankComponent implements OnInit, OnDestroy {
    bridgeAccountBanks: IBridgeAccountBank[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bridgeAccountBankService: BridgeAccountBankService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.bridgeAccountBankService.query().subscribe(
            (res: HttpResponse<IBridgeAccountBank[]>) => {
                this.bridgeAccountBanks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBridgeAccountBanks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBridgeAccountBank) {
        return item.id;
    }

    registerChangeInBridgeAccountBanks() {
        this.eventSubscriber = this.eventManager.subscribe('bridgeAccountBankListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
