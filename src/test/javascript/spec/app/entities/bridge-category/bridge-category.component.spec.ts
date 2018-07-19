/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinaltestTestModule } from '../../../test.module';
import { BridgeCategoryComponent } from 'app/entities/bridge-category/bridge-category.component';
import { BridgeCategoryService } from 'app/entities/bridge-category/bridge-category.service';
import { BridgeCategory } from 'app/shared/model/bridge-category.model';

describe('Component Tests', () => {
    describe('BridgeCategory Management Component', () => {
        let comp: BridgeCategoryComponent;
        let fixture: ComponentFixture<BridgeCategoryComponent>;
        let service: BridgeCategoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FinaltestTestModule],
                declarations: [BridgeCategoryComponent],
                providers: []
            })
                .overrideTemplate(BridgeCategoryComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BridgeCategoryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BridgeCategoryService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new BridgeCategory(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bridgeCategories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
