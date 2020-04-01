export interface IEmployee {
  id?: number;
  name?: string;
  employees?: IEmployee[];
  managerName?: string;
  managerId?: number;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public name?: string,
    public employees?: IEmployee[],
    public managerName?: string,
    public managerId?: number
  ) {}
}
