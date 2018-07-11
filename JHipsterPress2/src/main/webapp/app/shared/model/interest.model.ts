import { IParty } from 'app/shared/model//party.model';
import { IProfile } from 'app/shared/model//profile.model';

export interface IInterest {
    id?: number;
    interestName?: string;
    parties?: IParty[];
    profiles?: IProfile[];
}

export class Interest implements IInterest {
    constructor(public id?: number, public interestName?: string, public parties?: IParty[], public profiles?: IProfile[]) {}
}
