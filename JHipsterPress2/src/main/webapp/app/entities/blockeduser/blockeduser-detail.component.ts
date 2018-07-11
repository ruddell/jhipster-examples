import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBlockeduser } from 'app/shared/model/blockeduser.model';

@Component({
    selector: 'jhi-blockeduser-detail',
    templateUrl: './blockeduser-detail.component.html'
})
export class BlockeduserDetailComponent implements OnInit {
    blockeduser: IBlockeduser;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ blockeduser }) => {
            this.blockeduser = blockeduser;
        });
    }

    previousState() {
        window.history.back();
    }
}
