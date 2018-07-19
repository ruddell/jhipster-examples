import { IBridgeCategory } from 'app/shared/model//bridge-category.model';

export interface IBridgeCategory {
    id?: number;
    idBridge?: number;
    name?: string;
    bridgeCategoryParent?: IBridgeCategory;
}

export class BridgeCategory implements IBridgeCategory {
    constructor(public id?: number, public idBridge?: number, public name?: string, public bridgeCategoryParent?: IBridgeCategory) {}
}
