import { Component, OnInit, Optional } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

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
    name: [],
  });

  constructor(
    protected fooService: FooService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    @Optional() public activeModal?: NgbActiveModal
  ) {}

  ngOnInit(): void {
    if (this.activeModal) return;
    this.activatedRoute.data.subscribe(({ foo }) => {
      this.updateForm(foo);
    });
  }

  updateForm(foo: IFoo): void {
    this.editForm.patchValue({
      id: foo.id,
      name: foo.name,
    });
  }

  previousState(): void {
    if (this.activeModal) {
      this.activeModal.close();
    } else {
      window.history.back();
    }
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
      name: this.editForm.get(['name'])!.value,
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
