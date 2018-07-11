import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IAlbum } from 'app/shared/model/album.model';
import { AlbumService } from './album.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party';

@Component({
    selector: 'jhi-album-update',
    templateUrl: './album-update.component.html'
})
export class AlbumUpdateComponent implements OnInit {
    private _album: IAlbum;
    isSaving: boolean;

    parties: IParty[];
    creationDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private albumService: AlbumService,
        private partyService: PartyService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ album }) => {
            this.album = album;
        });
        this.partyService.query({ filter: 'album-is-null' }).subscribe(
            (res: HttpResponse<IParty[]>) => {
                if (!this.album.partyId) {
                    this.parties = res.body;
                } else {
                    this.partyService.find(this.album.partyId).subscribe(
                        (subRes: HttpResponse<IParty>) => {
                            this.parties = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.album.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
        if (this.album.id !== undefined) {
            this.subscribeToSaveResponse(this.albumService.update(this.album));
        } else {
            this.subscribeToSaveResponse(this.albumService.create(this.album));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAlbum>>) {
        result.subscribe((res: HttpResponse<IAlbum>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPartyById(index: number, item: IParty) {
        return item.id;
    }
    get album() {
        return this._album;
    }

    set album(album: IAlbum) {
        this._album = album;
        this.creationDate = moment(album.creationDate).format(DATE_TIME_FORMAT);
    }
}
