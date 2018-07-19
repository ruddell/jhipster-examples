import { Moment } from 'moment';
import { IBridgeAccountBank } from 'app/shared/model//bridge-account-bank.model';

export interface IAccountBankStat {
    id?: number;
    balance?: number;
    lastRefresh?: Moment;
    bridgeAccountBank?: IBridgeAccountBank;
}

export class AccountBankStat implements IAccountBankStat {
    constructor(public id?: number, public balance?: number, public lastRefresh?: Moment, public bridgeAccountBank?: IBridgeAccountBank) {}
}
