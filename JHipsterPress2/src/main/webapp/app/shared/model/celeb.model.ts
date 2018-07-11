import { IParty } from 'app/shared/model//party.model';
import { IProfile } from 'app/shared/model//profile.model';

export interface ICeleb {
    id?: number;
    celebName?: string;
    parties?: IParty[];
    profiles?: IProfile[];
}

export class Celeb implements ICeleb {
    constructor(public id?: number, public celebName?: string, public parties?: IParty[], public profiles?: IProfile[]) {}
}
