import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RentautoTestModule } from '../../../test.module';
import { PenaltyDetailComponent } from 'app/entities/penalty/penalty-detail.component';
import { Penalty } from 'app/shared/model/penalty.model';

describe('Component Tests', () => {
  describe('Penalty Management Detail Component', () => {
    let comp: PenaltyDetailComponent;
    let fixture: ComponentFixture<PenaltyDetailComponent>;
    const route = ({ data: of({ penalty: new Penalty(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RentautoTestModule],
        declarations: [PenaltyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PenaltyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PenaltyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load penalty on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.penalty).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
