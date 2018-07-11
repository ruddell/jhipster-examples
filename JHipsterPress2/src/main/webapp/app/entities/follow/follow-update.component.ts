import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IFollow } from 'app/shared/model/follow.model';
import { FollowService } from './follow.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party';

@Component({
    selector: 'jhi-follow-update',
    templateUrl: './follow-update.component.html'
})
export class FollowUpdateComponent implements OnInit {
    private _follow: IFollow;
    isSaving: boolean;

    parties: IParty[];
    creationDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private followService: FollowService,
        private partyService: PartyService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ follow }) => {
            this.follow = follow;
        });
        this.partyService.query().subscribe(
            (res: HttpResponse<IParty[]>) => {
                this.parties = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.follow.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
        if (this.follow.id !== undefined) {
            this.subscribeToSaveResponse(this.followService.update(this.follow));
        } else {
            this.subscribeToSaveResponse(this.followService.create(this.follow));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFollow>>) {
        result.subscribe((res: HttpResponse<IFollow>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get follow() {
        return this._follow;
    }

    set follow(follow: IFollow) {
        this._follow = follow;
        this.creationDate = moment(follow.creationDate).format(DATE_TIME_FORMAT);
    }
}
