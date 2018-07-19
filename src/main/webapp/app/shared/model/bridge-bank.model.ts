export interface IBridgeBank {
    id?: number;
    idBridge?: number;
    name?: string;
    countryCode?: string;
}

export class BridgeBank implements IBridgeBank {
    constructor(public id?: number, public idBridge?: number, public name?: string, public countryCode?: string) {}
}
