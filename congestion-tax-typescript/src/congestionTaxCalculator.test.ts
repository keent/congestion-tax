import { getTollFee } from './congestionTaxCalculator';
import { isTollFreeDate } from './congestionTaxCalculator'; // Replace 'yourFileName' with the actual file name containing your function
import Car from './car';

describe('isTollFreeDate', () => {
    test.each`
    date                      | expectedResult
    ${'2023-01-05 06:15:00'} | ${true}    // Not 2013
    ${'2013-01-05 06:15:00'} | ${true}    // Saturday
    ${'2013-01-06 08:15:00'} | ${true}    // Sunday
    ${'2013-12-31 08:15:00'} | ${true}    // Holiday
    ${'2013-01-08 06:15:00'} | ${false}   // 06:00–06:29
    ${'2013-01-08 08:15:00'} | ${false}   // 08:00–08:29
    ${'2013-01-08 15:15:00'} | ${false}   // 15:00–15:29
    ${'2013-01-08 18:15:00'} | ${false}   // 18:00–18:29
    ${'2013-01-05 02:15:00'} | ${true}    // 18:30–05:59
    ${'2013-01-08 08:45:00'} | ${false}   // 08:30–14:59
    ${'2013-01-08 16:45:00'} | ${false}   // 15:30–16:59
  `('should return $expectedResult for $date', ({ date, expectedResult }) => {
        const inputDate = new Date(date);

        const result = isTollFreeDate(inputDate);

        expect(result).toBe(expectedResult);
    });

    // Add more test cases for different scenarios
});

describe('getTollFee', () => {
    test('should return SEK 8 for 06:15 AM', () => {
        const date = new Date('2013-01-08T06:15:00');
        const vehicle = new Car();

        const result = getTollFee(date, vehicle);

        expect(result).toBe(8);
    });

    test('should return SEK 13 for 06:45 AM', () => {
        const date = new Date('2013-01-08T06:45:00');
        const vehicle = new Car();

        const result = getTollFee(date, vehicle);

        expect(result).toBe(13);
    });

    test('should return SEK 18 for 07:30 AM', () => {
        const date = new Date('2013-01-08T07:30:00');
        const vehicle = new Car();

        const result = getTollFee(date, vehicle);

        expect(result).toBe(18);
    });

    test('should return SEK 13 for 08:15 AM', () => {
        const date = new Date('2013-01-08T08:15:00');
        const vehicle = new Car();

        const result = getTollFee(date, vehicle);

        expect(result).toBe(13);
    });

    test('should return SEK 8 for 09:00 AM', () => {
        const date = new Date('2013-01-08T09:00:00');
        const vehicle = new Car();

        const result = getTollFee(date, vehicle);

        expect(result).toBe(8);
    });

    test('should return SEK 13 for 15:15 PM', () => {
        const date = new Date('2013-01-08T15:15:00');
        const vehicle = new Car();

        const result = getTollFee(date, vehicle);

        expect(result).toBe(13);
    });

    test('should return SEK 18 for 16:45 PM', () => {
        const date = new Date('2013-01-08T16:45:00');
        const vehicle = new Car();

        const result = getTollFee(date, vehicle);

        expect(result).toBe(18);
    });

    test('should return SEK 13 for 17:30 PM', () => {
        const date = new Date('2013-01-08T17:30:00');
        const vehicle = new Car();

        const result = getTollFee(date, vehicle);

        expect(result).toBe(13);
    });

    test('should return SEK 8 for 18:15 PM', () => {
        const date = new Date('2013-01-08T18:15:00');
        const vehicle = new Car();

        const result = getTollFee(date, vehicle);

        expect(result).toBe(8);
    });

    test('should return SEK 0 for 02:30 AM', () => {
        const date = new Date('2013-01-08T02:30:00');
        const vehicle = new Car();

        const result = getTollFee(date, vehicle);

        expect(result).toBe(0);
    });

    // Add more test cases for different scenarios
});
