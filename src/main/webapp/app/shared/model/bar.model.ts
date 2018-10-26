import { IFoo } from 'app/shared/model//foo.model';

export interface IBar {
    id?: number;
    name?: string;
    foo?: IFoo;
}

export class Bar implements IBar {
    constructor(public id?: number, public name?: string, public foo?: IFoo) {}
}
