import { Moment } from 'moment';
import { IPost } from 'app/shared/model//post.model';

export interface IBlog {
    id?: number;
    creationDate?: Moment;
    title?: string;
    posts?: IPost[];
}

export class Blog implements IBlog {
    constructor(public id?: number, public creationDate?: Moment, public title?: string, public posts?: IPost[]) {}
}
