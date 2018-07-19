import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBridgeUser } from 'app/shared/model/bridge-user.model';
import { BridgeUserService } from './bridge-user.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-bridge-user-update',
    templateUrl: './bridge-user-update.component.html'
})
export class BridgeUserUpdateComponent implements OnInit {
    private _bridgeUser: IBridgeUser;
    isSaving: boolean;

    users: IUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private bridgeUserService: BridgeUserService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bridgeUser }) => {
            this.bridgeUser = bridgeUser;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bridgeUser.id !== undefined) {
            this.subscribeToSaveResponse(this.bridgeUserService.update(this.bridgeUser));
        } else {
            this.subscribeToSaveResponse(this.bridgeUserService.create(this.bridgeUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBridgeUser>>) {
        result.subscribe((res: HttpResponse<IBridgeUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get bridgeUser() {
        return this._bridgeUser;
    }

    set bridgeUser(bridgeUser: IBridgeUser) {
        this._bridgeUser = bridgeUser;
    }
}
