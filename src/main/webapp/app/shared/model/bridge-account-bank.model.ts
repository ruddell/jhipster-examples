import { Moment } from 'moment';
import { IAccountBankStat } from 'app/shared/model//account-bank-stat.model';
import { IBridgeTransaction } from 'app/shared/model//bridge-transaction.model';
import { IBridgeBank } from 'app/shared/model//bridge-bank.model';
import { IBridgeAccountOnlineBank } from 'app/shared/model//bridge-account-online-bank.model';

export interface IBridgeAccountBank {
    id?: number;
    idBridge?: number;
    name?: string;
    type?: string;
    currencyCode?: string;
    itemStatus?: number;
    updatedAt?: Moment;
    bridgeAccountBankStats?: IAccountBankStat[];
    bridgeTransactions?: IBridgeTransaction[];
    bridgeBank?: IBridgeBank;
    bridgeAccountOnlineBank?: IBridgeAccountOnlineBank;
}

export class BridgeAccountBank implements IBridgeAccountBank {
    constructor(
        public id?: number,
        public idBridge?: number,
        public name?: string,
        public type?: string,
        public currencyCode?: string,
        public itemStatus?: number,
        public updatedAt?: Moment,
        public bridgeAccountBankStats?: IAccountBankStat[],
        public bridgeTransactions?: IBridgeTransaction[],
        public bridgeBank?: IBridgeBank,
        public bridgeAccountOnlineBank?: IBridgeAccountOnlineBank
    ) {}
}
