import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBridgeCategory } from 'app/shared/model/bridge-category.model';
import { BridgeCategoryService } from './bridge-category.service';

@Component({
    selector: 'jhi-bridge-category-update',
    templateUrl: './bridge-category-update.component.html'
})
export class BridgeCategoryUpdateComponent implements OnInit {
    private _bridgeCategory: IBridgeCategory;
    isSaving: boolean;

    bridgecategories: IBridgeCategory[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private bridgeCategoryService: BridgeCategoryService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bridgeCategory }) => {
            this.bridgeCategory = bridgeCategory;
        });
        this.bridgeCategoryService.query().subscribe(
            (res: HttpResponse<IBridgeCategory[]>) => {
                this.bridgecategories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bridgeCategory.id !== undefined) {
            this.subscribeToSaveResponse(this.bridgeCategoryService.update(this.bridgeCategory));
        } else {
            this.subscribeToSaveResponse(this.bridgeCategoryService.create(this.bridgeCategory));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBridgeCategory>>) {
        result.subscribe((res: HttpResponse<IBridgeCategory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get bridgeCategory() {
        return this._bridgeCategory;
    }

    set bridgeCategory(bridgeCategory: IBridgeCategory) {
        this._bridgeCategory = bridgeCategory;
    }
}
