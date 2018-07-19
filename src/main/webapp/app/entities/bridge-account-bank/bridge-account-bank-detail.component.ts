import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBridgeAccountBank } from 'app/shared/model/bridge-account-bank.model';

@Component({
    selector: 'jhi-bridge-account-bank-detail',
    templateUrl: './bridge-account-bank-detail.component.html'
})
export class BridgeAccountBankDetailComponent implements OnInit {
    bridgeAccountBank: IBridgeAccountBank;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bridgeAccountBank }) => {
            this.bridgeAccountBank = bridgeAccountBank;
        });
    }

    previousState() {
        window.history.back();
    }
}
