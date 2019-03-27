import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRegion } from 'app/shared/model/region.model';
import { RegionService } from './region.service';

@Component({
  selector: 'jhi-region-delete-dialog',
  templateUrl: './region-delete-dialog.component.html'
})
export class RegionDeleteDialogComponent {
  region: IRegion;

  constructor(protected regionService: RegionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.regionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'regionListModification',
        content: 'Deleted an region'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-region-delete-popup',
  template: ''
})
export class RegionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ region }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RegionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.region = region;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/region', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/region', { outlets: { popup: null } }]);
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
