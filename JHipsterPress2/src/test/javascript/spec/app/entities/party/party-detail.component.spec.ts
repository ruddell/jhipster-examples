/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JHipsterPress2TestModule } from '../../../test.module';
import { PartyDetailComponent } from 'app/entities/party/party-detail.component';
import { Party } from 'app/shared/model/party.model';

describe('Component Tests', () => {
    describe('Party Management Detail Component', () => {
        let comp: PartyDetailComponent;
        let fixture: ComponentFixture<PartyDetailComponent>;
        const route = ({ data: of({ party: new Party(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JHipsterPress2TestModule],
                declarations: [PartyDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PartyDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PartyDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.party).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
