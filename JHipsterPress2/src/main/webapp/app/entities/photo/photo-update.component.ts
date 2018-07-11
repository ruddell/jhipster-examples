import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IPhoto } from 'app/shared/model/photo.model';
import { PhotoService } from './photo.service';
import { IAlbum } from 'app/shared/model/album.model';
import { AlbumService } from 'app/entities/album';

@Component({
    selector: 'jhi-photo-update',
    templateUrl: './photo-update.component.html'
})
export class PhotoUpdateComponent implements OnInit {
    private _photo: IPhoto;
    isSaving: boolean;

    albums: IAlbum[];
    creationDate: string;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private photoService: PhotoService,
        private albumService: AlbumService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ photo }) => {
            this.photo = photo;
        });
        this.albumService.query().subscribe(
            (res: HttpResponse<IAlbum[]>) => {
                this.albums = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.photo, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.photo.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
        if (this.photo.id !== undefined) {
            this.subscribeToSaveResponse(this.photoService.update(this.photo));
        } else {
            this.subscribeToSaveResponse(this.photoService.create(this.photo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPhoto>>) {
        result.subscribe((res: HttpResponse<IPhoto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAlbumById(index: number, item: IAlbum) {
        return item.id;
    }
    get photo() {
        return this._photo;
    }

    set photo(photo: IPhoto) {
        this._photo = photo;
        this.creationDate = moment(photo.creationDate).format(DATE_TIME_FORMAT);
    }
}
