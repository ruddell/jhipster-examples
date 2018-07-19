import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBridgeCategory } from 'app/shared/model/bridge-category.model';

@Component({
    selector: 'jhi-bridge-category-detail',
    templateUrl: './bridge-category-detail.component.html'
})
export class BridgeCategoryDetailComponent implements OnInit {
    bridgeCategory: IBridgeCategory;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bridgeCategory }) => {
            this.bridgeCategory = bridgeCategory;
        });
    }

    previousState() {
        window.history.back();
    }
}
