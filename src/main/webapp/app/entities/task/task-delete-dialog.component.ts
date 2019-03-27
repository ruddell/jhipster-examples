import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITask } from 'app/shared/model/task.model';
import { TaskService } from './task.service';

@Component({
  selector: 'jhi-task-delete-dialog',
  templateUrl: './task-delete-dialog.component.html'
})
export class TaskDeleteDialogComponent {
  task: ITask;

  constructor(protected taskService: TaskService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.taskService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'taskListModification',
        content: 'Deleted an task'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-task-delete-popup',
  template: ''
})
export class TaskDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ task }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TaskDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.task = task;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/task', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/task', { outlets: { popup: null } }]);
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
