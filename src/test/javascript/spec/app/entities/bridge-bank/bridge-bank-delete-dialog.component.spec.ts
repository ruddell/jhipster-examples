/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeBankDeleteDialogComponent } from 'app/entities/bridge-bank/bridge-bank-delete-dialog.component';
import { BridgeBankService } from 'app/entities/bridge-bank/bridge-bank.service';

describe('Component Tests', () => {
    describe('BridgeBank Management Delete Component', () => {
        let comp: BridgeBankDeleteDialogComponent;
        let fixture: ComponentFixture<BridgeBankDeleteDialogComponent>;
        let service: BridgeBankService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeBankDeleteDialogComponent]
            })
                .overrideTemplate(BridgeBankDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BridgeBankDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BridgeBankService);
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
