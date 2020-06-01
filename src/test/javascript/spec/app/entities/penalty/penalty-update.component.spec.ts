import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RentautoTestModule } from '../../../test.module';
import { PenaltyUpdateComponent } from 'app/entities/penalty/penalty-update.component';
import { PenaltyService } from 'app/entities/penalty/penalty.service';
import { Penalty } from 'app/shared/model/penalty.model';

describe('Component Tests', () => {
  describe('Penalty Management Update Component', () => {
    let comp: PenaltyUpdateComponent;
    let fixture: ComponentFixture<PenaltyUpdateComponent>;
    let service: PenaltyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RentautoTestModule],
        declarations: [PenaltyUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PenaltyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PenaltyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PenaltyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Penalty(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Penalty();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
