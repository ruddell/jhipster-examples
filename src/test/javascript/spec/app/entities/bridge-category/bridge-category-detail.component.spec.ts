/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeCategoryDetailComponent } from 'app/entities/bridge-category/bridge-category-detail.component';
import { BridgeCategory } from 'app/shared/model/bridge-category.model';

describe('Component Tests', () => {
    describe('BridgeCategory Management Detail Component', () => {
        let comp: BridgeCategoryDetailComponent;
        let fixture: ComponentFixture<BridgeCategoryDetailComponent>;
        const route = ({ data: of({ bridgeCategory: new BridgeCategory(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeCategoryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BridgeCategoryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BridgeCategoryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bridgeCategory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
