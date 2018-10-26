import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IFoo } from 'app/shared/model/foo.model';
import { FooService } from './foo.service';

@Component({
    selector: 'jhi-foo-update',
    templateUrl: './foo-update.component.html'
})
export class FooUpdateComponent implements OnInit {
    foo: IFoo;
    isSaving: boolean;

    constructor(private fooService: FooService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ foo }) => {
            this.foo = foo;
        });
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
}
