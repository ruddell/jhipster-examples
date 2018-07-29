import { Moment } from 'moment';
import { IComment } from 'app/shared/model//comment.model';

export interface IPost {
    id?: number;
    creationDate?: Moment;
    headline?: string;
    bodytext?: string;
    imageContentType?: string;
    image?: any;
    comments?: IComment[];
    blogId?: number;
}

export class Post implements IPost {
    constructor(
        public id?: number,
        public creationDate?: Moment,
        public headline?: string,
        public bodytext?: string,
        public imageContentType?: string,
        public image?: any,
        public comments?: IComment[],
        public blogId?: number
    ) {}
}
