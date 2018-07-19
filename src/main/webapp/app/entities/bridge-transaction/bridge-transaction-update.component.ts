import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IBridgeTransaction } from 'app/shared/model/bridge-transaction.model';
import { BridgeTransactionService } from './bridge-transaction.service';
import { IBridgeCategory } from 'app/shared/model/bridge-category.model';
import { BridgeCategoryService } from 'app/entities/bridge-category';
import { IBridgeAccountBank } from 'app/shared/model/bridge-account-bank.model';
import { BridgeAccountBankService } from 'app/entities/bridge-account-bank';

@Component({
    selector: 'jhi-bridge-transaction-update',
    templateUrl: './bridge-transaction-update.component.html'
})
export class BridgeTransactionUpdateComponent implements OnInit {
    private _bridgeTransaction: IBridgeTransaction;
    isSaving: boolean;

    bridgecategories: IBridgeCategory[];

    bridgeaccountbanks: IBridgeAccountBank[];
    dateDp: any;
    updatedAt: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private bridgeTransactionService: BridgeTransactionService,
        private bridgeCategoryService: BridgeCategoryService,
        private bridgeAccountBankService: BridgeAccountBankService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bridgeTransaction }) => {
            this.bridgeTransaction = bridgeTransaction;
        });
        this.bridgeCategoryService.query().subscribe(
            (res: HttpResponse<IBridgeCategory[]>) => {
                this.bridgecategories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        this.bridgeTransaction.updatedAt = moment(this.updatedAt, DATE_TIME_FORMAT);
        if (this.bridgeTransaction.id !== undefined) {
            this.subscribeToSaveResponse(this.bridgeTransactionService.update(this.bridgeTransaction));
        } else {
            this.subscribeToSaveResponse(this.bridgeTransactionService.create(this.bridgeTransaction));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBridgeTransaction>>) {
        result.subscribe((res: HttpResponse<IBridgeTransaction>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBridgeCategoryById(index: number, item: IBridgeCategory) {
        return item.id;
    }

    trackBridgeAccountBankById(index: number, item: IBridgeAccountBank) {
        return item.id;
    }
    get bridgeTransaction() {
        return this._bridgeTransaction;
    }

    set bridgeTransaction(bridgeTransaction: IBridgeTransaction) {
        this._bridgeTransaction = bridgeTransaction;
        this.updatedAt = moment(bridgeTransaction.updatedAt).format(DATE_TIME_FORMAT);
    }
}
