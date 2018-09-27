import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFoo } from 'app/shared/model/foo.model';

@Component({
    selector: 'jhi-foo-detail',
    templateUrl: './foo-detail.component.html'
})
export class FooDetailComponent implements OnInit {
    foo: IFoo;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ foo }) => {
            this.foo = foo;
        });
    }

    previousState() {
        window.history.back();
    }
}
