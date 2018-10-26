import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBar } from 'app/shared/model/bar.model';

@Component({
    selector: 'jhi-bar-detail',
    templateUrl: './bar-detail.component.html'
})
export class BarDetailComponent implements OnInit {
    bar: IBar;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bar }) => {
            this.bar = bar;
        });
    }

    previousState() {
        window.history.back();
    }
}
