import { IEmployee } from 'app/shared/model/employee.model';

export interface IDepartment {
  id?: number;
  departmentName?: string;
  employees?: IEmployee[];
  locationStreetAddress?: string;
  locationId?: number;
}

export class Department implements IDepartment {
  constructor(
    public id?: number,
    public departmentName?: string,
    public employees?: IEmployee[],
    public locationStreetAddress?: string,
    public locationId?: number
  ) {}
}
