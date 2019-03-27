/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MonoTestModule } from '../../../test.module';
import { TaskDeleteDialogComponent } from 'app/entities/task/task-delete-dialog.component';
import { TaskService } from 'app/entities/task/task.service';

describe('Component Tests', () => {
  describe('Task Management Delete Component', () => {
    let comp: TaskDeleteDialogComponent;
    let fixture: ComponentFixture<TaskDeleteDialogComponent>;
    let service: TaskService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MonoTestModule],
        declarations: [TaskDeleteDialogComponent]
      })
        .overrideTemplate(TaskDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaskDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaskService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
