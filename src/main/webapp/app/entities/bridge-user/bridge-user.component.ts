import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBridgeUser } from 'app/shared/model/bridge-user.model';
import { Principal } from 'app/core';
import { BridgeUserService } from './bridge-user.service';

@Component({
    selector: 'jhi-bridge-user',
    templateUrl: './bridge-user.component.html'
})
export class BridgeUserComponent implements OnInit, OnDestroy {
    bridgeUsers: IBridgeUser[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bridgeUserService: BridgeUserService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.bridgeUserService.query().subscribe(
            (res: HttpResponse<IBridgeUser[]>) => {
                this.bridgeUsers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBridgeUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBridgeUser) {
        return item.id;
    }

    registerChangeInBridgeUsers() {
        this.eventSubscriber = this.eventManager.subscribe('bridgeUserListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
