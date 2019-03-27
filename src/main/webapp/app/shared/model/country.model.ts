export interface ICountry {
  id?: number;
  countryName?: string;
  regionRegionName?: string;
  regionId?: number;
}

export class Country implements ICountry {
  constructor(public id?: number, public countryName?: string, public regionRegionName?: string, public regionId?: number) {}
}
