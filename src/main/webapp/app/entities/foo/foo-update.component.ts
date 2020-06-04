import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFoo, Foo } from 'app/shared/model/foo.model';
import { FooService } from './foo.service';

@Component({
  selector: 'jhi-foo-update',
  templateUrl: './foo-update.component.html',
})
export class FooUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    test: [],
  });

  constructor(protected fooService: FooService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ foo }) => {
      this.updateForm(foo);
    });
  }

  updateForm(foo: IFoo): void {
    this.editForm.patchValue({
      id: foo.id,
      test: foo.test,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const foo = this.createFromForm();
    if (foo.id !== undefined) {
      this.subscribeToSaveResponse(this.fooService.update(foo));
    } else {
      this.subscribeToSaveResponse(this.fooService.create(foo));
    }
  }

  private createFromForm(): IFoo {
    return {
      ...new Foo(),
      id: this.editForm.get(['id'])!.value,
      test: this.editForm.get(['test'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFoo>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
