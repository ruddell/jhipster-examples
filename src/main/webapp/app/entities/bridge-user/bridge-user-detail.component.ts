import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBridgeUser } from 'app/shared/model/bridge-user.model';

@Component({
    selector: 'jhi-bridge-user-detail',
    templateUrl: './bridge-user-detail.component.html'
})
export class BridgeUserDetailComponent implements OnInit {
    bridgeUser: IBridgeUser;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bridgeUser }) => {
            this.bridgeUser = bridgeUser;
        });
    }

    previousState() {
        window.history.back();
    }
}
