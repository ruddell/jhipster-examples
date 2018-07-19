/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeAccountOnlineBankComponent } from 'app/entities/bridge-account-online-bank/bridge-account-online-bank.component';
import { BridgeAccountOnlineBankService } from 'app/entities/bridge-account-online-bank/bridge-account-online-bank.service';
import { BridgeAccountOnlineBank } from 'app/shared/model/bridge-account-online-bank.model';

describe('Component Tests', () => {
    describe('BridgeAccountOnlineBank Management Component', () => {
        let comp: BridgeAccountOnlineBankComponent;
        let fixture: ComponentFixture<BridgeAccountOnlineBankComponent>;
        let service: BridgeAccountOnlineBankService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeAccountOnlineBankComponent],
                providers: []
            })
                .overrideTemplate(BridgeAccountOnlineBankComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BridgeAccountOnlineBankComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BridgeAccountOnlineBankService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new BridgeAccountOnlineBank(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bridgeAccountOnlineBanks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
