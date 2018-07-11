import { Moment } from 'moment';

export interface IPhoto {
    id?: number;
    creationDate?: Moment;
    imageContentType?: string;
    image?: any;
    albumId?: number;
}

export class Photo implements IPhoto {
    constructor(
        public id?: number,
        public creationDate?: Moment,
        public imageContentType?: string,
        public image?: any,
        public albumId?: number
    ) {}
}
