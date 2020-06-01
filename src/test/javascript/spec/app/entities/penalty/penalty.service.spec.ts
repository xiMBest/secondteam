import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PenaltyService } from 'app/entities/penalty/penalty.service';
import { IPenalty, Penalty } from 'app/shared/model/penalty.model';

describe('Service Tests', () => {
  describe('Penalty Service', () => {
    let injector: TestBed;
    let service: PenaltyService;
    let httpMock: HttpTestingController;
    let elemDefault: IPenalty;
    let expectedResult: IPenalty | IPenalty[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PenaltyService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Penalty(0, currentDate, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            datePenalty: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Penalty', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            datePenalty: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datePenalty: currentDate
          },
          returnedFromService
        );

        service.create(new Penalty()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Penalty', () => {
        const returnedFromService = Object.assign(
          {
            datePenalty: currentDate.format(DATE_TIME_FORMAT),
            price: 1,
            description: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datePenalty: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Penalty', () => {
        const returnedFromService = Object.assign(
          {
            datePenalty: currentDate.format(DATE_TIME_FORMAT),
            price: 1,
            description: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datePenalty: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Penalty', () => {
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
