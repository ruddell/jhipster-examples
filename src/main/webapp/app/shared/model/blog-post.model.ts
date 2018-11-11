export interface IBlogPost {
    id?: number;
    name?: string;
    userLogin?: string;
    userId?: number;
}

export class BlogPost implements IBlogPost {
    constructor(public id?: number, public name?: string, public userLogin?: string, public userId?: number) {}
}
