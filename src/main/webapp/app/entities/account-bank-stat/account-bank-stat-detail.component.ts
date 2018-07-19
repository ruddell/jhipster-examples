import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountBankStat } from 'app/shared/model/account-bank-stat.model';

@Component({
    selector: 'jhi-account-bank-stat-detail',
    templateUrl: './account-bank-stat-detail.component.html'
})
export class AccountBankStatDetailComponent implements OnInit {
    accountBankStat: IAccountBankStat;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ accountBankStat }) => {
            this.accountBankStat = accountBankStat;
        });
    }

    previousState() {
        window.history.back();
    }
}
