import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBridgeBank } from 'app/shared/model/bridge-bank.model';

@Component({
    selector: 'jhi-bridge-bank-detail',
    templateUrl: './bridge-bank-detail.component.html'
})
export class BridgeBankDetailComponent implements OnInit {
    bridgeBank: IBridgeBank;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bridgeBank }) => {
            this.bridgeBank = bridgeBank;
        });
    }

    previousState() {
        window.history.back();
    }
}
