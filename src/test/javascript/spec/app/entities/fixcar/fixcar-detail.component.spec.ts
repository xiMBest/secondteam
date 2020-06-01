import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RentautoTestModule } from '../../../test.module';
import { FixcarDetailComponent } from 'app/entities/fixcar/fixcar-detail.component';
import { Fixcar } from 'app/shared/model/fixcar.model';

describe('Component Tests', () => {
  describe('Fixcar Management Detail Component', () => {
    let comp: FixcarDetailComponent;
    let fixture: ComponentFixture<FixcarDetailComponent>;
    const route = ({ data: of({ fixcar: new Fixcar(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RentautoTestModule],
        declarations: [FixcarDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FixcarDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FixcarDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fixcar on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fixcar).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
