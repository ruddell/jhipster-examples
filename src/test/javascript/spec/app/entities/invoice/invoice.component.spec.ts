/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MonoTestModule } from '../../../test.module';
import { InvoiceComponent } from 'app/entities/invoice/invoice.component';
import { InvoiceService } from 'app/entities/invoice/invoice.service';
import { Invoice } from 'app/shared/model/invoice.model';

describe('Component Tests', () => {
  describe('Invoice Management Component', () => {
    let comp: InvoiceComponent;
    let fixture: ComponentFixture<InvoiceComponent>;
    let service: InvoiceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MonoTestModule],
        declarations: [InvoiceComponent],
        providers: []
      })
        .overrideTemplate(InvoiceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InvoiceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvoiceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Invoice(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.invoices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
