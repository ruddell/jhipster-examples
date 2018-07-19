import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IBridgeBank } from 'app/shared/model/bridge-bank.model';
import { BridgeBankService } from './bridge-bank.service';

@Component({
    selector: 'jhi-bridge-bank-update',
    templateUrl: './bridge-bank-update.component.html'
})
export class BridgeBankUpdateComponent implements OnInit {
    private _bridgeBank: IBridgeBank;
    isSaving: boolean;

    constructor(private bridgeBankService: BridgeBankService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bridgeBank }) => {
            this.bridgeBank = bridgeBank;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bridgeBank.id !== undefined) {
            this.subscribeToSaveResponse(this.bridgeBankService.update(this.bridgeBank));
        } else {
            this.subscribeToSaveResponse(this.bridgeBankService.create(this.bridgeBank));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBridgeBank>>) {
        result.subscribe((res: HttpResponse<IBridgeBank>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get bridgeBank() {
        return this._bridgeBank;
    }

    set bridgeBank(bridgeBank: IBridgeBank) {
        this._bridgeBank = bridgeBank;
    }
}
