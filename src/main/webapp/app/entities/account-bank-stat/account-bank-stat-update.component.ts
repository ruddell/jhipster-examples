import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IAccountBankStat } from 'app/shared/model/account-bank-stat.model';
import { AccountBankStatService } from './account-bank-stat.service';
import { IBridgeAccountBank } from 'app/shared/model/bridge-account-bank.model';
import { BridgeAccountBankService } from 'app/entities/bridge-account-bank';

@Component({
    selector: 'jhi-account-bank-stat-update',
    templateUrl: './account-bank-stat-update.component.html'
})
export class AccountBankStatUpdateComponent implements OnInit {
    private _accountBankStat: IAccountBankStat;
    isSaving: boolean;

    bridgeaccountbanks: IBridgeAccountBank[];
    lastRefresh: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private accountBankStatService: AccountBankStatService,
        private bridgeAccountBankService: BridgeAccountBankService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ accountBankStat }) => {
            this.accountBankStat = accountBankStat;
        });
        this.bridgeAccountBankService.query().subscribe(
            (res: HttpResponse<IBridgeAccountBank[]>) => {
                this.bridgeaccountbanks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.accountBankStat.lastRefresh = moment(this.lastRefresh, DATE_TIME_FORMAT);
        if (this.accountBankStat.id !== undefined) {
            this.subscribeToSaveResponse(this.accountBankStatService.update(this.accountBankStat));
        } else {
            this.subscribeToSaveResponse(this.accountBankStatService.create(this.accountBankStat));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAccountBankStat>>) {
        result.subscribe((res: HttpResponse<IAccountBankStat>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackBridgeAccountBankById(index: number, item: IBridgeAccountBank) {
        return item.id;
    }
    get accountBankStat() {
        return this._accountBankStat;
    }

    set accountBankStat(accountBankStat: IAccountBankStat) {
        this._accountBankStat = accountBankStat;
        this.lastRefresh = moment(accountBankStat.lastRefresh).format(DATE_TIME_FORMAT);
    }
}
