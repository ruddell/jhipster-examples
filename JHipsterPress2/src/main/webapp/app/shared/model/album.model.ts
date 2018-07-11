import { Moment } from 'moment';
import { IPhoto } from 'app/shared/model//photo.model';

export interface IAlbum {
    id?: number;
    creationDate?: Moment;
    title?: string;
    partyId?: number;
    photos?: IPhoto[];
}

export class Album implements IAlbum {
    constructor(
        public id?: number,
        public creationDate?: Moment,
        public title?: string,
        public partyId?: number,
        public photos?: IPhoto[]
    ) {}
}
