/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeUserDeleteDialogComponent } from 'app/entities/bridge-user/bridge-user-delete-dialog.component';
import { BridgeUserService } from 'app/entities/bridge-user/bridge-user.service';

describe('Component Tests', () => {
    describe('BridgeUser Management Delete Component', () => {
        let comp: BridgeUserDeleteDialogComponent;
        let fixture: ComponentFixture<BridgeUserDeleteDialogComponent>;
        let service: BridgeUserService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeUserDeleteDialogComponent]
            })
                .overrideTemplate(BridgeUserDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BridgeUserDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BridgeUserService);
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
