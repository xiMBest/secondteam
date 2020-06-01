import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RentautoTestModule } from '../../../test.module';
import { ReservecarUpdateComponent } from 'app/entities/reservecar/reservecar-update.component';
import { ReservecarService } from 'app/entities/reservecar/reservecar.service';
import { Reservecar } from 'app/shared/model/reservecar.model';

describe('Component Tests', () => {
  describe('Reservecar Management Update Component', () => {
    let comp: ReservecarUpdateComponent;
    let fixture: ComponentFixture<ReservecarUpdateComponent>;
    let service: ReservecarService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RentautoTestModule],
        declarations: [ReservecarUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ReservecarUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReservecarUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReservecarService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Reservecar(123);
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
        const entity = new Reservecar();
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
