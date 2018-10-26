import { IBar } from 'app/shared/model//bar.model';

export interface IFoo {
    id?: number;
    fooName?: string;
    bar?: IBar;
}

export class Foo implements IFoo {
    constructor(public id?: number, public fooName?: string, public bar?: IBar) {}
}
