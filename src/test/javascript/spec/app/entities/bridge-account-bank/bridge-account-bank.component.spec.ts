/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeAccountBankComponent } from 'app/entities/bridge-account-bank/bridge-account-bank.component';
import { BridgeAccountBankService } from 'app/entities/bridge-account-bank/bridge-account-bank.service';
import { BridgeAccountBank } from 'app/shared/model/bridge-account-bank.model';

describe('Component Tests', () => {
    describe('BridgeAccountBank Management Component', () => {
        let comp: BridgeAccountBankComponent;
        let fixture: ComponentFixture<BridgeAccountBankComponent>;
        let service: BridgeAccountBankService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeAccountBankComponent],
                providers: []
            })
                .overrideTemplate(BridgeAccountBankComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BridgeAccountBankComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BridgeAccountBankService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new BridgeAccountBank(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bridgeAccountBanks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
