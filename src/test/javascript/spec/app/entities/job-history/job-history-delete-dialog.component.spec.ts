/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MonoTestModule } from '../../../test.module';
import { JobHistoryDeleteDialogComponent } from 'app/entities/job-history/job-history-delete-dialog.component';
import { JobHistoryService } from 'app/entities/job-history/job-history.service';

describe('Component Tests', () => {
  describe('JobHistory Management Delete Component', () => {
    let comp: JobHistoryDeleteDialogComponent;
    let fixture: ComponentFixture<JobHistoryDeleteDialogComponent>;
    let service: JobHistoryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MonoTestModule],
        declarations: [JobHistoryDeleteDialogComponent]
      })
        .overrideTemplate(JobHistoryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JobHistoryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JobHistoryService);
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
