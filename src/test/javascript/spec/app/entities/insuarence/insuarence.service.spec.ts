import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { InsuarenceService } from 'app/entities/insuarence/insuarence.service';
import { IInsuarence, Insuarence } from 'app/shared/model/insuarence.model';
import { InsuarenceType } from 'app/shared/model/enumerations/insuarence-type.model';

describe('Service Tests', () => {
  describe('Insuarence Service', () => {
    let injector: TestBed;
    let service: InsuarenceService;
    let httpMock: HttpTestingController;
    let elemDefault: IInsuarence;
    let expectedResult: IInsuarence | IInsuarence[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(InsuarenceService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Insuarence(0, currentDate, currentDate, 0, InsuarenceType.Gold);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateApply: currentDate.format(DATE_TIME_FORMAT),
            dateEnd: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Insuarence', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateApply: currentDate.format(DATE_TIME_FORMAT),
            dateEnd: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateApply: currentDate,
            dateEnd: currentDate
          },
          returnedFromService
        );

        service.create(new Insuarence()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Insuarence', () => {
        const returnedFromService = Object.assign(
          {
            dateApply: currentDate.format(DATE_TIME_FORMAT),
            dateEnd: currentDate.format(DATE_TIME_FORMAT),
            cost: 1,
            type: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateApply: currentDate,
            dateEnd: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Insuarence', () => {
        const returnedFromService = Object.assign(
          {
            dateApply: currentDate.format(DATE_TIME_FORMAT),
            dateEnd: currentDate.format(DATE_TIME_FORMAT),
            cost: 1,
            type: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateApply: currentDate,
            dateEnd: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Insuarence', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
