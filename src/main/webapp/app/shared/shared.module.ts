import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';
import { DateFormatPipe, ParsePipe } from 'angular2-moment';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { JhipsterSharedLibsModule, JhipsterSharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [JhipsterSharedLibsModule, JhipsterSharedCommonModule],
  declarations: [HasAnyAuthorityDirective],
  providers: [ParsePipe, DateFormatPipe, { provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
  exports: [JhipsterSharedCommonModule, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSharedModule {}
