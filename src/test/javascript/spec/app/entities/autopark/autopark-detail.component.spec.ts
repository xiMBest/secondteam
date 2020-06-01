import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RentautoTestModule } from '../../../test.module';
import { AutoparkDetailComponent } from 'app/entities/autopark/autopark-detail.component';
import { Autopark } from 'app/shared/model/autopark.model';

describe('Component Tests', () => {
  describe('Autopark Management Detail Component', () => {
    let comp: AutoparkDetailComponent;
    let fixture: ComponentFixture<AutoparkDetailComponent>;
    const route = ({ data: of({ autopark: new Autopark(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RentautoTestModule],
        declarations: [AutoparkDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AutoparkDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AutoparkDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load autopark on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.autopark).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
