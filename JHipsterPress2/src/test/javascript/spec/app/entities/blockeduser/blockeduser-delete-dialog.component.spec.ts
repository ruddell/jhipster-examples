/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JHipsterPress2TestModule } from '../../../test.module';
import { BlockeduserDeleteDialogComponent } from 'app/entities/blockeduser/blockeduser-delete-dialog.component';
import { BlockeduserService } from 'app/entities/blockeduser/blockeduser.service';

describe('Component Tests', () => {
    describe('Blockeduser Management Delete Component', () => {
        let comp: BlockeduserDeleteDialogComponent;
        let fixture: ComponentFixture<BlockeduserDeleteDialogComponent>;
        let service: BlockeduserService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JHipsterPress2TestModule],
                declarations: [BlockeduserDeleteDialogComponent]
            })
                .overrideTemplate(BlockeduserDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BlockeduserDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BlockeduserService);
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
