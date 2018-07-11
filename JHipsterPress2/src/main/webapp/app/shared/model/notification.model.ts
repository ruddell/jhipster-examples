import { Moment } from 'moment';

export const enum NotificationReason {
    FOLLOWING = 'FOLLOWING',
    UNFOLLOWING = 'UNFOLLOWING',
    PROPOSAL_RELEASED = 'PROPOSAL_RELEASED',
    FOLLOWER_STATUS = 'FOLLOWER_STATUS',
    AUTHORIZE_PARTY_FOLLOWER = 'AUTHORIZE_PARTY_FOLLOWER',
    UNAUTHORIZE_PARTY_FOLLOWER = 'UNAUTHORIZE_PARTY_FOLLOWER'
}

export interface INotification {
    id?: number;
    creationDate?: Moment;
    notificationDate?: Moment;
    notificationReason?: NotificationReason;
    notificationText?: string;
    isDeliverd?: boolean;
    userId?: number;
}

export class Notification implements INotification {
    constructor(
        public id?: number,
        public creationDate?: Moment,
        public notificationDate?: Moment,
        public notificationReason?: NotificationReason,
        public notificationText?: string,
        public isDeliverd?: boolean,
        public userId?: number
    ) {
        this.isDeliverd = false;
    }
}
