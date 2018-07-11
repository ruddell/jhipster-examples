/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JHipsterPress2TestModule } from '../../../test.module';
import { PartyDeleteDialogComponent } from 'app/entities/party/party-delete-dialog.component';
import { PartyService } from 'app/entities/party/party.service';

describe('Component Tests', () => {
    describe('Party Management Delete Component', () => {
        let comp: PartyDeleteDialogComponent;
        let fixture: ComponentFixture<PartyDeleteDialogComponent>;
        let service: PartyService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JHipsterPress2TestModule],
                declarations: [PartyDeleteDialogComponent]
            })
                .overrideTemplate(PartyDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PartyDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartyService);
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
