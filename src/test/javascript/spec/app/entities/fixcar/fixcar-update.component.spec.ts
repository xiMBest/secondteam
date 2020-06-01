import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RentautoTestModule } from '../../../test.module';
import { FixcarUpdateComponent } from 'app/entities/fixcar/fixcar-update.component';
import { FixcarService } from 'app/entities/fixcar/fixcar.service';
import { Fixcar } from 'app/shared/model/fixcar.model';

describe('Component Tests', () => {
  describe('Fixcar Management Update Component', () => {
    let comp: FixcarUpdateComponent;
    let fixture: ComponentFixture<FixcarUpdateComponent>;
    let service: FixcarService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RentautoTestModule],
        declarations: [FixcarUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FixcarUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FixcarUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FixcarService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Fixcar(123);
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
        const entity = new Fixcar();
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
