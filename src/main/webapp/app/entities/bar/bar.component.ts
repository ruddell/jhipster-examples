import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBar } from 'app/shared/model/bar.model';
import { Principal } from 'app/core';
import { BarService } from './bar.service';

@Component({
    selector: 'jhi-bar',
    templateUrl: './bar.component.html'
})
export class BarComponent implements OnInit, OnDestroy {
    bars: IBar[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private barService: BarService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.barService.query().subscribe(
            (res: HttpResponse<IBar[]>) => {
                this.bars = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBars();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBar) {
        return item.id;
    }

    registerChangeInBars() {
        this.eventSubscriber = this.eventManager.subscribe('barListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
