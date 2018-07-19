import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IBridgeAccountBank } from 'app/shared/model/bridge-account-bank.model';
import { BridgeAccountBankService } from './bridge-account-bank.service';
import { IBridgeBank } from 'app/shared/model/bridge-bank.model';
import { BridgeBankService } from 'app/entities/bridge-bank';
import { IBridgeAccountOnlineBank } from 'app/shared/model/bridge-account-online-bank.model';
import { BridgeAccountOnlineBankService } from 'app/entities/bridge-account-online-bank';

@Component({
    selector: 'jhi-bridge-account-bank-update',
    templateUrl: './bridge-account-bank-update.component.html'
})
export class BridgeAccountBankUpdateComponent implements OnInit {
    private _bridgeAccountBank: IBridgeAccountBank;
    isSaving: boolean;

    bridgebanks: IBridgeBank[];

    bridgeaccountonlinebanks: IBridgeAccountOnlineBank[];
    updatedAt: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private bridgeAccountBankService: BridgeAccountBankService,
        private bridgeBankService: BridgeBankService,
        private bridgeAccountOnlineBankService: BridgeAccountOnlineBankService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bridgeAccountBank }) => {
            this.bridgeAccountBank = bridgeAccountBank;
        });
        this.bridgeBankService.query().subscribe(
            (res: HttpResponse<IBridgeBank[]>) => {
                this.bridgebanks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.bridgeAccountOnlineBankService.query().subscribe(
            (res: HttpResponse<IBridgeAccountOnlineBank[]>) => {
                this.bridgeaccountonlinebanks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.bridgeAccountBank.updatedAt = moment(this.updatedAt, DATE_TIME_FORMAT);
        if (this.bridgeAccountBank.id !== undefined) {
            this.subscribeToSaveResponse(this.bridgeAccountBankService.update(this.bridgeAccountBank));
        } else {
            this.subscribeToSaveResponse(this.bridgeAccountBankService.create(this.bridgeAccountBank));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBridgeAccountBank>>) {
        result.subscribe((res: HttpResponse<IBridgeAccountBank>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBridgeBankById(index: number, item: IBridgeBank) {
        return item.id;
    }

    trackBridgeAccountOnlineBankById(index: number, item: IBridgeAccountOnlineBank) {
        return item.id;
    }
    get bridgeAccountBank() {
        return this._bridgeAccountBank;
    }

    set bridgeAccountBank(bridgeAccountBank: IBridgeAccountBank) {
        this._bridgeAccountBank = bridgeAccountBank;
        this.updatedAt = moment(bridgeAccountBank.updatedAt).format(DATE_TIME_FORMAT);
    }
}
