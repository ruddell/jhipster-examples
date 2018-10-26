import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFoo } from 'app/shared/model/foo.model';
import { Principal } from 'app/core';
import { FooService } from './foo.service';

@Component({
    selector: 'jhi-foo',
    templateUrl: './foo.component.html'
})
export class FooComponent implements OnInit, OnDestroy {
    foos: IFoo[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private fooService: FooService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.fooService.query().subscribe(
            (res: HttpResponse<IFoo[]>) => {
                this.foos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFoos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFoo) {
        return item.id;
    }

    registerChangeInFoos() {
        this.eventSubscriber = this.eventManager.subscribe('fooListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
