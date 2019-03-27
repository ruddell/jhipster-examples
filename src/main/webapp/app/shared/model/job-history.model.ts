import { Moment } from 'moment';

export const enum Language {
  FRENCH = 'FRENCH',
  ENGLISH = 'ENGLISH',
  SPANISH = 'SPANISH'
}

export interface IJobHistory {
  id?: number;
  startDate?: Moment;
  endDate?: Moment;
  language?: Language;
  jobId?: number;
  departmentId?: number;
  employeeId?: number;
}

export class JobHistory implements IJobHistory {
  constructor(
    public id?: number,
    public startDate?: Moment,
    public endDate?: Moment,
    public language?: Language,
    public jobId?: number,
    public departmentId?: number,
    public employeeId?: number
  ) {}
}
