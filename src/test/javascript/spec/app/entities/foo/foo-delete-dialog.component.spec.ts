/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MonoTestModule } from '../../../test.module';
import { FooDeleteDialogComponent } from 'app/entities/foo/foo-delete-dialog.component';
import { FooService } from 'app/entities/foo/foo.service';

describe('Component Tests', () => {
    describe('Foo Management Delete Component', () => {
        let comp: FooDeleteDialogComponent;
        let fixture: ComponentFixture<FooDeleteDialogComponent>;
        let service: FooService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MonoTestModule],
                declarations: [FooDeleteDialogComponent]
            })
                .overrideTemplate(FooDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FooDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FooService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
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
                )
            );
        });
    });
});
