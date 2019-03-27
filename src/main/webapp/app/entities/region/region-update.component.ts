import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IRegion, Region } from 'app/shared/model/region.model';
import { RegionService } from './region.service';

@Component({
  selector: 'jhi-region-update',
  templateUrl: './region-update.component.html'
})
export class RegionUpdateComponent implements OnInit {
  region: IRegion;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    regionName: []
  });

  constructor(protected regionService: RegionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ region }) => {
      this.updateForm(region);
      this.region = region;
    });
  }

  updateForm(region: IRegion) {
    this.editForm.patchValue({
      id: region.id,
      regionName: region.regionName
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const region = this.createFromForm();
    if (region.id !== undefined) {
      this.subscribeToSaveResponse(this.regionService.update(region));
    } else {
      this.subscribeToSaveResponse(this.regionService.create(region));
    }
  }

  private createFromForm(): IRegion {
    const entity = {
      ...new Region(),
      id: this.editForm.get(['id']).value,
      regionName: this.editForm.get(['regionName']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegion>>) {
    result.subscribe((res: HttpResponse<IRegion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
