import Vehicle from "./vehicle";

export default class Car implements Vehicle {
    getVehicleType(): string {
        return "Car";
    }
}
