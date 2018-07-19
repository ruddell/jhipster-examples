import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBridgeAccountOnlineBank } from 'app/shared/model/bridge-account-online-bank.model';
import { BridgeAccountOnlineBankService } from './bridge-account-online-bank.service';
import { IBridgeBank } from 'app/shared/model/bridge-bank.model';
import { BridgeBankService } from 'app/entities/bridge-bank';
import { IBridgeUser } from 'app/shared/model/bridge-user.model';
import { BridgeUserService } from 'app/entities/bridge-user';

@Component({
    selector: 'jhi-bridge-account-online-bank-update',
    templateUrl: './bridge-account-online-bank-update.component.html'
})
export class BridgeAccountOnlineBankUpdateComponent implements OnInit {
    private _bridgeAccountOnlineBank: IBridgeAccountOnlineBank;
    isSaving: boolean;

    bridgebanks: IBridgeBank[];

    bridgeusers: IBridgeUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private bridgeAccountOnlineBankService: BridgeAccountOnlineBankService,
        private bridgeBankService: BridgeBankService,
        private bridgeUserService: BridgeUserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bridgeAccountOnlineBank }) => {
            this.bridgeAccountOnlineBank = bridgeAccountOnlineBank;
        });
        this.bridgeBankService.query().subscribe(
            (res: HttpResponse<IBridgeBank[]>) => {
                this.bridgebanks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.bridgeUserService.query().subscribe(
            (res: HttpResponse<IBridgeUser[]>) => {
                this.bridgeusers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bridgeAccountOnlineBank.id !== undefined) {
            this.subscribeToSaveResponse(this.bridgeAccountOnlineBankService.update(this.bridgeAccountOnlineBank));
        } else {
            this.subscribeToSaveResponse(this.bridgeAccountOnlineBankService.create(this.bridgeAccountOnlineBank));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBridgeAccountOnlineBank>>) {
        result.subscribe(
            (res: HttpResponse<IBridgeAccountOnlineBank>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackBridgeUserById(index: number, item: IBridgeUser) {
        return item.id;
    }
    get bridgeAccountOnlineBank() {
        return this._bridgeAccountOnlineBank;
    }

    set bridgeAccountOnlineBank(bridgeAccountOnlineBank: IBridgeAccountOnlineBank) {
        this._bridgeAccountOnlineBank = bridgeAccountOnlineBank;
    }
}
