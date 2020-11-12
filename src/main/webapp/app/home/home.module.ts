import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AjsdklfjasdklfjsdlfkasdfasdSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [AjsdklfjasdklfjsdlfkasdfasdSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class AjsdklfjasdklfjsdlfkasdfasdHomeModule {}
