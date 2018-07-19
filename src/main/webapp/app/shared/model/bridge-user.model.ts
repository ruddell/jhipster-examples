import { IUser } from 'app/core/user/user.model';
import { IBridgeAccountOnlineBank } from 'app/shared/model//bridge-account-online-bank.model';

export interface IBridgeUser {
    id?: number;
    uuidBridge?: string;
    email?: string;
    password?: string;
    user?: IUser;
    bridgeAccountsOnlineBanks?: IBridgeAccountOnlineBank[];
}

export class BridgeUser implements IBridgeUser {
    constructor(
        public id?: number,
        public uuidBridge?: string,
        public email?: string,
        public password?: string,
        public user?: IUser,
        public bridgeAccountsOnlineBanks?: IBridgeAccountOnlineBank[]
    ) {}
}
