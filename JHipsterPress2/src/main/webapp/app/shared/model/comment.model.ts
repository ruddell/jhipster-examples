import { Moment } from 'moment';

export interface IComment {
    id?: number;
    creationDate?: Moment;
    commentText?: string;
    isOffensive?: boolean;
    postHeadline?: string;
    postId?: number;
    partyId?: number;
}

export class Comment implements IComment {
    constructor(
        public id?: number,
        public creationDate?: Moment,
        public commentText?: string,
        public isOffensive?: boolean,
        public postHeadline?: string,
        public postId?: number,
        public partyId?: number
    ) {
        this.isOffensive = false;
    }
}
