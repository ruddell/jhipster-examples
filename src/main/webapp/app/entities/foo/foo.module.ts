import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MonoSharedModule } from 'app/shared/shared.module';
import { FooComponent } from './foo.component';
import { FooDetailComponent } from './foo-detail.component';
import { FooUpdateComponent } from './foo-update.component';
import { FooDeletePopupComponent, FooDeleteDialogComponent } from './foo-delete-dialog.component';
import { fooRoute, fooPopupRoute } from './foo.route';

const ENTITY_STATES = [...fooRoute, ...fooPopupRoute];

@NgModule({
  imports: [MonoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [FooComponent, FooDetailComponent, FooUpdateComponent, FooDeleteDialogComponent, FooDeletePopupComponent],
  entryComponents: [FooDeleteDialogComponent]
})
export class MonoFooModule {}
