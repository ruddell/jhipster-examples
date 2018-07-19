import { Moment } from 'moment';
import { IBridgeCategory } from 'app/shared/model//bridge-category.model';
import { IBridgeAccountBank } from 'app/shared/model//bridge-account-bank.model';

export interface IBridgeTransaction {
    id?: number;
    idBridge?: number;
    description?: string;
    rawDescription?: string;
    date?: Moment;
    updatedAt?: Moment;
    isDeleted?: boolean;
    bridgeCategory?: IBridgeCategory;
    bridgeAccountBank?: IBridgeAccountBank;
}

export class BridgeTransaction implements IBridgeTransaction {
    constructor(
        public id?: number,
        public idBridge?: number,
        public description?: string,
        public rawDescription?: string,
        public date?: Moment,
        public updatedAt?: Moment,
        public isDeleted?: boolean,
        public bridgeCategory?: IBridgeCategory,
        public bridgeAccountBank?: IBridgeAccountBank
    ) {
        this.isDeleted = false;
    }
}
