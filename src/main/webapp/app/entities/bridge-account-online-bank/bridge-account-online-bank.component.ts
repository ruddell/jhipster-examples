import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBridgeAccountOnlineBank } from 'app/shared/model/bridge-account-online-bank.model';
import { Principal } from 'app/core';
import { BridgeAccountOnlineBankService } from './bridge-account-online-bank.service';

@Component({
    selector: 'jhi-bridge-account-online-bank',
    templateUrl: './bridge-account-online-bank.component.html'
})
export class BridgeAccountOnlineBankComponent implements OnInit, OnDestroy {
    bridgeAccountOnlineBanks: IBridgeAccountOnlineBank[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bridgeAccountOnlineBankService: BridgeAccountOnlineBankService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.bridgeAccountOnlineBankService.query().subscribe(
            (res: HttpResponse<IBridgeAccountOnlineBank[]>) => {
                this.bridgeAccountOnlineBanks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBridgeAccountOnlineBanks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBridgeAccountOnlineBank) {
        return item.id;
    }

    registerChangeInBridgeAccountOnlineBanks() {
        this.eventSubscriber = this.eventManager.subscribe('bridgeAccountOnlineBankListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
