import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBridgeAccountOnlineBank } from 'app/shared/model/bridge-account-online-bank.model';

@Component({
    selector: 'jhi-bridge-account-online-bank-detail',
    templateUrl: './bridge-account-online-bank-detail.component.html'
})
export class BridgeAccountOnlineBankDetailComponent implements OnInit {
    bridgeAccountOnlineBank: IBridgeAccountOnlineBank;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bridgeAccountOnlineBank }) => {
            this.bridgeAccountOnlineBank = bridgeAccountOnlineBank;
        });
    }

    previousState() {
        window.history.back();
    }
}
