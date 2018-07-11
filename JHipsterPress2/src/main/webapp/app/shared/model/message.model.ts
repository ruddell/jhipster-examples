import { Moment } from 'moment';

export interface IMessage {
    id?: number;
    creationDate?: Moment;
    messageText?: string;
    isDeliverd?: boolean;
    partyId?: number;
}

export class Message implements IMessage {
    constructor(
        public id?: number,
        public creationDate?: Moment,
        public messageText?: string,
        public isDeliverd?: boolean,
        public partyId?: number
    ) {
        this.isDeliverd = false;
    }
}
