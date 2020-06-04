import { Country } from 'app/shared/model/enumerations/country.model';

export interface IFoo {
  id?: number;
  test?: Country;
}

export class Foo implements IFoo {
  constructor(public id?: number, public test?: Country) {}
}
