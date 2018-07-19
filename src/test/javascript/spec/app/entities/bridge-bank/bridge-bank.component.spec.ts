/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeBankComponent } from 'app/entities/bridge-bank/bridge-bank.component';
import { BridgeBankService } from 'app/entities/bridge-bank/bridge-bank.service';
import { BridgeBank } from 'app/shared/model/bridge-bank.model';

describe('Component Tests', () => {
    describe('BridgeBank Management Component', () => {
        let comp: BridgeBankComponent;
        let fixture: ComponentFixture<BridgeBankComponent>;
        let service: BridgeBankService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeBankComponent],
                providers: []
            })
                .overrideTemplate(BridgeBankComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BridgeBankComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BridgeBankService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new BridgeBank(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bridgeBanks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
