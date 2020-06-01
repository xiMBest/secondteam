import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RentautoTestModule } from '../../../test.module';
import { RateDetailComponent } from 'app/entities/rate/rate-detail.component';
import { Rate } from 'app/shared/model/rate.model';

describe('Component Tests', () => {
  describe('Rate Management Detail Component', () => {
    let comp: RateDetailComponent;
    let fixture: ComponentFixture<RateDetailComponent>;
    const route = ({ data: of({ rate: new Rate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RentautoTestModule],
        declarations: [RateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load rate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
