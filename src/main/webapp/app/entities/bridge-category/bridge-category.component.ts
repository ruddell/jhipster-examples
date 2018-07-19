import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBridgeCategory } from 'app/shared/model/bridge-category.model';
import { Principal } from 'app/core';
import { BridgeCategoryService } from './bridge-category.service';

@Component({
    selector: 'jhi-bridge-category',
    templateUrl: './bridge-category.component.html'
})
export class BridgeCategoryComponent implements OnInit, OnDestroy {
    bridgeCategories: IBridgeCategory[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bridgeCategoryService: BridgeCategoryService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.bridgeCategoryService.query().subscribe(
            (res: HttpResponse<IBridgeCategory[]>) => {
                this.bridgeCategories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBridgeCategories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBridgeCategory) {
        return item.id;
    }

    registerChangeInBridgeCategories() {
        this.eventSubscriber = this.eventManager.subscribe('bridgeCategoryListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
