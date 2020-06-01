import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AutoparkService } from 'app/entities/autopark/autopark.service';
import { IAutopark, Autopark } from 'app/shared/model/autopark.model';
import { CarType } from 'app/shared/model/enumerations/car-type.model';

describe('Service Tests', () => {
  describe('Autopark Service', () => {
    let injector: TestBed;
    let service: AutoparkService;
    let httpMock: HttpTestingController;
    let elemDefault: IAutopark;
    let expectedResult: IAutopark | IAutopark[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AutoparkService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Autopark(0, 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', CarType.Car, 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Autopark', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Autopark()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Autopark', () => {
        const returnedFromService = Object.assign(
          {
            mark: 'BBBBBB',
            model: 'BBBBBB',
            prePrice: 1,
            color: 'BBBBBB',
            type: 'BBBBBB',
            pledge: 1,
            statusAvaileble: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Autopark', () => {
        const returnedFromService = Object.assign(
          {
            mark: 'BBBBBB',
            model: 'BBBBBB',
            prePrice: 1,
            color: 'BBBBBB',
            type: 'BBBBBB',
            pledge: 1,
            statusAvaileble: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Autopark', () => {
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
