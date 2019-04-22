import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { MonoSharedLibsModule, MonoSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [MonoSharedLibsModule, MonoSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [MonoSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MonoSharedModule {
  static forRoot() {
    return {
      ngModule: MonoSharedModule
    };
  }
}
