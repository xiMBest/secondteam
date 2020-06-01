import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RentautoTestModule } from '../../../test.module';
import { InsuarenceDetailComponent } from 'app/entities/insuarence/insuarence-detail.component';
import { Insuarence } from 'app/shared/model/insuarence.model';

describe('Component Tests', () => {
  describe('Insuarence Management Detail Component', () => {
    let comp: InsuarenceDetailComponent;
    let fixture: ComponentFixture<InsuarenceDetailComponent>;
    const route = ({ data: of({ insuarence: new Insuarence(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RentautoTestModule],
        declarations: [InsuarenceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InsuarenceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InsuarenceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load insuarence on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.insuarence).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
