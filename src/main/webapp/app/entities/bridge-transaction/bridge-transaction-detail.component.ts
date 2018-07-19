import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBridgeTransaction } from 'app/shared/model/bridge-transaction.model';

@Component({
    selector: 'jhi-bridge-transaction-detail',
    templateUrl: './bridge-transaction-detail.component.html'
})
export class BridgeTransactionDetailComponent implements OnInit {
    bridgeTransaction: IBridgeTransaction;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bridgeTransaction }) => {
            this.bridgeTransaction = bridgeTransaction;
        });
    }

    previousState() {
        window.history.back();
    }
}
