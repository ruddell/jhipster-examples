import { Moment } from 'moment';

export interface IBlockeduser {
    id?: number;
    creationDate?: Moment;
    blockinguserId?: number;
    blockeduserId?: number;
}

export class Blockeduser implements IBlockeduser {
    constructor(public id?: number, public creationDate?: Moment, public blockinguserId?: number, public blockeduserId?: number) {}
}
