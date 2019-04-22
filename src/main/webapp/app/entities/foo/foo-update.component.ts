import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IFoo, Foo } from 'app/shared/model/foo.model';
import { FooService } from './foo.service';

@Component({
  selector: 'jhi-foo-update',
  templateUrl: './foo-update.component.html'
})
export class FooUpdateComponent implements OnInit {
  foo: IFoo;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: []
  });

  constructor(protected fooService: FooService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ foo }) => {
      this.updateForm(foo);
      this.foo = foo;
    });
  }

  updateForm(foo: IFoo) {
    this.editForm.patchValue({
      id: foo.id,
      name: foo.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const foo = this.createFromForm();
    if (foo.id !== undefined) {
      this.subscribeToSaveResponse(this.fooService.update(foo));
    } else {
      this.subscribeToSaveResponse(this.fooService.create(foo));
    }
  }

  private createFromForm(): IFoo {
    const entity = {
      ...new Foo(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFoo>>) {
    result.subscribe((res: HttpResponse<IFoo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
