import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IBar } from 'app/shared/model/bar.model';
import { BarService } from './bar.service';

@Component({
    selector: 'jhi-bar-update',
    templateUrl: './bar-update.component.html'
})
export class BarUpdateComponent implements OnInit {
    bar: IBar;
    isSaving: boolean;

    constructor(private barService: BarService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bar }) => {
            this.bar = bar;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bar.id !== undefined) {
            this.subscribeToSaveResponse(this.barService.update(this.bar));
        } else {
            this.subscribeToSaveResponse(this.barService.create(this.bar));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBar>>) {
        result.subscribe((res: HttpResponse<IBar>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
