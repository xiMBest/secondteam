import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ReservecarService } from 'app/entities/reservecar/reservecar.service';
import { IReservecar, Reservecar } from 'app/shared/model/reservecar.model';
import { CarType } from 'app/shared/model/enumerations/car-type.model';

describe('Service Tests', () => {
  describe('Reservecar Service', () => {
    let injector: TestBed;
    let service: ReservecarService;
    let httpMock: HttpTestingController;
    let elemDefault: IReservecar;
    let expectedResult: IReservecar | IReservecar[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ReservecarService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Reservecar(0, 'AAAAAAA', CarType.Car, 0, currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            datePickUP: currentDate.format(DATE_TIME_FORMAT),
            dateDropDown: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Reservecar', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            datePickUP: currentDate.format(DATE_TIME_FORMAT),
            dateDropDown: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datePickUP: currentDate,
            dateDropDown: currentDate
          },
          returnedFromService
        );

        service.create(new Reservecar()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Reservecar', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            type: 'BBBBBB',
            totalPrice: 1,
            datePickUP: currentDate.format(DATE_TIME_FORMAT),
            dateDropDown: currentDate.format(DATE_TIME_FORMAT),
            description: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datePickUP: currentDate,
            dateDropDown: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Reservecar', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            type: 'BBBBBB',
            totalPrice: 1,
            datePickUP: currentDate.format(DATE_TIME_FORMAT),
            dateDropDown: currentDate.format(DATE_TIME_FORMAT),
            description: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datePickUP: currentDate,
            dateDropDown: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Reservecar', () => {
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
