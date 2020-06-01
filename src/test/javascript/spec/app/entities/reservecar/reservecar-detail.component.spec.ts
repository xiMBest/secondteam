import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RentautoTestModule } from '../../../test.module';
import { ReservecarDetailComponent } from 'app/entities/reservecar/reservecar-detail.component';
import { Reservecar } from 'app/shared/model/reservecar.model';

describe('Component Tests', () => {
  describe('Reservecar Management Detail Component', () => {
    let comp: ReservecarDetailComponent;
    let fixture: ComponentFixture<ReservecarDetailComponent>;
    const route = ({ data: of({ reservecar: new Reservecar(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RentautoTestModule],
        declarations: [ReservecarDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReservecarDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReservecarDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load reservecar on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reservecar).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
