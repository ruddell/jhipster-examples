import { IBridgeAccountBank } from 'app/shared/model//bridge-account-bank.model';
import { IBridgeBank } from 'app/shared/model//bridge-bank.model';
import { IBridgeUser } from 'app/shared/model//bridge-user.model';

export interface IBridgeAccountOnlineBank {
    id?: number;
    idBridge?: number;
    status?: number;
    bridgeAccountsBanks?: IBridgeAccountBank[];
    bridgeBank?: IBridgeBank;
    bridgeUser?: IBridgeUser;
}

export class BridgeAccountOnlineBank implements IBridgeAccountOnlineBank {
    constructor(
        public id?: number,
        public idBridge?: number,
        public status?: number,
        public bridgeAccountsBanks?: IBridgeAccountBank[],
        public bridgeBank?: IBridgeBank,
        public bridgeUser?: IBridgeUser
    ) {}
}
