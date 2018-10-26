import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFoo } from 'app/shared/model/foo.model';
import { FooService } from './foo.service';
import { IBar } from 'app/shared/model/bar.model';
import { BarService } from 'app/entities/bar';

@Component({
    selector: 'jhi-foo-update',
    templateUrl: './foo-update.component.html'
})
export class FooUpdateComponent implements OnInit {
    foo: IFoo;
    isSaving: boolean;

    bars: IBar[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private fooService: FooService,
        private barService: BarService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ foo }) => {
            this.foo = foo;
        });
        this.barService.query().subscribe(
            (res: HttpResponse<IBar[]>) => {
                this.bars = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.foo.id !== undefined) {
            this.subscribeToSaveResponse(this.fooService.update(this.foo));
        } else {
            this.subscribeToSaveResponse(this.fooService.create(this.foo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFoo>>) {
        result.subscribe((res: HttpResponse<IFoo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBarById(index: number, item: IBar) {
        return item.id;
    }
}
