/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeBankDetailComponent } from 'app/entities/bridge-bank/bridge-bank-detail.component';
import { BridgeBank } from 'app/shared/model/bridge-bank.model';

describe('Component Tests', () => {
    describe('BridgeBank Management Detail Component', () => {
        let comp: BridgeBankDetailComponent;
        let fixture: ComponentFixture<BridgeBankDetailComponent>;
        const route = ({ data: of({ bridgeBank: new BridgeBank(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeBankDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BridgeBankDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BridgeBankDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bridgeBank).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
