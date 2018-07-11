import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IParty } from 'app/shared/model/party.model';
import { PartyService } from './party.service';
import { IUser, UserService } from 'app/core';
import { IAlbum } from 'app/shared/model/album.model';
import { AlbumService } from 'app/entities/album';
import { IInterest } from 'app/shared/model/interest.model';
import { InterestService } from 'app/entities/interest';
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from 'app/entities/activity';
import { ICeleb } from 'app/shared/model/celeb.model';
import { CelebService } from 'app/entities/celeb';

@Component({
    selector: 'jhi-party-update',
    templateUrl: './party-update.component.html'
})
export class PartyUpdateComponent implements OnInit {
    private _party: IParty;
    isSaving: boolean;

    users: IUser[];

    albums: IAlbum[];

    interests: IInterest[];

    activities: IActivity[];

    celebs: ICeleb[];
    creationDate: string;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private partyService: PartyService,
        private userService: UserService,
        private albumService: AlbumService,
        private interestService: InterestService,
        private activityService: ActivityService,
        private celebService: CelebService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ party }) => {
            this.party = party;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.albumService.query().subscribe(
            (res: HttpResponse<IAlbum[]>) => {
                this.albums = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.interestService.query().subscribe(
            (res: HttpResponse<IInterest[]>) => {
                this.interests = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.activityService.query().subscribe(
            (res: HttpResponse<IActivity[]>) => {
                this.activities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.celebService.query().subscribe(
            (res: HttpResponse<ICeleb[]>) => {
                this.celebs = res.body;
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
        this.dataUtils.clearInputImage(this.party, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.party.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
        if (this.party.id !== undefined) {
            this.subscribeToSaveResponse(this.partyService.update(this.party));
        } else {
            this.subscribeToSaveResponse(this.partyService.create(this.party));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IParty>>) {
        result.subscribe((res: HttpResponse<IParty>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackAlbumById(index: number, item: IAlbum) {
        return item.id;
    }

    trackInterestById(index: number, item: IInterest) {
        return item.id;
    }

    trackActivityById(index: number, item: IActivity) {
        return item.id;
    }

    trackCelebById(index: number, item: ICeleb) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get party() {
        return this._party;
    }

    set party(party: IParty) {
        this._party = party;
        this.creationDate = moment(party.creationDate).format(DATE_TIME_FORMAT);
    }
}
