import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IUrllink } from 'app/shared/model/urllink.model';
import { UrllinkService } from './urllink.service';

@Component({
    selector: 'jhi-urllink-update',
    templateUrl: './urllink-update.component.html'
})
export class UrllinkUpdateComponent implements OnInit {
    private _urllink: IUrllink;
    isSaving: boolean;

    constructor(private urllinkService: UrllinkService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ urllink }) => {
            this.urllink = urllink;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.urllink.id !== undefined) {
            this.subscribeToSaveResponse(this.urllinkService.update(this.urllink));
        } else {
            this.subscribeToSaveResponse(this.urllinkService.create(this.urllink));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUrllink>>) {
        result.subscribe((res: HttpResponse<IUrllink>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get urllink() {
        return this._urllink;
    }

    set urllink(urllink: IUrllink) {
        this._urllink = urllink;
    }
}
