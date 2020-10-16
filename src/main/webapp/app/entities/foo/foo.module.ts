import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { FooComponent } from './foo.component';
import { FooDetailComponent } from './foo-detail.component';
import { FooUpdateComponent } from './foo-update.component';
import { FooDeleteDialogComponent } from './foo-delete-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [FooComponent, FooDetailComponent, FooUpdateComponent, FooDeleteDialogComponent],
  exports: [FooDetailComponent, FooUpdateComponent, FooDeleteDialogComponent],
  entryComponents: [FooDeleteDialogComponent],
})
export class FooModule {}
