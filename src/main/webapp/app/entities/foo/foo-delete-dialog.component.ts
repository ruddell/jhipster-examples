import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFoo } from 'app/shared/model/foo.model';
import { FooService } from './foo.service';

@Component({
  selector: 'jhi-foo-delete-dialog',
  templateUrl: './foo-delete-dialog.component.html'
})
export class FooDeleteDialogComponent {
  foo: IFoo;

  constructor(protected fooService: FooService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fooService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'fooListModification',
        content: 'Deleted an foo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-foo-delete-popup',
  template: ''
})
export class FooDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ foo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FooDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.foo = foo;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/foo', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/foo', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
