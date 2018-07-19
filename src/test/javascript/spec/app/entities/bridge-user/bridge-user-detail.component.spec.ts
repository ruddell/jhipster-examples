/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeUserDetailComponent } from 'app/entities/bridge-user/bridge-user-detail.component';
import { BridgeUser } from 'app/shared/model/bridge-user.model';

describe('Component Tests', () => {
    describe('BridgeUser Management Detail Component', () => {
        let comp: BridgeUserDetailComponent;
        let fixture: ComponentFixture<BridgeUserDetailComponent>;
        const route = ({ data: of({ bridgeUser: new BridgeUser(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeUserDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BridgeUserDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BridgeUserDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bridgeUser).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
