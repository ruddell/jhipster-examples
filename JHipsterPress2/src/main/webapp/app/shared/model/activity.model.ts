import { IParty } from 'app/shared/model//party.model';
import { IProfile } from 'app/shared/model//profile.model';

export interface IActivity {
    id?: number;
    activityName?: string;
    parties?: IParty[];
    profiles?: IProfile[];
}

export class Activity implements IActivity {
    constructor(public id?: number, public activityName?: string, public parties?: IParty[], public profiles?: IProfile[]) {}
}
