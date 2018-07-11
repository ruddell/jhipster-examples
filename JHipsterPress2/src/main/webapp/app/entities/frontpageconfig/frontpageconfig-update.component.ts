import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFrontpageconfig } from 'app/shared/model/frontpageconfig.model';
import { FrontpageconfigService } from './frontpageconfig.service';

@Component({
    selector: 'jhi-frontpageconfig-update',
    templateUrl: './frontpageconfig-update.component.html'
})
export class FrontpageconfigUpdateComponent implements OnInit {
    private _frontpageconfig: IFrontpageconfig;
    isSaving: boolean;
    creationDate: string;

    constructor(private frontpageconfigService: FrontpageconfigService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ frontpageconfig }) => {
            this.frontpageconfig = frontpageconfig;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.frontpageconfig.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
        if (this.frontpageconfig.id !== undefined) {
            this.subscribeToSaveResponse(this.frontpageconfigService.update(this.frontpageconfig));
        } else {
            this.subscribeToSaveResponse(this.frontpageconfigService.create(this.frontpageconfig));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFrontpageconfig>>) {
        result.subscribe((res: HttpResponse<IFrontpageconfig>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get frontpageconfig() {
        return this._frontpageconfig;
    }

    set frontpageconfig(frontpageconfig: IFrontpageconfig) {
        this._frontpageconfig = frontpageconfig;
        this.creationDate = moment(frontpageconfig.creationDate).format(DATE_TIME_FORMAT);
    }
}
