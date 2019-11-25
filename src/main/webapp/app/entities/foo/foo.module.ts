import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EsSharedModule } from 'app/shared/shared.module';
import { FooComponent } from './foo.component';
import { FooDetailComponent } from './foo-detail.component';
import { FooUpdateComponent } from './foo-update.component';
import { FooDeleteDialogComponent } from './foo-delete-dialog.component';
import { fooRoute } from './foo.route';

@NgModule({
  imports: [EsSharedModule, RouterModule.forChild(fooRoute)],
  declarations: [FooComponent, FooDetailComponent, FooUpdateComponent, FooDeleteDialogComponent],
  entryComponents: [FooDeleteDialogComponent]
})
export class EsFooModule {}
