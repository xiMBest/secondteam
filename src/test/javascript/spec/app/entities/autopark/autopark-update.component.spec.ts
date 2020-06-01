import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RentautoTestModule } from '../../../test.module';
import { AutoparkUpdateComponent } from 'app/entities/autopark/autopark-update.component';
import { AutoparkService } from 'app/entities/autopark/autopark.service';
import { Autopark } from 'app/shared/model/autopark.model';

describe('Component Tests', () => {
  describe('Autopark Management Update Component', () => {
    let comp: AutoparkUpdateComponent;
    let fixture: ComponentFixture<AutoparkUpdateComponent>;
    let service: AutoparkService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RentautoTestModule],
        declarations: [AutoparkUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AutoparkUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AutoparkUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AutoparkService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Autopark(123);
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
        const entity = new Autopark();
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
