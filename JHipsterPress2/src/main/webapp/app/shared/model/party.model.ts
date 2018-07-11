import { Moment } from 'moment';
import { IBlog } from 'app/shared/model//blog.model';
import { IComment } from 'app/shared/model//comment.model';
import { IMessage } from 'app/shared/model//message.model';
import { IFollow } from 'app/shared/model//follow.model';
import { IBlockeduser } from 'app/shared/model//blockeduser.model';
import { IInterest } from 'app/shared/model//interest.model';
import { IActivity } from 'app/shared/model//activity.model';
import { ICeleb } from 'app/shared/model//celeb.model';

export interface IParty {
    id?: number;
    creationDate?: Moment;
    partyname?: string;
    partydescription?: string;
    imageContentType?: string;
    image?: any;
    isActive?: boolean;
    blogs?: IBlog[];
    comments?: IComment[];
    messages?: IMessage[];
    followeds?: IFollow[];
    followings?: IFollow[];
    blockingusers?: IBlockeduser[];
    blockedusers?: IBlockeduser[];
    userId?: number;
    albumId?: number;
    interests?: IInterest[];
    activities?: IActivity[];
    celebs?: ICeleb[];
}

export class Party implements IParty {
    constructor(
        public id?: number,
        public creationDate?: Moment,
        public partyname?: string,
        public partydescription?: string,
        public imageContentType?: string,
        public image?: any,
        public isActive?: boolean,
        public blogs?: IBlog[],
        public comments?: IComment[],
        public messages?: IMessage[],
        public followeds?: IFollow[],
        public followings?: IFollow[],
        public blockingusers?: IBlockeduser[],
        public blockedusers?: IBlockeduser[],
        public userId?: number,
        public albumId?: number,
        public interests?: IInterest[],
        public activities?: IActivity[],
        public celebs?: ICeleb[]
    ) {
        this.isActive = false;
    }
}
