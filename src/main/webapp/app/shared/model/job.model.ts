import { ITask } from 'app/shared/model/task.model';

export interface IJob {
  id?: number;
  jobTitle?: string;
  minSalary?: number;
  maxSalary?: number;
  tasks?: ITask[];
  employeeEmail?: string;
  employeeId?: number;
}

export class Job implements IJob {
  constructor(
    public id?: number,
    public jobTitle?: string,
    public minSalary?: number,
    public maxSalary?: number,
    public tasks?: ITask[],
    public employeeEmail?: string,
    public employeeId?: number
  ) {}
}
