import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EsTestModule } from '../../../test.module';
import { FooDetailComponent } from 'app/entities/foo/foo-detail.component';
import { Foo } from 'app/shared/model/foo.model';

describe('Component Tests', () => {
  describe('Foo Management Detail Component', () => {
    let comp: FooDetailComponent;
    let fixture: ComponentFixture<FooDetailComponent>;
    const route = ({ data: of({ foo: new Foo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsTestModule],
        declarations: [FooDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FooDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FooDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.foo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
