import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFoo } from 'app/shared/model/foo.model';
import { FooService } from './foo.service';
import { FooDeleteDialogComponent } from './foo-delete-dialog.component';

@Component({
  selector: 'jhi-foo',
  templateUrl: './foo.component.html',
})
export class FooComponent implements OnInit, OnDestroy {
  foos?: IFoo[];
  eventSubscriber?: Subscription;

  constructor(protected fooService: FooService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.fooService.query().subscribe((res: HttpResponse<IFoo[]>) => (this.foos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFoos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFoo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFoos(): void {
    this.eventSubscriber = this.eventManager.subscribe('fooListModification', () => this.loadAll());
  }

  delete(foo: IFoo): void {
    const modalRef = this.modalService.open(FooDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.foo = foo;
  }
}
