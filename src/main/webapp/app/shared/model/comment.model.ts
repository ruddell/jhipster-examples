import { Moment } from 'moment';

export interface IComment {
    id?: number;
    creationDate?: Moment;
    commentText?: string;
    postHeadline?: string;
    postId?: number;
}

export class Comment implements IComment {
    constructor(
        public id?: number,
        public creationDate?: Moment,
        public commentText?: string,
        public postHeadline?: string,
        public postId?: number
    ) {}
}
