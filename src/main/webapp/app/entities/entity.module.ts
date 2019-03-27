import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'region',
        loadChildren: './region/region.module#MonoRegionModule'
      },
      {
        path: 'country',
        loadChildren: './country/country.module#MonoCountryModule'
      },
      {
        path: 'location',
        loadChildren: './location/location.module#MonoLocationModule'
      },
      {
        path: 'department',
        loadChildren: './department/department.module#MonoDepartmentModule'
      },
      {
        path: 'task',
        loadChildren: './task/task.module#MonoTaskModule'
      },
      {
        path: 'employee',
        loadChildren: './employee/employee.module#MonoEmployeeModule'
      },
      {
        path: 'job',
        loadChildren: './job/job.module#MonoJobModule'
      },
      {
        path: 'job-history',
        loadChildren: './job-history/job-history.module#MonoJobHistoryModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MonoEntityModule {}
