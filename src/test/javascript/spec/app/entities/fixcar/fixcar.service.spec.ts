import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { FixcarService } from 'app/entities/fixcar/fixcar.service';
import { IFixcar, Fixcar } from 'app/shared/model/fixcar.model';

describe('Service Tests', () => {
  describe('Fixcar Service', () => {
    let injector: TestBed;
    let service: FixcarService;
    let httpMock: HttpTestingController;
    let elemDefault: IFixcar;
    let expectedResult: IFixcar | IFixcar[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FixcarService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Fixcar(0, 0, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateFixing: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Fixcar', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateFixing: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateFixing: currentDate
          },
          returnedFromService
        );

        service.create(new Fixcar()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Fixcar', () => {
        const returnedFromService = Object.assign(
          {
            price: 1,
            dateFixing: currentDate.format(DATE_TIME_FORMAT),
            description: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateFixing: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Fixcar', () => {
        const returnedFromService = Object.assign(
          {
            price: 1,
            dateFixing: currentDate.format(DATE_TIME_FORMAT),
            description: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateFixing: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Fixcar', () => {
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
