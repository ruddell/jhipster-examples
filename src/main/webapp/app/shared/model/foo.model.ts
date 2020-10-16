export interface IFoo {
  id?: number;
  name?: string;
  title?: string;
}

export class Foo implements IFoo {
  constructor(public id?: number, public name?: string, public title?: string) {}
}
