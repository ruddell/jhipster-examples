import { IFoo } from 'app/shared/model//foo.model';

export interface IBar {
    id?: number;
    name?: string;
    foos?: IFoo[];
}

export class Bar implements IBar {
    constructor(public id?: number, public name?: string, public foos?: IFoo[]) {}
}
