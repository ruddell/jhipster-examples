/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MonoTestModule } from '../../../test.module';
import { DepartmentDeleteDialogComponent } from 'app/entities/department/department-delete-dialog.component';
import { DepartmentService } from 'app/entities/department/department.service';

describe('Component Tests', () => {
  describe('Department Management Delete Component', () => {
    let comp: DepartmentDeleteDialogComponent;
    let fixture: ComponentFixture<DepartmentDeleteDialogComponent>;
    let service: DepartmentService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MonoTestModule],
        declarations: [DepartmentDeleteDialogComponent]
      })
        .overrideTemplate(DepartmentDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DepartmentDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DepartmentService);
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
