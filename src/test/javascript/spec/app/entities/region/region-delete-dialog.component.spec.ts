/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MonoTestModule } from '../../../test.module';
import { RegionDeleteDialogComponent } from 'app/entities/region/region-delete-dialog.component';
import { RegionService } from 'app/entities/region/region.service';

describe('Component Tests', () => {
  describe('Region Management Delete Component', () => {
    let comp: RegionDeleteDialogComponent;
    let fixture: ComponentFixture<RegionDeleteDialogComponent>;
    let service: RegionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MonoTestModule],
        declarations: [RegionDeleteDialogComponent]
      })
        .overrideTemplate(RegionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RegionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegionService);
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
