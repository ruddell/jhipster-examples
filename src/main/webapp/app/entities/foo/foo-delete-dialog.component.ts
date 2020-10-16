import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFoo } from 'app/shared/model/foo.model';
import { FooService } from './foo.service';

@Component({
  templateUrl: './foo-delete-dialog.component.html',
})
export class FooDeleteDialogComponent {
  foo?: IFoo;

  constructor(protected fooService: FooService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fooService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fooListModification');
      this.activeModal.close();
    });
  }
}
